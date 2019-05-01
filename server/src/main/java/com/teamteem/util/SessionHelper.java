package com.teamteem.util;

import com.google.api.Http;
import com.teamteem.model.Person;

import javax.annotation.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean("sessionHelper")
@SessionScoped
public class SessionHelper {

    /**
     * The key that identifies a Person who is logged in in the current session.
     */
    public static final String USER_KEY = "user";

    public HttpSession getCurrentSession(boolean create) {
        return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(create);
    }

    public HttpSession getCurrentSession() {
        return getCurrentSession(true);
    }

    public Person getLoggedInPerson(HttpSession session) {
        return (Person) session.getAttribute(USER_KEY);
    }

    public Person getLoggedInPerson() {
        return getLoggedInPerson(getCurrentSession());
    }

    public HttpSession setLoggedInPerson(HttpSession session, Person person) {
        session.setAttribute(USER_KEY, person);

        return session;
    }

    public HttpSession setLoggedInPerson(Person person) {
        return setLoggedInPerson(getCurrentSession(), person);
    }

    /***
     * Return if the session is logged in or not with the current session.
     * @return If the session is logged in.
     */
    public boolean isLoggedIn() {
        return isLoggedIn(getCurrentSession(true));
    }

    /***
     * Given a Session, return if the session is logged in or not.
     * @param session The session.
     * @return If the session is logged in.
     */
    public boolean isLoggedIn(HttpSession session) {

        Object object = session.getAttribute(USER_KEY);

        try {
            Person person = (Person) object;
        } catch (ClassCastException e) {
            return false;
        }

        if (object != null) {
            return true;
        } else {
            return false;
        }

    }

}