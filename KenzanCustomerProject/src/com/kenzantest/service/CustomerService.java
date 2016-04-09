package com.kenzantest.service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import com.kenzantest.dao.CustomerDAO;
import com.kenzantest.dao.CustomerDAOImpl;

@Path("/CustomerService")
public class CustomerService {
	
	private CustomerDAO customerDAO = new CustomerDAOImpl();
	private final static Logger logger = Logger.getLogger(CustomerService.class.getName());

	@GET
	@Path("/customers")
	@Produces(MediaType.APPLICATION_JSON)
	public List<CustomerResource> getUsers(){
		return customerDAO.getAllCustomers();
	}

	@PUT
	@Path("/customers/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addNewCustomer(@Valid CustomerResource customer) {
		logger.log(Level.INFO, "Request to add a new customer. Customer: " + customer.toJsonString());
		CustomerResource newCustomer = customerDAO.addCustomer(customer);
		if (newCustomer != null) {
			//success
			return Response.status(200).entity(customer).build();
		} else {
			//failure
			logger.log(Level.WARNING, "Failed to add a new customer. Customer: " + customer.toJsonString());
			JSONObject errorResponse = new JSONObject();
			errorResponse.put("error", "Unable to add new customer. Customer Name: " + customer.getName());
			return Response.status(200).entity(errorResponse.toString()).build();
		}
	}
 
	@DELETE
	@Path("/customers/delete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteCustomer(CustomerResource customer) {
		logger.log(Level.INFO, "Request to delete a customer. Customer: " + customer.toJsonString());
		CustomerResource deletedCustomer = customerDAO.deleteCustomer(customer);
		if (deletedCustomer != null) {
			//success
			return Response.status(200).entity(customer).build();
		} else {
			//failure
			logger.log(Level.WARNING, "Failed to delete a customer. Customer: " + customer.toJsonString());
			JSONObject errorResponse = new JSONObject();
			errorResponse.put("error", "Unable to find cutomer. Delete failed. Customer Name: " + customer.getName());
			return Response.status(200).entity(errorResponse.toString()).build();
		}
	}
	
	@POST
	@Path("/customers/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateCustomer(CustomerResource customer) {
		logger.log(Level.INFO, "Request to update a customer. Customer: " + customer.toJsonString());
		CustomerResource updatedCustomer = customerDAO.updateCustomer(customer);
		if (updatedCustomer != null) {
			//success
			return Response.status(200).entity(updatedCustomer).build();
		} else {
			//failure
			logger.log(Level.WARNING, "Failed to update a customer. Customer: " + customer.toJsonString());
			JSONObject errorResponse = new JSONObject();
			errorResponse.put("error", "Customer not found. Update failed. Customer Name: " + customer.getName());
			return Response.status(200).entity(errorResponse.toString()).build();
		}
	}
	

}
