import os
import shutil
from unittest import *

from filetype import filetype

from V2ALib import convert_video
from V2AServer import V2AServer

data_dir = os.path.abspath("../../sampledata")
audio_dir = os.path.join(data_dir, "audio")
video_dir = os.path.join(data_dir, "video")
temp_dir = os.path.abspath("./temp")


class LibraryTest(TestCase):
    """These only test the libraries my V2AServer class uses, not the server itself."""

    def setUp(self):
        _ = os.path.join(temp_dir, "potato.mp3")

        if os.path.isfile(_):
            os.remove(_)

    def test_conversion(self):
        """Test if the library can just convert video files and produce MP3 files."""

        potato_mp3_path = convert_video(os.path.join(video_dir, "potato.mp4"),
                                        os.path.join(temp_dir, "potato.mp3"))

        # File should exist.
        assert (os.path.exists(potato_mp3_path))

        # We should be able to open it.
        open(potato_mp3_path, 'r').close()

        # It should look like an MP3.
        assert filetype.guess(potato_mp3_path).extension == "mp3"


class ServerTest(TestCase):

    def setUp(self):
        """Clean out our temp folder."""
        shutil.rmtree(V2AServer.tempfolder())

    def testLocks(self):
        """Test if our V2AServer locks its directories properly."""
        a = V2AServer()
        b = V2AServer()

        while True:
            pass
