package com.teamteem.dao;

import com.teamteem.model.Person;
import org.apache.http.auth.InvalidCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.io.Serializable;

@ManagedBean(name = "login")
@SessionScoped
public class Login implements Serializable {

    private static final long serialVersionUID = -1776415679978131804L;

    private String username;
    private String password;

    private Person currentUser;

    @Autowired
    private PersonDAO personDAO;

    public void setPersonDAO(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }


    public String login() {

        if(username == null) {
            throw new NullPointerException("Username cannot be null!");
        }

        if(password == null) {
            throw new NullPointerException("Password cannot be null!");
        }

        try {
            currentUser = personDAO.getPersonByUsernameAndPassword(username, password);
        } catch (InvalidCredentialsException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Incorrect Username or Password", "Invalid Credentials"));
            return null;
        }

        return "/logged_in/home.xhtml?faces-redirect=true";
    }

    // invalidate/logout the session
    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/searchable-video-library/login.xhtml?faces-redirect=true";
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

    public Person getCurrentUser() {
        return currentUser;
    }

}