package com.kenzantest.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.kenzantest.service.Address;
import com.kenzantest.service.CustomerResource;

public class CustomerDAOImpl implements CustomerDAO {

	private static EntityManagerFactory entityManagerFactory;
	private static EntityManager entityManager;
	private static final Logger logger = Logger.getLogger(CustomerDAOImpl.class.getName());

	static {
		try {
			entityManagerFactory = Persistence.createEntityManagerFactory("test");
			entityManager = entityManagerFactory.createEntityManager();
		} catch (Throwable ex) {
			logger.log(Level.SEVERE, "Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kenzantest.CustomerDAO#getAllCustomers()
	 */
	@Override
	public List<CustomerResource> getAllCustomers() {
		List<CustomerResource> allCustomers = new ArrayList<CustomerResource>();
		try {
			entityManager.getTransaction().begin();
			@SuppressWarnings("unchecked")
			List<Customer> customers = entityManager.createQuery("from Customer").getResultList();
			if (customers.isEmpty() == false) {
				for (Customer c : customers) {
					Address address = new Address(c.getStreet(), c.getCity(), c.getState(), c.getZip());
					CustomerResource cr = new CustomerResource(c.getCustomerId(), c.getCustomerName(), address,
							c.getEmail(), c.getTelephone());
					allCustomers.add(cr);
				}
			}
			entityManager.getTransaction().commit();
			return allCustomers;
		} catch (Exception e) {
			 logger.log(Level.INFO, "Unable to retrieve list of all customers");
			entityManager.getTransaction().rollback();
			return null;
		} finally {
			if (entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.kenzantest.CustomerDAO#addCustomer(com.kenzantest.CustomerResource)
	 */
	@Override
	public CustomerResource addCustomer(CustomerResource cr) {
		if (cr != null) {
			try {
				entityManager.getTransaction().begin();
				Customer customer = new Customer(cr.getName(), cr.getAddress().getStreet(), cr.getAddress().getCity(),
						cr.getAddress().getState(), cr.getAddress().getZip(), cr.getEmail(), cr.getTelephone());
				customer = entityManager.merge(customer);
				cr.setId(customer.getCustomerId());
				entityManager.getTransaction().commit();
			} catch (Exception e) {
				logger.log(Level.INFO, "Unable to add a new customer");
				entityManager.getTransaction().rollback();
			} finally {
				if (entityManager.getTransaction().isActive()) {
					entityManager.getTransaction().rollback();
				}
			}
		}
		return cr;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kenzantest.CustomerDAO#deleteCustomer(com.kenzantest.
	 * CustomerResource)
	 */
	@Override
	public CustomerResource deleteCustomer(CustomerResource cr) {
		if (cr != null) {
			try {
				entityManager.getTransaction().begin();
				Customer customer = entityManager.find(Customer.class, cr.getId());
				if (customer == null) {
					return null;
				} else {
					entityManager.remove(customer);
				}
				entityManager.getTransaction().commit();
				return cr;
			} catch (Exception e) {
				logger.log(Level.INFO, "Unable to delete a new customer");
				entityManager.getTransaction().rollback();
			} finally {
				if (entityManager.getTransaction().isActive()) {
					entityManager.getTransaction().rollback();
				}
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kenzantest.CustomerDAO#updateCustomer(com.kenzantest.
	 * CustomerResource)
	 */
	@Override
	public CustomerResource updateCustomer(CustomerResource cr) {
		if (cr != null) {
			try {
				entityManager.getTransaction().begin();
				Customer customer = entityManager.find(Customer.class, cr.getId());
				if (customer == null) {
					return null;
				} else {
					customer.setCustomerName(cr.getName());
					customer.setStreet(cr.getAddress().getStreet());
					customer.setCity(cr.getAddress().getCity());
					customer.setState(cr.getAddress().getState());
					customer.setZip(cr.getAddress().getZip());
					customer.setEmail(cr.getEmail());
					customer.setTelephone(cr.getTelephone());
				}
				entityManager.getTransaction().commit();
				return cr;
			} catch (Exception e) {
				logger.log(Level.INFO, "Unable to update a new customer");
				entityManager.getTransaction().rollback();
			} finally {
				if (entityManager.getTransaction().isActive()) {
					entityManager.getTransaction().rollback();
				}
			}
		}
		return null;
	}

	public CustomerResource getSingleResult() {
		try {
			entityManager.getTransaction().begin();
			@SuppressWarnings("unchecked")
			Customer customer = (Customer) entityManager.createQuery("from Customer").getSingleResult();
			Address address = new Address(customer.getStreet(), customer.getCity(), customer.getState(),
					customer.getZip());
			CustomerResource cr = new CustomerResource(customer.getCustomerId(), customer.getCustomerName(), address,
					customer.getEmail(), customer.getTelephone());
			entityManager.getTransaction().commit();
			return cr;
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			return null;
		} finally {
			if (entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}
		}
	}

}
