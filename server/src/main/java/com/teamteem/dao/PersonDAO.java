package com.teamteem.dao;

import com.teamteem.model.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PersonDAO implements PersonDAOI {


    private static final Logger logger = LoggerFactory.getLogger(PersonDAO.class);

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf) {
        this.sessionFactory = sf;
    }

    @Override
    public void addPerson(@NotNull Person person) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(person);
        logger.info("Person saved successfully, Person Details=" + person);
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
