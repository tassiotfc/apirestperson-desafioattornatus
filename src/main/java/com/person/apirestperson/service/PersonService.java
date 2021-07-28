package com.person.apirestperson.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.person.apirestperson.dto.Persons;
import com.person.apirestperson.exception.ResourceNotFoundException;
import com.person.apirestperson.model.Address;
import com.person.apirestperson.model.Person;
import com.person.apirestperson.repository.PersonRepository;

@Service
@Transactional
public class PersonService {
	@Autowired
	PersonRepository personRepository;

	public Person savePerson(Person person) {
		return personRepository.save(person);
	}

	public ResponseEntity<Person> updatePerson(Long id, Person person) 
			throws ResourceNotFoundException {
		Person p = personRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Person not found for this id :: " + id));
		
		p.setName(person.getName());
		p.setBirthDay(person.getBirthDay());
		p.setAdresses(person.getAdresses());
		final Person updatedPerson = personRepository.save(p);
		return ResponseEntity.ok(updatedPerson);
	}

	public ResponseEntity<Person> findPerson(Long id) throws ResourceNotFoundException {
		Person p = personRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Person not found for this id :: " + id));
		
		return ResponseEntity.ok().body(p);
	}
	
	public Persons findAllPersons() {
		List<Person> persons = personRepository.findAll();
		Persons personsDTO = new Persons();
		persons.forEach(each -> {
			personsDTO.getPersons().add(each);
		});
		return personsDTO;
	}

	public ResponseEntity<Person> saveAddress(Long id, Address address) throws ResourceNotFoundException {
		Person p = personRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Person not found for this id :: " + id));

		if (p.getAdresses() == null) {
			p.setAdresses(new HashSet<Address>());
		}
		if (address.getIsMain() == true) {
			for (Address e : p.getAdresses()) {
				if (e.getIsMain() == true) {
					e.setIsMain(false);
				}
			}
		}
		p.getAdresses().add(address);
		personRepository.save(p);
		return ResponseEntity.ok(p);
	}

	public ResponseEntity<List<Address>> findAllAddresses(Long id) throws ResourceNotFoundException {
		personRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Person not found for this id :: " + id));
		List<Address> persons = personRepository.findAdressesById(id);
		return ResponseEntity.ok(persons);
	}

	public ResponseEntity<Person> updateMainAddress(Long idPerson, Long idAddress) throws ResourceNotFoundException {
		Person p = personRepository.findById(idPerson)
				.orElseThrow(() -> new ResourceNotFoundException("Person not found for this id :: " + idPerson));

		for (Address e : p.getAdresses()) {
			e.setIsMain(e.getId() == idAddress);
		}
		final Person updatedPerson = personRepository.save(p);
		return ResponseEntity.ok(updatedPerson);
	}
}