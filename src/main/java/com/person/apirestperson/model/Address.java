package com.person.apirestperson.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.person.apirestperson.validator.Cep;

@Entity(name = "Address")
@Table(name="addresses")
public class Address{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "logradouro may not be null")
	private String logradouro;
	
	@Cep
	@NotNull(message = "cep may not be null")
	private String cep;

	@NotNull(message = "numeber may not be null")
	private Integer number;

	@NotNull(message = "city may not be null")
	private String city;
	
	private Boolean isMain = false;
	
	public Address(@NotNull(message = "logradouro may not be null") String logradouro,
			@NotNull(message = "cep may not be null") String cep,
			@NotNull(message = "numeber may not be null") Integer number,
			@NotNull(message = "city may not be null") String city, Boolean isMain) {
		super();
		this.logradouro = logradouro;
		this.cep = cep;
		this.number = number;
		this.city = city;
		this.isMain = isMain;
	}
	
	public Address() {
		
	}

	public Boolean getIsMain() {
		return this.isMain;
	}
	
	public void setIsMain(Boolean isMain) {
		this.isMain = isMain;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer numero) {
		this.number = numero;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String cidade) {
		this.city = cidade;
	}
}