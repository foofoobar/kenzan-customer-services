package com.kenzantest;

import static com.jayway.restassured.RestAssured.expect;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.restassured.http.ContentType;
import com.kenzantest.service.Address;
import com.kenzantest.service.CustomerResource;


public class CustomerServiceTest {
	
	
	@Test
	public void testInvalidPath() {
		expect().statusCode(404)
				.when().get("/KenzanCustomerProject/api/CustomerService/invalidPath");
	}
	
	
	@Test
	public void testAddNewCustomer() throws JsonGenerationException, JsonMappingException, IOException {
		String newCustomerJson = getSampleCustomer(0);
		given()
		.contentType(ContentType.JSON)
		.body(newCustomerJson)
		.expect().statusCode(200)
		.body("id", greaterThan(0))
		.when().put("/KenzanCustomerProject/api/CustomerService/customers/add");
	}
	
	@Test
	public void testAddOnlySupportedByHttpPut() throws JsonGenerationException, JsonMappingException, IOException {
		String newCustomerJson = getSampleCustomer(0);
		given()
		.contentType(ContentType.JSON)
		.body(newCustomerJson)
		.expect().statusCode(405)
		.when().post("/KenzanCustomerProject/api/CustomerService/customers/add");
	}
	
	@Test
	public void testAddInvalidCustomer() throws JsonGenerationException, JsonMappingException, IOException {
		String newCustomerJson = new ObjectMapper().writeValueAsString(getSampleInvalidCustomerResource(0));
		List<String> errorMessages = given()
		.contentType(ContentType.JSON)
		.body(newCustomerJson)
		.header("Accept", "application/JSON")
		.expect().statusCode(400)
		.when().put("/KenzanCustomerProject/api/CustomerService/customers/add")
		.then()
		.extract()
		.path("errorMessages");
		
		assertTrue(errorMessages.contains("Name is a required field and cannot be empty"));
		assertTrue(errorMessages.contains("Email is a required field and must be valid"));
		assertTrue(errorMessages.contains("State is a required 2 letter code"));
		assertFalse(errorMessages.contains("Address is a required field and cannot be empty"));
		
	}


	@Test
	public void testDeleteExistingCustomer() throws JsonGenerationException, JsonMappingException, IOException {
		
		//add a user and subsequently delete it.
		CustomerResource cr = getSampleCustomerResource(0);
		String newCustomerJson = new ObjectMapper().writeValueAsString(cr);	
		int customerId =
		given()
		.contentType(ContentType.JSON)
		.body(newCustomerJson)
		.when().put("/KenzanCustomerProject/api/CustomerService/customers/add")
		.then()
        .body("id", greaterThan(0))
        .extract()
        .path("id");
			
		//set the id of newly created customer
		cr.setId(customerId);
		newCustomerJson = new ObjectMapper().writeValueAsString(cr);
		
		//delete request
		given()
		.contentType(ContentType.JSON)
		.body(newCustomerJson)
		.expect().statusCode(200)
		.body("id", equalTo(new Long(cr.getId()).intValue()))
		.when().delete("/KenzanCustomerProject/api/CustomerService/customers/delete");
}

	
	@Test
	public void testDeleteOnlySupportedByHttpDelete() throws JsonGenerationException, JsonMappingException, IOException {
		String newCustomerJson = getSampleCustomer(1);
		given()
		.contentType(ContentType.JSON)
		.body(newCustomerJson)
		.expect().statusCode(405)
		.when().post("/KenzanCustomerProject/api/CustomerService/customers/delete");
	}

	@Test
	public void testDeleteErrorMessage() throws JsonGenerationException, JsonMappingException, IOException {
		String newCustomerJson = getSampleCustomer(1234);
		given()
		.contentType(ContentType.JSON)
		.body(newCustomerJson)
		.expect().statusCode(200)
		.body("error", containsString("Delete failed"))
		.when().delete("/KenzanCustomerProject/api/CustomerService/customers/delete");
	}
	
	
	@Test
	public void testUpdateExistingCustomer() throws JsonGenerationException, JsonMappingException, IOException {
		
		//add a user and subsequently update it.
		CustomerResource cr = getSampleCustomerResource(0);
		String newCustomerJson = new ObjectMapper().writeValueAsString(cr);	
		int customerId =
		given()
		.contentType(ContentType.JSON)
		.body(newCustomerJson)
		.when().put("/KenzanCustomerProject/api/CustomerService/customers/add")
		.then()
        .body("id", greaterThan(0))
        .body("name", equalTo("Captain Hook") )
        .extract()
        .path("id");
			
		//set the id of newly created customer
		cr.setId(customerId);
		cr.setName("Jake");
		newCustomerJson = new ObjectMapper().writeValueAsString(cr);
		
		//update request
		given()
		.contentType(ContentType.JSON)
		.body(newCustomerJson)
		.expect().statusCode(200)
		.body("id", equalTo(new Long(cr.getId()).intValue()))
		.body("name", equalTo("Jake"))
		.when().post("/KenzanCustomerProject/api/CustomerService/customers/update");
	}
	
	@Test
	public void testUpdateErrorMessage() throws JsonGenerationException, JsonMappingException, IOException {
		String newCustomerJson = getSampleCustomer(1234);
		given()
		.contentType(ContentType.JSON)
		.body(newCustomerJson)
		.expect().statusCode(200)
		.body("error", containsString("Update failed"))
		.when().post("/KenzanCustomerProject/api/CustomerService/customers/update");
	}
	
	private String getSampleCustomer(long id) throws IOException, JsonGenerationException, JsonMappingException {
		CustomerResource cr = getSampleCustomerResource(id);
		ObjectMapper mapper = new ObjectMapper();
		String newCustomerJson = mapper.writeValueAsString(cr);
		return newCustomerJson;
	}
	
	private CustomerResource getSampleCustomerResource(long id) {
		CustomerResource cr = new CustomerResource();
		cr.setId(id);
		cr.setName("Captain Hook");
		cr.setEmail("hook@gmail.com");
		cr.setTelephone("123-456-7890");
		cr.setAddress(new Address("123 Main St.", "Neverland", "AK", "11001"));
		return cr;
	}
	
	private CustomerResource getSampleInvalidCustomerResource(long id) {
		//missing required data such as name, email, telephone
		CustomerResource cr = new CustomerResource();
		cr.setId(id);
		cr.setName("");
		cr.setAddress(new Address("123 Main St.", "Neverland", "AK12", "AAB00"));
		return cr;
	}
}

