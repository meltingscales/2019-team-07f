package com.teamteem.service;

import com.teamteem.dao.PersonDAO;
import com.teamteem.model.Person;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.List;

@Service
@ManagedBean(name = "personService")
@SessionScoped
public class PersonService implements PersonServiceI {

    private PersonDAO personDAO;

    public PersonService() {
        this.personDAO = new PersonDAO();
    }

    @Override
    @Transactional
    public void addPerson(Person person) {
        this.personDAO.addPerson(person);
    }

    @Transactional
    @Override
    public void delPerson(Person person) {
        this.personDAO.delPerson(person);
    }

    @Transactional
    @Override
    public List<Person> listPersons() {
        return this.personDAO.listPersons();
    }

    public void setPersonDAO(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }
}
