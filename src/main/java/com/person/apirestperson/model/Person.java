package com.person.apirestperson.model;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

@Entity(name = "Person")
@Table(name="persons")
public class Person{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 
	
	@NotNull(message = "name may not be null")
	private String name;
	
	@NotNull(message = "birth day may not be null")
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate birthDay; 
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "person_address",
		joinColumns = { @JoinColumn(name = "person_id")},
		inverseJoinColumns = { @JoinColumn (name = "address_id")})
	private Set<Address> adresses;
	
	public Person(@NotNull(message = "name may not be null") String name,
			@NotNull(message = "birth day may not be null") LocalDate birthDay, Set<Address> adresses) {
		super();
		this.name = name;
		this.birthDay = birthDay;
		this.adresses = adresses;
	}
	
	public Person() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(LocalDate birthDay) {
		this.birthDay  = birthDay;
	}

	public Set<Address> getAdresses() {
		return adresses;
	}

	public void setAdresses(Set<Address> address) {
		this.adresses = address;
	}
}
