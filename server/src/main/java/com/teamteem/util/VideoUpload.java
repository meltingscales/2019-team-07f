package com.teamteem.util;

import com.teamteem.model.Video;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

public class VideoUpload extends HttpServlet {
    private static final Logger LOG = Logger.getLogger(VideoConverter.class.getName());
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            String line;
            //get the file chosen by the user
            Part filePart = request.getPart("uploadBean.file");

            String ffmpeg ="C:\\Users\\Administrator\\Desktop\\ffmpeg\\bin\\ffmpeg.exe";
            String cmd = ffmpeg + " -i " + filePart + " " + filePart;
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


            //get the InputStream to store the file somewhere
            InputStream fileInputStream = filePart.getInputStream();

            //for example, you can copy the uploaded file to the server
            //note that you probably don't want to do this in real life!
            //upload it to a file host like S3 or GCS instead
            File fileToSave = new File("WebContent/uploaded-files/" + filePart.getSubmittedFileName());
            Files.copy(fileInputStream, fileToSave.toPath(), StandardCopyOption.REPLACE_EXISTING);

            //get the URL of the uploaded file
            String fileUrl = "http://localhost:8080/uploaded-files/" + filePart.getSubmittedFileName();

            //You can get other form data too
            String name = request.getParameter("name");

            //create output HTML that uses the
            response.getOutputStream().println("<p>Thanks " + name + "! Here's a link to your uploaded file:</p>");
            response.getOutputStream().println("<p><a href=\"" + fileUrl + "\">" + fileUrl + "</a></p>");
            response.getOutputStream().println("<p>Upload another file <a href=\"http://localhost:8080/new-video.xhtml\">here</a>.</p>");

        } catch (IOException | InterruptedException e) {
            LOG.log(Level.SEVERE, null, e);
        }
    }}

