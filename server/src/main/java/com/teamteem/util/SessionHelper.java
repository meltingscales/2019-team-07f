package com.teamteem.util;

import com.google.api.Http;
import com.teamteem.dao.PersonDAO;
import com.teamteem.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@Transactional
@ManagedBean(name = "sessionHelper")
@SessionScoped
public class SessionHelper {

    /**
     * The key that identifies a Person who is logged in in the current session.
     */
    public static final String USER_KEY = "user";

    @Autowired
    private PersonDAO personDAO;

    public HttpSession getCurrentSession(boolean create) {
        return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(create);
    }

    public HttpSession getCurrentSession() {
        return getCurrentSession(true);
    }

    public Person getLoggedInPerson(HttpSession session) {

        // Person from session, potentially old.
        Person session_person = (Person) session.getAttribute(USER_KEY);

        // New person that could have updated Videos, name, etc.
        Person updated_person = personDAO.getPersonByID(session_person.getId());

        return updated_person;

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