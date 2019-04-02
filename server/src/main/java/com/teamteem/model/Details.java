package com.teamteem.model;

import javax.faces.bean.ManagedBean;
import javax.persistence.*;

@Entity
@Table (name = "details")
@ManagedBean (name = "details")
public class Details {

    @Id
    @Column (name = "id")
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;

    private String details;
    private String type;
    private double size;

    @OneToOne
    @JoinColumn (name = "video_id", nullable = false)
    private Video video;

    @OneToOne
    @JoinColumn (name = "audio_id", nullable = false)
    private Audio audio;

    @OneToOne
    @JoinColumn (name = "text_id", nullable = false)
    private Text text;
}
