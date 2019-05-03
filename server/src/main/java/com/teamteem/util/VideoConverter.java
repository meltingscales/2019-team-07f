package com.teamteem.util;

import org.apache.commons.io.FileExistsException;

import javax.faces.bean.ManagedBean;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Idris
 */
@ManagedBean(name = "videoConverter")
public class VideoConverter implements javax.servlet.ServletContextListener {

    private static final String FFMPEG_NAME = "ffmpeg";

    /*
     Makes sure that ffmpeg is installed.
     */
    static {
        try {
            Runtime.getRuntime().exec(FFMPEG_NAME);
        } catch (IOException e) {
            e.printStackTrace();
            throw new NullPointerException(String.format("The %s executable does not seem to be on the path.", FFMPEG_NAME));
        }
    }

    private static final Logger LOG = Logger.getLogger(VideoConverter.class.getName());

    public File mp4_to_mp3(File mp4File, File mp3File) throws IOException, InterruptedException {

        /*if (mp3File.exists()) {
            throw new FileExistsException(String.format("Output file %s already exists.", mp3File.getAbsolutePath()));
        }*/

        if (!mp4File.exists()) {
            throw new FileNotFoundException(String.format("Input file %s does not exist!", mp4File.getAbsolutePath()));
        }

        try {
            String line;

            String cmd = String.format("%s -i %s %s", FFMPEG_NAME, mp4File, mp3File); // TODO Severe command injection risk.
            System.out.println(cmd);

            Process p = Runtime.getRuntime().exec(cmd);

            BufferedReader in = new BufferedReader(new InputStreamReader(p.getErrorStream()));

            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }

            p.waitFor();

            System.out.println("Video converted successfully!");
            in.close();
        } catch (IOException | InterruptedException e) {
            LOG.log(Level.SEVERE, null, e);
            throw e;
        }

        if (!mp3File.exists()) {
            throw new FileNotFoundException(String.format("File %s should exist after conversion but it does not!", mp3File.getAbsolutePath()));
        }

        return mp3File;

    }

    /*public File mp3_to_wav(File mp3File, File wavFile) throws IOException, InterruptedException {

        if (wavFile.exists()) {
            throw new FileExistsException(String.format("Output file %s already exists.", wavFile.getAbsolutePath()));
        }

        if (!mp3File.exists()) {
            throw new FileNotFoundException(String.format("Input file %s does not exist!", mp3File.getAbsolutePath()));
        }

        try {
            String line;

            String cmd = String.format("%s -i %s %s", FFMPEG_NAME, mp3File, wavFile); // TODO Severe command injection risk.
            System.out.println(cmd);

            Process p = Runtime.getRuntime().exec(cmd);

            BufferedReader in = new BufferedReader(new InputStreamReader(p.getErrorStream()));

            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }

            p.waitFor();

            System.out.println("Audio converted successfully!");
            in.close();
        } catch (IOException | InterruptedException e) {
            LOG.log(Level.SEVERE, null, e);
            throw e;
        }

        if (!wavFile.exists()) {
            throw new FileNotFoundException(String.format("File %s should exist after conversion but it does not!", wavFile.getAbsolutePath()));
        }

        return wavFile;

    }*/
}