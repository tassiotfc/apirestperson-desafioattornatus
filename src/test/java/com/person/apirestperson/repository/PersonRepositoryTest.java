package com.person.apirestperson.repository;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.person.apirestperson.exception.ResourceNotFoundException;
import com.person.apirestperson.model.Address;
import com.person.apirestperson.model.Person;

@DataJpaTest
public class PersonRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private PersonRepository personRepository;

	private Person p1, p2;

	@BeforeEach
	public void createPerson() {
		p1 = new Person("Tássio", LocalDate.of(2020, 12, 01), new HashSet<Address>());
		p2 = new Person("Oliveira", LocalDate.of(2015, 11, 21), new HashSet<Address>());
	}

	@Test
	public void testSavePerson() throws ResourceNotFoundException {
		Long id = entityManager.persistAndGetId(p1, Long.class);
		assertNotNull(id);
		final Person p3 = personRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Person not found for this id :: " + id));
		assertNotNull(p3);
		assertAll(() -> assertEquals("Tássio", p3.getName()), 
				() -> assertEquals(true, p3.getAdresses().isEmpty()),
				() -> assertEquals(LocalDate.of(2020, 12, 01), p3.getBirthDay()));
	}
	
	@Test
	public void testFindAllPerson() {
		Person p3 = new Person("Augusto", LocalDate.of(2020, 12, 01), new HashSet<Address>());

		personRepository.save(p1);
		personRepository.save(p2);
		personRepository.save(p3);

		final List<Person> persons = personRepository.findAll();
		assertNotNull(persons);
		assertAll(() -> assertEquals(3, persons.size()), 
				() -> assertEquals("Tássio", persons.get(0).getName()),
				() -> assertEquals("Oliveira", persons.get(1).getName()),
				() -> assertEquals("Augusto", persons.get(2).getName()));
	}

	@Test
	public void testFindAllAddress() throws ResourceNotFoundException{
		Address address1 = new Address("Portugal", "59900000", 12, "Pau dos Ferros", false);
		Address address2 = new Address("José Josias", "58915000", 56, "Uiraúna", true);
		
		p1.getAdresses().add(address1);
		p1.getAdresses().add(address2);
		
		personRepository.save(p1);

		personRepository.findById(1L)
				.orElseThrow(() -> new ResourceNotFoundException("Person not found for this id :: " + 1L));
				
		final List<Address> persons = personRepository.findAdressesById(1L);
		
		assertAll(
				() -> assertEquals(2, persons.size()),
				() -> assertEquals("Portugal", persons.get(1).getLogradouro()),
				() -> assertEquals("59900000", persons.get(1).getCep()),
				() -> assertEquals("Pau dos Ferros", persons.get(1).getCity()),
				() -> assertEquals(12, persons.get(1).getNumber()),
				() -> assertEquals(false, persons.get(1).getIsMain()),
				() -> assertEquals("José Josias", persons.get(0).getLogradouro()),
				() -> assertEquals("58915000", persons.get(0).getCep()),
				() -> assertEquals("Uiraúna", persons.get(0).getCity()),
				() -> assertEquals(56, persons.get(0).getNumber()),
				() -> assertEquals(true, persons.get(0).getIsMain()));
	}
	
	@Test
	public void testUpdatePerson() throws ResourceNotFoundException {
		Person savedPerson = personRepository.save(p1);		
		final Person p3 = personRepository.findById(savedPerson.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Person not found for this id :: " + 1));

		p3.setName("Tássio Fernandes");
		p3.setBirthDay(LocalDate.of(1994, 12, 15));

		personRepository.save(p3);
		
		final List<Person> persons = personRepository.findAll();
		assertNotNull(persons);
		assertAll(() -> assertEquals(1, persons.size()), 
				() -> assertEquals("Tássio Fernandes", persons.get(0).getName()));
		
	}
}