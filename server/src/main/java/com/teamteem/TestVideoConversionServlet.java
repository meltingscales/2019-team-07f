package com.teamteem;

import com.teamteem.util.VideoConverter;

import javax.faces.bean.ManagedBean;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URL;

@ManagedBean (name = "testVideoConversion")

public class TestVideoConversionServlet extends HttpServlet {

    public static String run_conversion_test() throws Exception {

        VideoConverter videoConverter = new VideoConverter();

        File mp4_test_folder = new File(TestVideoConversionServlet.class.getClassLoader().getResource("mp4_test-data").getFile());

        File mp4File = new File(mp4_test_folder, "Cloud_Speech_API_Demo.mp4");
        File mp3File = new File(mp4_test_folder, "output.wav");

        if (mp3File.exists()) {
            mp3File.delete();
        }

        videoConverter.mp4_to_mp3(mp4File, mp3File);

        if (!mp3File.exists()) {
            throw new Exception("MP3 file we converted does not exist somehow!");
        }

        return "Test successful.";

    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        try {
            run_conversion_test();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e.getMessage());
        }

        res.setStatus(200);
        res.getWriter().write("Success.");
    }
}
