package com.teamteem.model;

import javax.faces.bean.ManagedBean;
import javax.persistence.*;

@Entity
@Table (name = "text")
@ManagedBean (name = "text")
public class Text {

    @Id
    @Column (name = "id")
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    @ManyToOne
    @JoinColumn (name = "person_id", nullable = false)
    private Person person;
}
