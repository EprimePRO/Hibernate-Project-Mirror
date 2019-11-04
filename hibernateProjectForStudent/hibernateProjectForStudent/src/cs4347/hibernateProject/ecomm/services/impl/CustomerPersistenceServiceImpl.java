/* NOTICE: All materials provided by this project, and materials derived 
 * from the project, are the property of the University of Texas. 
 * Project materials, or those derived from the materials, cannot be placed 
 * into publicly accessible locations on the web. Project materials cannot 
 * be shared with other project teams. Making project materials publicly 
 * accessible, or sharing with other project teams will result in the 
 * failure of the team responsible and any team that uses the shared materials. 
 * Sharing project materials or using shared materials will also result 
 * in the reporting of all team members for academic dishonesty. 
 */ 
 
package cs4347.hibernateProject.ecomm.services.impl;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import cs4347.hibernateProject.ecomm.entity.Customer;
import cs4347.hibernateProject.ecomm.entity.Purchase;
import cs4347.hibernateProject.ecomm.services.CustomerPersistenceService;
import cs4347.hibernateProject.ecomm.util.DAOException;

public class CustomerPersistenceServiceImpl implements CustomerPersistenceService
{
	@PersistenceContext 
	private EntityManager em; 
	
	public CustomerPersistenceServiceImpl(EntityManager em) {
		this.em = em;
	}
	
	/**
	 */
	@Override
	public void create(Customer customer) throws SQLException, DAOException
	{
		try {
			em.getTransaction().begin();
			em.persist(customer);
			em.getTransaction().commit();
		}
		catch (Exception ex) {
			em.getTransaction().rollback();
			throw ex;
		}
	}

	@Override
	public Customer retrieve(Long id) 
	{
		try {
		em.getTransaction().begin();
		Customer cus = em.find(Customer.class, id);
		em.getTransaction().commit();
		return cus;
	}
	catch (Exception ex) {
		em.getTransaction().rollback();
		throw ex;
	}
	}

	@Override
	public void update(Customer c1) throws SQLException, DAOException
	{
		try {
			em.getTransaction().begin();
			Customer c2 = em.find(Customer.class, c1.getId());
			c2.setAddress(c1.getAddress());
			c2.setCreditCard(c1.getCreditCard());
			c2.setEmail(c1.getEmail());
			//c2.setDob(c1.getDob());
			em.getTransaction().commit();
		}
		catch (Exception ex) {
			em.getTransaction().rollback();
			throw ex;
		}
	}

	@Override
	public void delete(Long id) throws SQLException, DAOException
	{
		try {
			em.getTransaction().begin();
			Customer c2 = em.find(Customer.class, id);
			em.remove(c2);
			em.getTransaction().commit();
		}
		catch (Exception ex) {
			em.getTransaction().rollback();
			throw ex;
		}
	}

	@Override
	public List<Customer> retrieveByZipCode(String zipCode) throws SQLException, DAOException
	{
		em.getTransaction().begin();
		List<Customer> emps = (List<Customer>)em.createQuery("from Customer as c where c.address.zipcode = :zip")
			.setParameter("zip", zipCode)
			.getResultList();
		em.getTransaction().commit();
		return emps;
	}

	@Override
	public List<Customer> retrieveByDOB(Date startDate, Date endDate) throws SQLException, DAOException
	{
		em.getTransaction().begin();
		List<Customer> emps = (List<Customer>)em.createQuery("from Customer as c where c.dob between :start and :end")
			.setParameter("start", startDate)
			.setParameter("end", endDate)
			.getResultList();
		em.getTransaction().commit();
		return emps;
	}
}
