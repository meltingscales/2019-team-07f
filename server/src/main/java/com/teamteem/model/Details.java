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

    /*@OneToOne
    @JoinColumn (name = "video_id", nullable = false)
    private Video video;*/
}
