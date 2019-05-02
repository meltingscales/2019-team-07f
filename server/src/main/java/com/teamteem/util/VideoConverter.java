package com.teamteem.util;

import org.apache.commons.io.FileExistsException;

import javax.faces.bean.ManagedBean;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Idris
 */
@ManagedBean(name = "videoconverter")
public class VideoConverter implements javax.servlet.ServletContextListener {

    /*@Autowired
    public VideoDAO videoDAO;

    @Autowired
    private SessionHelper sessionHelper;

    private Part file;
    private String uploadedfileName;*/

    private static final String fileExt = "mp3";
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

        if (mp3File.exists()) {
            throw new FileExistsException(String.format("Output file %s already exists.", mp3File.getAbsolutePath()));
        }

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

    /*public void placeAudioFile() throws Exception{

        Person person = sessionHelper.getLoggedInPerson();

        File person_video_folder = VideoDAO.getPersonVideoFolder(person);

        File videoFile = new File(person_video_folder, uploadedfileName);

        if (videoFile.exists()) {
            mp4_to_mp3();
        } else {
            throw new NullPointerException("mp4 file does't exist!");
        }

        if (file != null) {
            videoDAO.saveVideoFile(person, file, String.format("%s.%s", uploadedfileName, fileExt));
        } else {
            throw new NullPointerException(String.format("`file` is null somehow! This %s's variables are not being automatically filled in!", VideoConverter.class.getSimpleName()));
        }
    }*/

}