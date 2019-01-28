import os
import shutil
import tempfile
from uuid import uuid1

import V2ALib


class FolderLockedException(Exception):
    pass


class V2AServer:
    """A server that listens for requests to convert video to audio."""

    magicname = "V2AServer"

    @staticmethod
    def tempfolder():
        return os.path.join(tempfile.gettempdir(), "V2AServer")

    @staticmethod
    def lockfile_name():
        return "lock.lock"

    def infile_name(self) -> str:
        return os.path.join(self.outfolder, 'in.mp4')

    def outfile_name(self) -> str:
        return os.path.join(self.outfolder, 'out.mp3')

    def uuid_name(self):
        return str(self.uuid)

    @staticmethod
    def force_purge_tempfolder():
        """Note: Should only ever be used if you interrupt the Python runtime and never give the V2AServer a chance
        to clean up, so there's stale lockfiles.

        Do NOT run this when V2AServers are still running."""
        shutil.rmtree(V2AServer.tempfolder())

    def __init__(self, verbose=0):
        """Make a new V2AServer.

        This will generate a folder inside /tmp/V2AServer/<UUID> that's a V2AServer object-specific folder which gets
        deleted when this object is destroyed.

        That lets you make many!"""

        # UUID to prevent folder name collisions so you can run this in parallel.
        self.uuid = uuid1()  # TODO possibly collides if millions of uuids are created in under 100ns. This is nit-picky.

        # Folder that this V2AServer will output to.
        self.outfolder = os.path.join(V2AServer.tempfolder(), str(self.uuid))

        # If root tempfolder doesn't exist, make it.
        if not os.path.exists(V2AServer.tempfolder()):
            try:
                os.makedirs(V2AServer.tempfolder()) # Allows a non-threadsafe operation to be threadsafe.
            except FileExistsError:
                pass

        # If OUR tempfolder doesn't exist, make it.
        if not os.path.exists(self.outfolder):
            os.makedirs(self.outfolder)

        if verbose: print(f"Output folder: {self.outfolder}")

        # Location of a lock file that may exist.
        self.lockfile = os.path.join(self.outfolder, V2AServer.lockfile_name())

        if os.path.isfile(self.lockfile):
            # If lockfile exists,
            print("Folder that we want to use is somehow in use. Collision?")
            raise FolderLockedException(f"{self.lockfile} is in use!")
        else:  # Lockfile doesn't exist.
            open(self.lockfile, 'w+').close()  # Create it and release lock.

        # Open the file to obtain a lock on it.
        self.lock = open(self.lockfile, 'w+')
        self.lock.write(str(self.uuid))
        self.lock.flush()

    def __del__(self):
        """Called when this object is deleted or is garbage collected."""
        self.close()

    def close(self):
        """Stop the server and clean up."""
        # Release lock on lockfile.
        if (not self.lock.closed):
            self.lock.close()

        # Delete lockfile.
        if (os.path.isfile(self.lockfile)):
            os.remove(self.lockfile)

        # Delete UUID folder.
        if os.path.exists(self.outfolder):
            shutil.rmtree(self.outfolder)

    def read_file(self, r: int) -> str:
        """Listen on a read pipe file descriptor for an incoming MP4 file on `r`, and save it to a file.

        That file gets converted.

        :param r: Readable file descriptor, i.e. 1st return value of `os.pipe()`.
        :return: Path to output file.
        """
        # Get MP4 path to save incoming data.
        mp4in = self.infile_name()

        mp3out = os.path.join(self.outfolder, 'out.mp3')

        # Open a handle on the read pipe in binary mode.
        with os.fdopen(r, 'rb') as reader:
            # Open a handle on the file that will get the MP4's contents
            with open(mp4in, 'wb') as infile:
                while True:  # While we have stuff to read,
                    data = reader.read(1024)  # Take a little data.

                    if len(data) == 0:  # No more to read?
                        break  # Stop reading.

                    infile.write(data)  # Write what small data we took to a file.

        # Convert MP4 to MP3 file.
        return V2ALib.convert_video(mp4in, mp3out)

    def write_file(self, w: int):
        """
        Listen on a file descriptor for an outgoing A read request on `w`, to which MP3 bytes will be written.

        :param w: Writable file descriptor, i.e. 2nd return value of `os.pipe()`.
        """

        # Get MP3 path to the soon-to-be converted file.
        mp3out = self.outfile_name()

        # Open a handle on the write pipe in binary mode to send the MP3.
        with os.fdopen(w, 'wb') as writer:
            # Open the mp3 file to read from.
            with open(mp3out, 'rb') as outfile:
                while True:  # While we have stuff to read,
                    data = outfile.read(1024)  # Take a little data.

                    if len(data) == 0:  # No more to read?
                        break  # Stop reading.

                    writer.write(data)  # Write what small data we took to a pipe.
