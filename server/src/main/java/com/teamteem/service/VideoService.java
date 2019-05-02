package com.teamteem.service;

import com.teamteem.dao.VideoDAO;
import com.teamteem.model.Person;
import com.teamteem.model.Video;
import com.teamteem.util.SessionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.List;
import java.util.Set;

@Service
@ManagedBean(name = "videoService")
@SessionScoped
public class VideoService {

    @Autowired
    private VideoDAO videoDAO;

    @Autowired
    private SessionHelper sessionHelper;

    public List<Video> listVideos(Person person) {
        return videoDAO.getVideos(person);
    }

    public List<Video> listVideos() {
        return listVideos(sessionHelper.getLoggedInPerson());
    }
}
