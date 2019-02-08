@file:Suppress("unused")

package com.teamteem.dao

import com.teamteem.model.Person

interface PersonDAOI {
    fun addPerson(person: Person)

    fun delPerson(person: Person)

    fun listPersons(): List<Person>
}