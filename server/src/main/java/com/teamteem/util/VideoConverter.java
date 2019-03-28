package com.teamteem.util;

import javax.faces.bean.ManagedBean;

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
 class VideoConverter {

    private static final Logger LOG = Logger.getLogger(VideoConverter.class.getName());

    public static void main(String[] args) {

        try {
            String line;
            Scanner s = new Scanner(System.in);
            System.out.println("Enter video path: ");

            String mp4File = s.next();
            System.out.println("Enter Audio path: ");
            String mp3File = s.next();

            String cmd = "C:\\Users\\Administrator\\Desktop\\ffmpeg\\bin\\ffmpeg.exe -i " + mp4File + " " + mp3File;
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