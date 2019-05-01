package com.teamteem.dao;

import com.teamteem.model.Person;
import com.teamteem.util.SessionHelper;
import org.apache.http.auth.InvalidCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
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

    @Autowired
    private SessionHelper sessionHelper;

    public void setPersonDAO(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }


    public String login() {

        if (username == null) {
            throw new NullPointerException("Username cannot be null!");
        }

        if (password == null) {
            throw new NullPointerException("Password cannot be null!");
        }

        try {
            currentUser = personDAO.getPersonByUsernameAndPassword(username, password);
            sessionHelper.setLoggedInPerson(currentUser);
        } catch (InvalidCredentialsException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Incorrect Username or Password", "Invalid Credentials"));
            return null;
        }

        return "/";
    }

    // invalidate/logout the session
    public String logout() {
        System.out.printf("%s IS LOGGING OUT!", new SessionHelper().getLoggedInPerson().getName());
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/";
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