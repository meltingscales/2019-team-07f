import os
from unittest import *

import videotoaudio

data_dir = os.path.abspath("../../sampledata")
audio_dir = os.path.join(data_dir, "audio")
video_dir = os.path.join(data_dir, "video")


class SimpleTest(TestCase):
    def test_simple(self):
        potato_mp3_path = videotoaudio.convert_video(os.path.join(video_dir, "potato.mp4"))
        print(potato_mp3_path)
