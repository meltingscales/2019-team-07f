package com.teamteem.startup;

import com.teamteem.dao.PersonDAO;
import com.teamteem.model.Person;
import com.teamteem.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ReferencedBean;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.ArrayList;
import java.util.List;

/***
 * A servlet listener that inserts test users into the database on startup.
 */
@WebListener
@ManagedBean(name="testUserStartupServletListener")
public class TestUserStartupServletListener implements ServletContextListener {

    private PersonService personService = new PersonService();

    public static final List<Person> people = new ArrayList<Person>() {
        {
            add(new Person("Test Man", "TestDude123", "testman", "TestCountry", "testpassword"));
        }
    };

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        System.out.println("POTATO DESTROYED");
    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        System.out.println("POTATO INITIALIZED");

        System.out.println("My personService:");
        System.out.println(personService);

        for (Person person : people) {
            System.out.printf("Adding '%s'\n", person.toString());
//            personService.addPerson(person);
        }

    }


}
