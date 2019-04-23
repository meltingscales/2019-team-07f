package com.teamteem.dao;

import com.teamteem.config.UploadConfig;
import com.teamteem.model.Person;

import java.io.File;

import static com.teamteem.config.UploadConfig.*;

public class VideoDAO {


    /**
     * Given a Person, return the folder that person owns for uploading videos.
     *
     * @param person A Person.
     * @return The Folder their videos should reside in.
     */
    public static File getPersonVideoFolder(Person person) {

        int person_id = person.getId();

        // This should never happen. We cannot allow uploading to a fully or partially un-marshalled person's folder.
        assert (person_id != 0);

        File person_video_folder = new File(videosFolder, ((Integer) person.getId()).toString());

        if (!person_video_folder.exists()) {
            person_video_folder.mkdir();
        }

        return person_video_folder;
    }

}
