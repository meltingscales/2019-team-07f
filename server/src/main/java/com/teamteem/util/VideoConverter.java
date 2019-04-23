package com.teamteem.util;

import org.apache.commons.io.FileExistsException;

import javax.faces.bean.ManagedBean;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import java.io.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Idris
 */
@ManagedBean(name = "videoconverter")
public class VideoConverter implements javax.servlet.ServletContextListener {

    private static final Logger LOG = Logger.getLogger(VideoConverter.class.getName());

    public File Convert(File mp4File, File mp3File) throws IOException, InterruptedException {

        if (mp3File.exists()) {
            throw new FileExistsException(String.format("Output file %s already exists.", mp3File.getAbsolutePath()));
        }

        try {
            String line;

            String cmd = "ffmpeg -i " + mp4File + " " + mp3File;
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

}