"""This file shows you how to use the V2AServer, if the tests weren't enough.

We convert our "potato.mp4" example video into "./temp/potato.mp3" here.

This example is the SIMPLEST usage of this program.

You can see in my test cases I use pipes and threads and other crazy stuff, and that's something you can do too, but
for simple usages like this, stuff like passing file descriptors is totally fine to do.

See test cases for more notes and advanced usage.
"""
import os
import shutil
import time

from V2AServer import V2AServer

data_dir = os.path.abspath("../../sampledata")
audio_dir = os.path.join(data_dir, "audio")
video_dir = os.path.join(data_dir, "video")
temp_dir = os.path.abspath("./temp")

if __name__ == "__main__":

    # Ensure temp dir exists, and make it empty.
    if os.path.exists(temp_dir):
        shutil.rmtree(temp_dir)
    os.mkdir(temp_dir)

    # Path to MP4 to be converted.
    MP4_path = os.path.join(video_dir, "potato.mp4")

    # Path to save converted MP3 to.
    MP3_path = os.path.join(temp_dir, "potato.mp3")

    # New V2AServer.
    v2aserver = V2AServer()

    # Read an MP4 file to the V2AServer.
    v2aserver.read_file(open(MP4_path, 'rb'))

    # Write an MP3 file to a file.
    v2aserver.write_file(open(MP3_path, 'wb'))

    print(f"Converted file is at '{MP3_path}'! Will be deleted in ten seconds. Better open it fast.")

    time.sleep(10 * 1000)

    # Like to keep the temp dir clean.
    if (os.path.exists(temp_dir)):
        shutil.rmtree(temp_dir)
        print("MP3 went 'poof'!")
