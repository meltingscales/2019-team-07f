package com.teamteem;

import com.teamteem.util.VideoConverter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class TestVideoConversionServlet extends HttpServlet {

    public static String run_conversion_test() throws IOException, InterruptedException {

        VideoConverter videoConverter = new VideoConverter();

        URL mp4_test_folder = TestVideoConversionServlet.class.getClassLoader().getResource("mp4_test-data");

        System.out.printf("File is %s", mp4_test_folder);

        File mp4File = new File(mp4_test_folder.getFile(), "Cloud_Speech_API_Demo.mp4");
        File mp3File = new File(mp4_test_folder.getFile(), "output.wav");

        if (mp3File.exists()) {
            mp3File.delete();
        }

        videoConverter.mp4_to_mp3(mp4File, mp3File);

        return "Test successful.";

    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        try {
            run_conversion_test();
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new ServletException(e.getMessage());
        }

        res.setStatus(200);
        res.getWriter().write("Success.");
    }
}
