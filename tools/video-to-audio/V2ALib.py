# TODO package known good ffmpeg binary to avoid security risk when ffmpeg doesn't exist and gets downloaded by Imageio.
# Any external downloads that happen upon setup are a security risk.
# This is very nit-picky, but could be important if GitHub is compromised.

from moviepy.editor import *

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

    clip.audio.close()

    clip.close()

    # Return path.
    return outpath

def stream(reader, writer, chunksize=1024, stoplen=0):
    """Low-level utility function to stream all of `reader`'s contents into `writer` chunk-by-chunk."""
    while True:
        data = reader.read(chunksize) # Read a lil

        if len(data) <= stoplen: # End of stream
            break

        writer.write(data) # Write a lil
