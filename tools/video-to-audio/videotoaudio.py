import shutil
import tempfile
import time
from uuid import uuid1

from moviepy.editor import *


# TODO package known good ffmpeg binary to avoid security risk when ffmpeg doesn't exist and gets downloaded by Imageio.
# Any external downloads that happen upon setup are a security risk.
# This is very nit-picky, but could be important if GitHub is compromised.

def convert_video(videopath: str, outpath: str) -> str:
    """
    :param videopath:   Path to a video file.
    :param outpath:     Path where audio file should be saved.
    :return:            Path of audio file.
    """
    # Load clip.
    clip = VideoFileClip(videopath)

    # Write audio.
    clip.audio.write_audiofile(outpath)

    # Return path.
    return outpath


class V2AServer:
    """A server that listens for requests to convert video to audio."""

    magicname = "V2AServer"

    def __init__(self):
        """Make a new V2AServer.

        This will generate a folder inside ~/tmp/V2AServer/<UUID> that's a V2AServer object-specific folder which gets
        deleted when this object is destroyed.

        That lets you make many!"""

        # UUID to prevent folder name collisions so you can run this in parallel.
        self.uuid = uuid1()  # TODO possibly collides if millions of uuids are created in under 100ns. This is nit-picky.

        # Folder that this V2AServer will output to.
        self.outfolder = os.path.join(tempfile.gettempdir(), "V2AServer", str(self.uuid))

        if not os.path.exists(self.outfolder):
            os.makedirs(self.outfolder)

        print(f"Output folder: {self.outfolder}")

        # Location of a lock file that may exist.
        self.lockfile = os.path.join(self.outfolder, "lock.lock")

        if os.path.isfile(self.lockfile):
            # If lockfile exists,
            try:
                tf = open(self.lockfile) # Try to open it.
                tf.close()
            except IOError as e: # If it fails,
                print("Folder that we want to use is somehow in use. Collision?")
                raise Exception(f"{self.lockfile} is in use!")
        else: # Lockfile doesn't exist.
            open(self.lockfile, 'w+').close() # Create it and release lock.

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

server = V2AServer()

time.sleep(2000)