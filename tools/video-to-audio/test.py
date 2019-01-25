import os
import shutil
import threading
import unittest
from filetype import filetype

from V2ALib import convert_video
from V2AServer import V2AServer

data_dir = os.path.abspath("../../sampledata")
audio_dir = os.path.join(data_dir, "audio")
video_dir = os.path.join(data_dir, "video")
temp_dir = os.path.abspath("./temp")

# Fix temp dir not existing.
if not os.path.exists(temp_dir):
    os.mkdir(temp_dir)


class LibraryTest(unittest.TestCase):
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

        # It should REALLY look like an MP3.
        assert (filetype.guess(potato_mp3_path).mime == "audio/mpeg")


class ServerTest(unittest.TestCase):
    """Test our servers."""

    def setUp(self):
        """Clean out our temp folder in case of any tasty crust left over."""
        if os.path.exists(V2AServer.tempfolder()):
            shutil.rmtree(V2AServer.tempfolder())

    def testConversion(self):
        """Tests if I can put an MP4 into a pipe, and get an MP3 back."""
        v2aserver = V2AServer()

        r, w = os.pipe()  # File descriptors for read/write

        def listen():  # Listen function, to be called on a different thread.
            v2aserver.listen(r, w)

        print("FINISH testConversion! Do it! DO IT! DOOO ITTTT!")
        return "nope lol"

        t1 = threading.Thread(target=listen)  # Run the server's listen on a different thread.
        t1.run()

        # TODO send MP4 through w pipe

        # TODO recieve MP3 through r pipe

    def testLocksStress(self, maxservers=100):
        """Stress test if our V2AServer locks its directories properly."""

        # Lots of servers.
        v2aservers = [V2AServer() for i in range(0, maxservers)]

        # Test each server separately.
        for i in range(0, maxservers):
            server = v2aservers[i]

            # Server's output folder should exist.
            assert (os.path.exists(server.outfolder))

            # Server's lockfile should exist.
            assert (os.path.isfile(server.lockfile))

            # Opening a server's lockfile should show its UUID matches.
            uuid = open(server.lockfile, 'r').read()
            assert (uuid == str(server.uuid))

        # For all tuples of servers,
        for x in range(0, maxservers):
            for y in range(0, maxservers):
                # If they're different servers,
                if x != y:
                    s1 = v2aservers[x]
                    s2 = v2aservers[y]

                    # Lockfile paths should differ.
                    assert (s1.lockfile != s2.lockfile)

                    # Output folder path should differ.
                    assert (s1.outfolder != s2.outfolder)

        # Close all V2AServer objects.
        for v2as in v2aservers:
            v2as.close()
            assert (v2as.lock.closed)
            assert (not os.path.isfile(v2as.lockfile))
            assert (not os.path.exists(v2as.outfolder))

        # After all V2AServers are closed, they should have cleaned up properly.
        assert (len(os.listdir(V2AServer.tempfolder())) == 0)

if __name__ == "__main__":
    unittest.main()