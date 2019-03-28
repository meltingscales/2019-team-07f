package com.teamteem.model;

import javax.faces.bean.ManagedBean;
import javax.persistence.*;

@Entity
@Table(name = "video")
@ManagedBean(name = "video")
public class Video {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    @ManyToOne
    @JoinColumn(name="person_id", nullable = false)
    private Person person;
}
