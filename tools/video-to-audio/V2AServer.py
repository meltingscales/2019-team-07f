import os
import shutil
import tempfile
from uuid import uuid1

import V2ALib


class V2AServer:
    """A server that listens for requests to convert video to audio."""

    magicname = "V2AServer"

    @staticmethod
    def tempfolder():
        return os.path.join(tempfile.gettempdir(), "V2AServer")

    @staticmethod
    def lockfile_name():
        return "lock.lock"

    def __init__(self):
        """Make a new V2AServer.

        This will generate a folder inside ~/tmp/V2AServer/<UUID> that's a V2AServer object-specific folder which gets
        deleted when this object is destroyed.

        That lets you make many!"""

        # UUID to prevent folder name collisions so you can run this in parallel.
        self.uuid = uuid1()  # TODO possibly collides if millions of uuids are created in under 100ns. This is nit-picky.

        # Folder that this V2AServer will output to.
        self.outfolder = os.path.join(V2AServer.tempfolder(), str(self.uuid))

        if not os.path.exists(self.outfolder):
            os.makedirs(self.outfolder)

        print(f"Output folder: {self.outfolder}")

        # Location of a lock file that may exist.
        self.lockfile = os.path.join(self.outfolder, V2AServer.lockfile_name())

        if os.path.isfile(self.lockfile):
            # If lockfile exists,
            try:
                tf = open(self.lockfile)  # Try to open it.
                tf.close()
            except IOError as e:  # If it fails,
                print("Folder that we want to use is somehow in use. Collision?")
                raise Exception(f"{self.lockfile} is in use!")
        else:  # Lockfile doesn't exist.
            open(self.lockfile, 'w+').close()  # Create it and release lock.

        # Open the file to obtain a lock on it.
        self.lock = open(self.lockfile, 'r')

    def __del__(self):
        """Called when this object is deleted."""
        # Release lock on lockfile.
        self.lock.close()

        # Delete lockfile.
        os.remove(self.lockfile)

        # Delete UUID folder.
        shutil.rmtree(self.outfolder)

    def listen(self, r: int, w: int):
        """

        This is a blocking operation.

        Listen on a pair of file descriptors first for:
        - An incoming MP4 file on `r`.

        And then:
        - A read request on `w`, to which MP3 bytes will be written.

        :param r: Readable file descriptor, i.e. 1st return value of `os.pipe()`.
        :param w: Writable file descriptor, i.e. 2nd return value of `os.pipe()`.
        """
        # Get MP4 path to save incoming data.
        mp4in = os.path.join(self.outfolder, 'in.mp4')

        # Get MP3 path to the soon-to-be converted file.
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
        mp3out = V2ALib.convert_video(mp4in, mp3out)

        # Open a handle on the write pipe in binary mode to send the MP3.
        with os.fdopen(w, 'wb') as writer:
            # Open the mp3 file to read from.
            with open(mp3out, 'rb') as outfile:
                while True:  # While we have stuff to read,
                    data = outfile.read(1024)  # Take a little data.

                    if len(data) == 0:  # No more to read?
                        break  # Stop reading.

                    writer.write(data)  # Write what small data we took to a pipe.