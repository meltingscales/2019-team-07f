package com.teamteem.dao;

import com.teamteem.config.UploadConfig;
import com.teamteem.model.Person;
import com.teamteem.model.Video;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.faces.bean.ManagedBean;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.teamteem.config.UploadConfig.*;

@Repository
@ManagedBean(name = "videoDAO")
public class VideoDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf) {
        this.sessionFactory = sf;
    }

    public Session getSession() {
        try {
            return sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            return sessionFactory.openSession();
        }
    }

    private static final Logger logger = LoggerFactory.getLogger(PersonDAO.class);


    /**
     * Given a Person, return the folder that person owns for uploading videos.
     *
     * @param person A Person.
     * @return The Folder their videos should reside in.
     */
    public File getPersonVideoFolder(Person person) {

        int person_id = person.getId();

        // This should never happen. We cannot allow uploading to a fully or partially un-marshalled person's folder.
        assert (person_id != 0);

        String s_person_id = ((Integer) person_id).toString();

        File person_video_folder = new File(videosFolder, "person");
        person_video_folder = new File(person_video_folder, s_person_id);

        if (!person_video_folder.exists()) {
            person_video_folder.mkdirs();
        }

        return person_video_folder;
    }

    public List<File> getVideos(Person person) {
        File personVideoFolder = getPersonVideoFolder(person);

        File[] files = personVideoFolder.listFiles();

        if ((files == null) || (files.length == 0)) {
            return new ArrayList<>();
        }

        return Arrays.asList(Objects.requireNonNull(personVideoFolder.listFiles()));
    }

    /***
     * Save a {@link Video} object.
     * @param video The {@link Video} object to a database.
     * @param person The {@link Person} object which owns the {@link Video}.
     * @return The {@link Video} object.
     */
    public Video saveVideo(Person person, Video video) {
        Session session = getSession();

//        session.saveOrUpdate(video);

        throw new NullPointerException(String.format("SaveVideo method is unfinished!")); //TODO finish this method!
    }

    public File saveVideoFile(Person person, Part file, String filename) throws IOException {

        File person_video_folder = this.getPersonVideoFolder(person);

        InputStream input = file.getInputStream();

        File videoFile = new File(person_video_folder, filename);

        System.out.println(person_video_folder.getAbsolutePath());

        if (!videoFile.exists()) {
            videoFile.createNewFile();
        }

        FileOutputStream output = new FileOutputStream(videoFile);

        byte[] buffer = new byte[1024];
        int length;

        while ((length = input.read(buffer)) > 0) {
            output.write(buffer, 0, length);
        }

        input.close();
        output.close();

        Video video = new Video(); //TODO instantiate this Video object with the correct parameters!

//        this.saveVideo(person, video);
        //TODO Save this Video object to the database!

        return videoFile;

    }


}
