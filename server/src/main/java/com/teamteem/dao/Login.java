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

    //private SessionFactory sessionFactory;

    public String login() {
        currentUser = validate(username, password);

        if (currentUser != null) {
            return "/logged_in/home.xhtml?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Incorrect Username or Password", "Invalid Credentials"));
            return null;
        }
    }

    // invalidate/logout the session
    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/login.xhtml?faces-redirect=true";
    }

    // check if current user is logged in
    public boolean isLoggedIn() {
        return currentUser != null;
    }

    /*public boolean validate(String username, String password) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM Person WHERE username = ? AND password = ?").setParameter(0, person.getUsername(), person.getPassword());

        if (query.getResultList().size() > 0) {
          return true;
        } else {
          return false;
        }
    }*/

    private User validate(String username, String password) {
        User result = null;

        if (username.equals("admin") && password.equals("admin")) {
            result = new User(username, "Hardcoded User");
        }

        /*try {
            SessionFactory sf = new Configuration().configure().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();
            Query query = session.createQuery("FROM person WHERE username = ? AND password = ?");
            query.setString("username", "username");
            query.setString("password", "password");
        } catch (Exception e) {
            e.printStackTrace();
        }*/

            return result;
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