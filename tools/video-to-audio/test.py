import os
import shutil
import threading
import unittest

from filetype import filetype

from V2ALib import convert_video, stream
from V2AServer import V2AServer

data_dir = os.path.abspath("../../sampledata")
audio_dir = os.path.join(data_dir, "audio")
video_dir = os.path.join(data_dir, "video")
temp_dir = os.path.abspath("./temp")


class LibraryTest(unittest.TestCase):
    """These only test the libraries my V2AServer class uses, not the server itself."""

    def setUp(self):
        # Create temp directory right here.
        if not os.path.exists(temp_dir):
            os.mkdir(temp_dir)

    def tearDown(self):
        # Remove temp directory.
        shutil.rmtree(temp_dir)

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
        # Make temp folder if not exists.
        if not os.path.exists(temp_dir):
            os.mkdir(temp_dir)

    def tearDown(self):

        # Delete V2AServer's temp dirs as if it was never run.
        if os.path.exists(V2AServer.tempfolder()):
            shutil.rmtree(V2AServer.tempfolder())

        # Remove temp directory.
        shutil.rmtree(temp_dir)

    def testConversion(self):
        """Tests if I can put an MP4 into a pipe, and get an MP3 back."""
        v2aserver = V2AServer()

        mp3_out_path = os.path.join(temp_dir, "potato.mp3") # Where to save MP3?
        mp4_in_path = os.path.join(video_dir, "potato.mp4")

        r, w = os.pipe()  # File descriptors for read/write

        thread_read = threading.Thread(target=v2aserver.read_file,
                                       args=(r,))  # Run the server's read on a different thread.
        thread_read.start()  # Server will now wait to read a file.

        # Send MP4 through writer pipe
        with open(mp4_in_path, 'rb') as read_file:
            with os.fdopen(w, 'wb') as writer:
                stream(read_file, writer)

        thread_read.join()  # Wait for read thread to end.

        print("Converted. File should be at " + v2aserver.outfile_name())
        assert (os.path.exists(v2aserver.outfile_name()))

        r, w = os.pipe()  # Regenerate file descriptors for read/write

        thread_write = threading.Thread(target=v2aserver.write_file,
                                        args=(w,))  # Run the server's write on a different thread.

        thread_write.start()  # Server will wait to write a file.

        # Create blank MP3 output file if not exists
        if not os.path.exists(mp3_out_path):
            open(mp3_out_path, 'a').close()

        # get MP3 through reader pipe
        with open(mp3_out_path, 'wb') as write_file:
            with os.fdopen(r, 'rb') as reader:
                stream(reader, write_file)

        thread_write.join()  # Wait for write thread to end.

        # Converted file should exist.
        assert (os.path.exists(mp3_out_path))

        # Should still look like an MP3...
        with open(mp3_out_path, 'rb') as mp3outfile:
            data = mp3outfile.read(1000)
            assert (b"ID3" in data)
            assert (b"LAME" in data)

        v2aserver.close()  # Done converting!

        # All dirs should be destroyed.
        assert (not os.path.exists(v2aserver.outfile_name()))
        assert (not os.path.exists(v2aserver.infile_name()))
        assert (not os.path.exists(v2aserver.outfolder))

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
