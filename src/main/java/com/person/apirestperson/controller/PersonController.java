package com.person.apirestperson.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.person.apirestperson.dto.Persons;
import com.person.apirestperson.exception.ResourceNotFoundException;
import com.person.apirestperson.model.Address;
import com.person.apirestperson.model.Person;
import com.person.apirestperson.service.PersonService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/person")
@Api(value = "API REST Pessoa")
@CrossOrigin(origins = "*")
public class PersonController {
	@Autowired
	PersonService personService;

	@PostMapping("/")
	@ApiOperation(value = "Save person")
	public Person savePerson(@Valid @RequestBody Person person) {
		return personService.savePerson(person);
	}

	@PutMapping("/{id}")
	@ApiOperation(value = "Update person")
	public ResponseEntity<Person> updatePerson(@PathVariable(value = "id") Long id, 
			@Valid @RequestBody Person person) throws ResourceNotFoundException{
		
		return personService.updatePerson(id, person);
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "Find person by id")
	public ResponseEntity<Person> findPerson(@PathVariable(value = "id") Long id) 
			throws ResourceNotFoundException {
		
		return personService.findPerson(id);
	}

	@GetMapping("/")
	@ApiOperation(value = "Find all persons")
	public Persons findAllPersons() {
		return personService.findAllPersons();
	}

	@PutMapping("/{id}/address")
	@ApiOperation(value = "Save address of person by id")
	public ResponseEntity<Person> saveAddress(@PathVariable(value = "id") Long id,
			@Valid @RequestBody Address address) throws ResourceNotFoundException {
		
		return personService.saveAddress(id, address);
	}

	@GetMapping("/{id}/addresses")
	@ApiOperation(value = "Find all addresses of person by id")
	public ResponseEntity<List<Address>> findAllAddresses(@PathVariable(value = "id") Long id) 
			throws ResourceNotFoundException {
		
		return personService.findAllAddresses(id);
	}

	@PutMapping("/{idPerson}/mainAddress/{idAddress}")
	@ApiOperation(value = "Update address main of person")
	public ResponseEntity<Person> updateMainAddress(@PathVariable(value = "idPerson") Long idPerson,
			@PathVariable(value = "idAddress") Long idAddress) throws ResourceNotFoundException{
		
		return personService.updateMainAddress(idPerson, idAddress);
	}
}