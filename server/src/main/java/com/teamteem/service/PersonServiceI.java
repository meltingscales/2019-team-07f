package com.teamteem.service;

import com.teamteem.model.Person;

import java.util.List;

public interface PersonServiceI {

    void addPerson(Person person);

    void delPerson(Person person);

    public List<Person> listPersons();
}
