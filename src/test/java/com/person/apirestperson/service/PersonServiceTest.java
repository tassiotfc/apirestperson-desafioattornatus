package com.person.apirestperson.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.person.apirestperson.dto.Persons;
import com.person.apirestperson.exception.ResourceNotFoundException;
import com.person.apirestperson.model.Address;
import com.person.apirestperson.model.Person;
import com.person.apirestperson.repository.PersonRepository;

@SpringBootTest(classes = PersonService.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class PersonServiceTest {
	
	@Autowired
	private PersonService personService;
	
	@MockBean
	private PersonRepository personRepository;
	
	private Person p1, p2;
	
	@BeforeEach
	public void createPerson() {
		p1 = new Person("Tássio", LocalDate.of(2020, 12, 01), new HashSet<Address>());
		p2 = new Person("Oliveira", LocalDate.of(2015, 11, 21), new HashSet<Address>());
	}
	
	@Test
	public void testSavePerson() throws ResourceNotFoundException
	{
		personService.savePerson(p1);
		
		ArgumentCaptor<Person> personArgumentCaptor = ArgumentCaptor.forClass(Person.class);
		verify(personRepository).save(personArgumentCaptor.capture());
		Person savedPerson = personArgumentCaptor.getValue();
		assertAll(() -> assertEquals("Tássio", savedPerson.getName()), 
				() -> assertEquals(true, savedPerson.getAdresses().isEmpty()),
				() -> assertEquals(LocalDate.of(2020, 12, 01), savedPerson.getBirthDay()));
        verifyNoMoreInteractions(personRepository);
	}
		
	@Test
	public void findAllPerson() throws Exception{
		List<Person> persons = new ArrayList<Person>();
		persons.add(p1);
		persons.add(p2);
		
		when(personRepository.findAll()).thenReturn(persons);
		
		Persons persons2 = personService.findAllPersons();
		
		assertAll(() -> assertEquals("Tássio", persons2.getPersons().get(0).getName()),
				() -> assertEquals(LocalDate.of(2020, 12, 01), persons2.getPersons().get(0).getBirthDay()),
				() ->assertEquals(true, persons2.getPersons().get(0).getAdresses().isEmpty()),
				() ->assertEquals("Oliveira", persons2.getPersons().get(1).getName()),
				() ->assertEquals(LocalDate.of(2015, 11, 21), persons2.getPersons().get(1).getBirthDay()),
				() ->assertEquals(true, persons2.getPersons().get(1).getAdresses().isEmpty()));
	}
}
