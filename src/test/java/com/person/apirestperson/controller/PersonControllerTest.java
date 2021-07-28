package com.person.apirestperson.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.person.apirestperson.dto.Persons;
import com.person.apirestperson.model.Address;
import com.person.apirestperson.model.Person;
import com.person.apirestperson.service.PersonService;

@ContextConfiguration(classes = PersonController.class)
@WebMvcTest(controllers = PersonController.class)
public class PersonControllerTest {

	@Autowired
    MockMvc mockMvc;

	@MockBean
	private PersonService personService;
	
	private Person p1, p2;
	
	@BeforeEach
	public void createPerson() {
		p1 = new Person("Tássio", LocalDate.of(2020, 12, 01), new HashSet<Address>());
		p2 = new Person("Oliveira", LocalDate.of(2015, 11, 21), new HashSet<Address>());
	}
	
	@Test
	public void testSavePerson() throws Exception
	{	
		mockMvc.perform(post("/person/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(p1)))
                .andExpect(status().is2xxSuccessful());
		
		ArgumentCaptor<Person> personArgumentCaptor = ArgumentCaptor.forClass(Person.class);
        verify(personService).savePerson(personArgumentCaptor.capture());
        Person capturedRequest = personArgumentCaptor.getValue();
        assertEquals("Tássio", capturedRequest.getName());
        assertEquals(LocalDate.of(2020, 12, 01), capturedRequest.getBirthDay());
        assertEquals(true, capturedRequest.getAdresses().isEmpty());
	}
	
	@Test
	public void findAllPerson() throws Exception{
		Persons persons = new Persons();
		persons.getPersons().add(p1);
		persons.getPersons().add(p2);
		
		when(personService.findAllPersons()).thenReturn(persons);
		
		mockMvc.perform(get("/person/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(p1)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.persons.*", hasSize(2)))
                .andExpect(jsonPath("$.persons[0].name", equalTo("Tássio")))
                .andExpect(jsonPath("$.persons[0].birthDay", equalTo(LocalDate.of(2020, 12, 01).format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))))
                .andExpect(jsonPath("$.persons[0].adresses", equalTo(new ArrayList<Person>())))
                .andExpect(jsonPath("$.persons[1].name", equalTo("Oliveira")))
                .andExpect(jsonPath("$.persons[1].birthDay", equalTo(LocalDate.of(2015, 11, 21).format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))))
                .andExpect(jsonPath("$.persons[1].adresses", equalTo(new ArrayList<Person>())));
	}
}
