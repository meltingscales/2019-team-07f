package com.teamteem.model;

import java.io.Serializable;

public class User implements Serializable {

  private static final long serialVersionUID = -3698877761649437462L;

  private String username;
  private String fullName;

  public User(String username, String fullName) {
    this.username = username;
    this.fullName = fullName;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getName() {
    return fullName;
  }
}