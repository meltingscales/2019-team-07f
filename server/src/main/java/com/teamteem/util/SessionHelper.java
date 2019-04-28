package com.teamteem.util;

import com.teamteem.model.Person;

import javax.annotation.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean("sessionHelper")
@SessionScoped
public class SessionHelper {
    public static HttpSession getCurrentSession(boolean create) {
        return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(create);
    }

    public static HttpSession getCurrentSession() {
        return getCurrentSession(true);
    }

    public Person getLoggedInPerson() {
        return (Person) getCurrentSession().getAttribute("user");
    }


    /***
     * Return if the session is logged in or not with the current session.
     * @return If the session is logged in.
     */
    public static boolean isLoggedIn() {
        return isLoggedIn(getCurrentSession(true));
    }

    /***
     * Given a Session, return if the session is logged in or not.
     * @param session The session.
     * @return If the session is logged in.
     */
    public static boolean isLoggedIn(HttpSession session) {

        Object object = session.getAttribute("user");

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