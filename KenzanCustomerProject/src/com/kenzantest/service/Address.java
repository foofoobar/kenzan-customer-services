package com.kenzantest.service;

import java.io.Serializable;

import javax.validation.constraints.Pattern;
import javax.validation.executable.ExecutableType;
import javax.validation.executable.ValidateOnExecution;

import org.hibernate.validator.constraints.NotBlank;

@ValidateOnExecution(type = ExecutableType.ALL)
public class Address implements Serializable{

	private static final long serialVersionUID = -3373456587827790666L;
	private String street;
	private String city;
	private String state;
	private String zip;
	
	public Address() {
		
	}
	
	public Address(String street, String city, String state, String zip) {
		this.street = street;
		this.city = city;
		this.state = state;
		this.zip = zip;
	}

	@NotBlank(message="Street name is required")
	public String getStreet() {
		return street;
	}
	
	public void setStreet(String street) {
		this.street = street;
	}
	
	@NotBlank(message="City is required")
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	@NotBlank (message="State is a required 2 letter code")
	@Pattern(regexp="[a-zA-Z]{2}", message="State is a required 2 letter code")
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	@NotBlank(message="Zip is a required and must be a 5 digit code")
	@Pattern(regexp="\\d{5}", message="Zip is a required and must be a 5 digit code")
	public String getZip() {
		return zip;
	}
	
	public void setZip(String zip) {
		this.zip = zip;
	}
	
}
