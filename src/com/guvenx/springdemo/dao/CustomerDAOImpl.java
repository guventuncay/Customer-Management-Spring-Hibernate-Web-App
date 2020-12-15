package com.guvenx.springdemo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.guvenx.springdemo.entity.Customer;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

	// need to inject the session factory

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Customer> getCustomers() {

		// get the current hibernate session
		Session session = this.sessionFactory.getCurrentSession();
		// create a query
		Query<Customer> query = session.createQuery("from Customer order by lastName", Customer.class);
		// execute query an get result list
		List<Customer> customers = query.getResultList();
		// return the results

		return customers;
	}

	@Override
	public void saveCustomer(Customer customer) {
		// get current hibernate session
		Session session = this.sessionFactory.getCurrentSession();
		// save/update the customer
		session.saveOrUpdate(customer);
	}

	@Override
	public Customer getCustomer(int id) {
		// get session
		Session session = this.sessionFactory.getCurrentSession();
		// get customer
		Customer customer = session.get(Customer.class, id);
		// return customer
		return customer;
	}

	@Override
	public void deleteCustomer(int id) {
		// get the current hibernate session
		Session session = this.sessionFactory.getCurrentSession();
		// delete object with primary key
		Query<?> query = session.createQuery("delete from Customer where id= :id");
		query.setParameter("id", id);
		query.executeUpdate();
	}

	@Override
	public List<Customer> searchCustomers(String searchName) {

		// get the current hibernate session
		Session session = sessionFactory.getCurrentSession();
		
		Query<Customer> query = null;
		
		//
		// only search by name if searchName is not empty
		//
		if (searchName != null && searchName.trim().length() > 0) {

			// search for firstName or lastName ... case insensitive
			query =session.createQuery("from Customer where lower(firstName) like :name or lower(lastName) like :name", Customer.class);
			query.setParameter("name", "%" + searchName.toLowerCase() + "%");

		}
		else {
			// theSearchName is empty ... so just get all customers
			query =session.createQuery("from Customer", Customer.class);			
		}
		
		// execute query and get result list
		List<Customer> customers = query.getResultList();
				
		// return the results		
		return customers;
		
	}

}
