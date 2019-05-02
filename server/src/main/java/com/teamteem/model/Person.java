package com.teamteem.model;

import javax.faces.bean.ManagedBean;
import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "person")
@ManagedBean(name = "person")
public class Person {

    public Person() {
    }

    public Person(String name, String username, String email, String country, String password) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.country = country;
        this.password = password;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;

    private String country;

    private String password;

    @OneToMany(mappedBy = "person")
    private List<Video> videos;

    @OneToMany(mappedBy = "person")
    private List<Audio> audios;

    @OneToMany(mappedBy = "person")
    private List<Text> texts;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return String.format("id=%d, username=%s", getId(), getUsername());
    }

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

    public List<Audio> getAudios() {
        return audios;
    }

    public void setAudios(List<Audio> audios) {
        this.audios = audios;
    }

    public List<Text> getTexts() {
        return texts;
    }

    public void setTexts(List<Text> texts) {
        this.texts = texts;
    }
}
