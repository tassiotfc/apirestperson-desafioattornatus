package com.person.apirestperson.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.person.apirestperson.model.Address;
import com.person.apirestperson.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>{
	@Query("select p.adresses from Person p where p.id = ?1")
	public List<Address> findAdressesById(Long id);
}