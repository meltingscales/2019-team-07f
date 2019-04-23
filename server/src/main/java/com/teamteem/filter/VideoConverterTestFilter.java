package com.teamteem.filter;


import com.teamteem.util.VideoConverter;

import javax.servlet.*;
import java.io.File;
import java.io.*;

/***
 * A one-time filter that converts videos as a test.
 */
public class VideoConverterTestFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        System.out.println("-------------------VideoConverterTestFilter... init() BEGIN");

        String relativeWebPath = filterConfig.getInitParameter("video_file_path");

        System.out.println("....RELATIVE path: video_file_path=" + relativeWebPath);

        String absoluteDiskPath = filterConfig.getServletContext().getRealPath(relativeWebPath);

        System.out.println("....ACTUAL path: video_file_path=" + absoluteDiskPath);

        File f = new File("potato.mp4");
        try {
            boolean ok = f.createNewFile();
            if (ok) {
                System.out.println("------" + f.getAbsolutePath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        convertVideos(relativeWebPath);

        System.out.println("-------------------VideoConverterTestFilter... init() END");

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        //System.out.println("VideoConverterTestFilter.... BEFORE.");

        chain.doFilter(req, res);//sends request to next resource

        //System.out.println("VideoConverterTestFilter.... AFTER.");
    }

    public void convertVideos(String parentDir) throws ServletException {
        VideoConverter videoConverter = new VideoConverter();

        File mp4File = new File(parentDir, "potato.mp4");
        File mp3File = new File(parentDir, "potato.mp3");

        try {
            videoConverter.mp4_to_mp3(mp4File, mp3File);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            throw(new ServletException(e.getMessage()));
        }

    }
}

