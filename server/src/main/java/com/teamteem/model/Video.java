package com.teamteem.model;

import javax.faces.bean.ManagedBean;
import javax.persistence.*;
import java.io.File;

@Entity
@Table(name = "video")
@ManagedBean(name = "video")
public class Video {

    public Video() {

    }

    public Video(String title, String path, Person person, Details details) {
        this.title = title;
        this.path = path;
        this.person = person;
        this.details = details;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Name of video.
     */
    private String title;

    /**
     * Location on disk of video file.
     */
    private String path;

    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    @OneToOne(mappedBy = "video")
    private Details details;

    public Details getDetails() {
        return details;
    }

    public void setDetails(Details details) {
        this.details = details;
    }
}
