package com.teamteem.model;

import javax.faces.bean.ManagedBean;
import javax.persistence.*;

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
        return this.username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

    @Override
    public String toString() {
        return String.format("id=%d, username=%s", getId(), getUsername());
    }


}
