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


    public String Convert(String mp4File, String mp3File, String ffmpeg) {

        try {
            String line;

            String cmd = "ffmpeg -i " + mp4File + " " + mp3File;
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
        } catch (IOException | InterruptedException e) {
            LOG.log(Level.SEVERE, null, e);
        }


        return mp3File;

    }

    
    public static void main(String[] args) throws ServletException {
        VideoConverter videoConverter = new VideoConverter();

        //String mp4File ="C:\\Users\\Administrator\\Documents\\CMU.mp4";
        String mp4File="C:\\Users\\Administrator\\Desktop\\2019-team-07f\\server\\src\\main\\resources\\mp4_test-data\\potato.mp4";
        String mp3File="C:\\Users\\Administrator\\Desktop\\2019-team-07f\\server/src\\main\\resources\\mp4_test-data\\potato.mp3";
        //String mp3File ="C:\\Users\\Administrator\\Documents\\CMU.mp3";
        //String ffmpeg ="C:\\Program Files\\ffmpeg\\bin\\ffmpeg.exe";
        String ffmpeg ="/usr/local/bin/ffmpeg.exe";

        videoConverter.Convert(mp4File, mp3File, ffmpeg);




    }
}