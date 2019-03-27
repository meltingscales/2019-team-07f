package com.teamteem.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class VideoConverter {
    /***
     *
     * Converts a video file to an audio file.
     *
     * @param pathToVideo Path to the video file to convert.
     * @return A path to the converted MP3 file.
     * @throws FileNotFoundException Thrown when the video file doesn't exist.
     */
    private static final Logger LOG = Logger.getLogger(VideoConverter.class.getName());

    public static void main(String[] args) {

        try {
            String line;
            String mp4File = "tools/video-to-audio/sampledata/video/potato.mp4potato.mp4";
            String mp3File = "tools/video-to-audio/sampledata/video/potato.mp3";

            // ffmpeg -i input.mp4 output.avi as it's on www.ffmpeg.org
//            FFmpeg ffmpeg = new FFmpeg("/path/to/ffmpeg");
//            FFprobe ffprobe = new FFprobe("/path/to/ffprobe");
            String cmd = "ffmpeg -i " + mp4File + " " + mp3File;
            Process p = Runtime.getRuntime().exec(cmd);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(p.getErrorStream()));
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            p.waitFor();
            System.out.println("Video converted successfully!");
            in.close();
        } catch (IOException | InterruptedException e) {
            LOG.log(Level.SEVERE, null, e);
        }

    }

}
