package com.kenzantest.dao;

import java.util.List;

import com.kenzantest.service.CustomerResource;

public interface CustomerDAO {

	List<CustomerResource> getAllCustomers();

	CustomerResource addCustomer(CustomerResource cr);

	CustomerResource deleteCustomer(CustomerResource cr);

	CustomerResource updateCustomer(CustomerResource cr);

}