package com.person.apirestperson.dto;

import java.util.ArrayList;
import java.util.List;

import com.person.apirestperson.model.Person;

public class Persons {
	List<Person> persons = new ArrayList<Person>();

	public List<Person> getPersons() {
		return persons;
	}

	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}
}
