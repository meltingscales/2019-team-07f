package com.teamteem.dao;

import com.teamteem.model.Person;
import com.teamteem.model.User;
import com.teamteem.util.BCrypt;
import org.apache.http.auth.InvalidCredentialsException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PersonDAO implements PersonDAOI {

    private static final Logger logger = LoggerFactory.getLogger(PersonDAO.class);

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf) {
        this.sessionFactory = sf;
    }

    public Session getSession() {
        try {
            return sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            return sessionFactory.openSession();
        }
    }

    /**
     * Gets a Person by username and password, or throw an {@link InvalidCredentialsException} if the credentials supplied are invalid.
     *
     * @param username Username of a Person.
     * @param password Unhashed password of a Person.
     * @return The Person associated with this login information.
     */
    public Person getPersonByUsernameAndPassword(String username, String password) throws InvalidCredentialsException {

        Session session = getSession();

        Query query = session.createQuery("FROM Person WHERE username = :username")
                .setParameter("username", username);


        List<Person> results = query.getResultList();
        if (results.size() == 0) {
            throw new InvalidCredentialsException("No user by that username exists!");
        }

        Person person = results.get(0);

        if (BCrypt.checkpw(password, person.getPassword())) {
            return (Person) query.getResultList().get(0);
        } else {
            throw new InvalidCredentialsException("Password is invalid!");
        }


    }

    public void clear(Person person) {
        person.setUsername(null);
        person.setName(null);
        person.setCountry(null);
        person.setEmail(null);
        person.setPassword(null);
    }

    /***
     * Given an ID, return the Person associated with that ID.
     * Throws an {@link javax.persistence.EntityNotFoundException} if the Person does not exist.
     * @param id The id of a Person.
     * @return A Person.
     * @throws javax.persistence.EntityNotFoundException
     */
    public Person getPersonByID(int id) {

        Session session = getSession();

        Query query = session.createQuery("FROM Person WHERE id=?").setParameter(0, id);

        List<Person> results = query.getResultList();

        if (results.size() <= 0) {
            throw new EntityNotFoundException("No person by that ID found.");
        }

        return results.get(0);
    }

    /***
     * Given a username, return the Person associated with that username.
     * Throws an {@link javax.persistence.EntityNotFoundException} if the Person does not exist.
     * @param username The username of a Person.
     * @return A Person.
     * @throws javax.persistence.EntityNotFoundException
     */
    public Person getPersonByUsername(String username) {

        Session session = getSession();

        Query query = session.createQuery("FROM Person WHERE username=?").setParameter(0, username);

        List<Person> results = query.getResultList();

        if (results.size() <= 0) {
            throw new EntityNotFoundException("No person by that username found.");
        }

        return results.get(0);
    }


    @Override
    public void addPerson(@NotNull Person person) {
        Session session = getSession();
        List<FacesMessage> problems = new ArrayList<>();

        // Extra crispy Password hash anyone?
        String hashedPassword = BCrypt.hashpw(person.getPassword(), BCrypt.gensalt(12));
        person.setPassword(hashedPassword);

        // Anyone using that username?
        Query query = session.createQuery("FROM Person WHERE username = ?")
                .setParameter(0, person.getUsername());

        if (query.getResultList().size() > 0) {
            problems.add(new FacesMessage(FacesMessage.SEVERITY_ERROR, "That username is in use.", null));
        }

        // Anyone using that email?
        query = session.createQuery("FROM Person WHERE email = ?")
                .setParameter(0, person.getEmail());

        if (query.getResultList().size() > 0) {
            problems.add(new FacesMessage(FacesMessage.SEVERITY_ERROR, "That email is in use.", null));
        }

        // If we got problems,
        if (problems.size() > 0) {
            FacesContext con = FacesContext.getCurrentInstance();

            // Display them all.
            for (FacesMessage message : problems) {
                con.addMessage(null, message);
            }

        } else { // No problems, then...
            session.save(person);
            logger.info("Person saved successfully, Person Details=" + person);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registration Successful!", "Registered"));
        }
    }

    @NotNull
    @SuppressWarnings("unchecked")
    @Override
    public List<Person> listPersons() {
        Session session = this.sessionFactory.getCurrentSession();
        List<Person> personsList = session.createQuery("from Person").list();

        for (Person p : personsList) {
            logger.info("Person List::" + p);
        }

        return personsList;
    }

    @Override
    public void delPerson(@NotNull Person person) {
        Session session = this.sessionFactory.getCurrentSession();

        session.delete(person);
    }
}
