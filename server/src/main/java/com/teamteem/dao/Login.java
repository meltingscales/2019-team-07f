package com.teamteem.dao;

import com.teamteem.model.User;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;

@ManagedBean
@SessionScoped
public class Login implements Serializable {

    private static final long serialVersionUID = -1776415679978131804L;

    private String username;
    private String password;

    private User currentUser;

    public String login() {
        currentUser = PersonDAO.validate(username, password);

        if (currentUser != null) {
            return "/logged_in/home.xhtml?faces-redirect=true";
        } else if (username.equals("admin") && password.equals("admin")) {
            return "/logged_in/admin.xhtml?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Incorrect Username or Password", "Invalid Credentials"));
            return null;
        }
    }

    // invalidate/logout the session
    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/searchable-video-library/login.xhtml?faces-redirect=true";
    }

    // check if current user is logged in
    public boolean isLoggedIn() {
        return currentUser != null;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getCurrentUser() {
        return currentUser;
    }

}