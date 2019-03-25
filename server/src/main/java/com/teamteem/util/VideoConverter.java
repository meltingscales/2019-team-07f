package com.teamteem.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;

public final class VideoConverter {
    /***
     *
     * Converts a video file to an audio file.
     *
     * @param pathToVideo Path to the video file to convert.
     * @return A path to the converted MP3 file.
     * @throws FileNotFoundException Thrown when the video file doesn't exist.
     */
    public static File convertVideo(File pathToVideo) throws FileNotFoundException {
        if (pathToVideo.exists()) {
            throw new FileNotFoundException("Video file doesn't exist!");
        }

        // TODO insert conversion code here...

        return new File("not_yet_implemented.mp3");
    }
}
