package com.teamteem.startup;

import com.teamteem.dao.PersonDAO;
import com.teamteem.model.Person;
import com.teamteem.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ReferencedBean;
import javax.persistence.EntityNotFoundException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.ArrayList;
import java.util.List;

/***
 * A servlet listener that inserts test users into the database on startup.
 */
@WebListener
@ManagedBean(name = "testUserStartupServletListener")
public class TestUserStartupServletListener implements ServletContextListener {

    @Autowired
    private PersonDAO personDAO;

    public void setPersonDAO(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    private static final List<Person> people = new ArrayList<Person>() {
        {
            add(new Person("Test Man", "TestDude123", "testman", "TestCountry", "testpassword"));
        }
    };

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        System.out.printf("%s DESTROYED", TestUserStartupServletListener.class.getSimpleName());
    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {

        System.out.printf("%s INITIALIZED", TestUserStartupServletListener.class.getSimpleName());

        if (personDAO == null) {
            System.out.print("personDAO is NULL. Cannot insert test users!");
        } else {
            for (Person person : people) {
                try {

                    // If the person exists,
                    personDAO.getPersonByUsername(person.getUsername());

                    //Do nothing.

                } catch (EntityNotFoundException e) {
                    // Add new Person if they do not exist.
                    personDAO.addPerson(person);
                }

            }

        }


    }
}
