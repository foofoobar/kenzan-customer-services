package com.kenzantest.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.executable.ValidateOnExecution;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.executable.ExecutableType; 

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;



//API representation of the customer object.

//@XmlRootElement(name = "customer")(Needed if we want to support XML)

@ValidateOnExecution(type = ExecutableType.ALL)
public class CustomerResource implements Serializable{
	private static final long serialVersionUID = 927049801077239116L;
	private long id;
	private String name;
	
	@NotNull(message="Address is a required field and cannot be empty")
	@Valid
	private Address address;
	private String email;
	private String telephone;
	
	private static final Logger logger = Logger.getLogger(CustomerResource.class.getName());
	
	public CustomerResource() {
		
	}
	
	public CustomerResource(long id, String name, Address address, String email, String telephone) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.email = email;
		this.telephone = telephone;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	@NotBlank(message="Name is a required field and cannot be empty")
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
 
	public Address getAddress() {
		return address;
	}
	
	public void setAddress(Address address) {
		this.address = address;
	}
	
	@NotBlank( message="Email is a required field and must be valid")
	@Pattern(regexp="[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}", message="Email is a required field and must be valid")
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	@Pattern(regexp="\\d{3}-\\d{3}-\\d{4}", message="Must be a valid phone number of the format (xxx)xxx-xxxx")
	public String getTelephone() {
		return telephone;
	}
	
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	public String toJsonString() {
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString= "";
		try {
			jsonInString = mapper.writeValueAsString(this);
		} catch (JsonGenerationException e) {
			logger.log(Level.INFO, "Error in ObjectMapper converting POJO to JSON String", e );
		} catch (JsonMappingException e) {
			logger.log(Level.INFO, "Error in ObjectMapper converting POJO to JSON String", e );
		} catch (IOException e) {
			logger.log(Level.INFO, "Error in ObjectMapper converting POJO to JSON String", e );
		}
		return jsonInString;
	}
}
