package com.teamteem.util;

import javax.faces.bean.ManagedBean;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Idris
 *
 */
@ManagedBean(name = "videoconverter")
 class VideoConverter extends HttpServlet{

    private static final Logger LOG = Logger.getLogger(VideoConverter.class.getName());

    public static void main(String[] args) throws ServletException {

        try {
            String line;
            Scanner s = new Scanner(System.in);
            System.out.println("Enter video path: ");

            String mp4File = s.next();
            System.out.println("Enter Audio path: ");
            String mp3File = s.next();

            System.out.println("Enter FFmpeg path: ");
            String ffmpeg = s.next();

            String cmd = ffmpeg + " -i " + mp4File + " " + mp3File;
            System.out.println(cmd);

            Process p = Runtime.getRuntime().exec(cmd);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(p.getErrorStream()));
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            p.waitFor();
            System.out.println("Video converted successfully!");
            in.close();
            s.close();
        } catch (IOException | InterruptedException e) {
            LOG.log(Level.SEVERE, null, e);
        }

    }
}