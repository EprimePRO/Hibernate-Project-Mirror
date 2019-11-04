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

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import cs4347.hibernateProject.ecomm.entity.Customer;
import cs4347.hibernateProject.ecomm.entity.Product;
import cs4347.hibernateProject.ecomm.services.ProductPersistenceService;
import cs4347.hibernateProject.ecomm.util.DAOException;

public class ProductPersistenceServiceImpl implements ProductPersistenceService
{
	@PersistenceContext 
	private EntityManager em; 
	
	public ProductPersistenceServiceImpl(EntityManager em) {
		this.em = em;
	}
	
	@Override
	public void create(Product product) throws SQLException, DAOException
	{
		try {
			em.getTransaction().begin();
			em.persist(product);
			em.getTransaction().commit();
		}
		catch (Exception ex) {
			em.getTransaction().rollback();
			throw ex;
		}
	}

	@Override
	public Product retrieve(Long id) throws SQLException, DAOException
	{
		try {
			em.getTransaction().begin();
			Product pro = em.find(Product.class, id);
			em.getTransaction().commit();
			return pro;
		}
		catch (Exception ex) {
			em.getTransaction().rollback();
			throw ex;
		}
	}

	@Override
	public void update(Product product) throws SQLException, DAOException
	{
		try {
			em.getTransaction().begin();
			Product p2 = em.find(Product.class, product.getId());
			p2.setProdUPC(product.getProdUPC());
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
			Product c2 = em.find(Product.class, id);
			em.remove(c2);
			em.getTransaction().commit();
		}
		catch (Exception ex) {
			em.getTransaction().rollback();
			throw ex;
		}
	}

	@Override
	public Product retrieveByUPC(String upc) throws SQLException, DAOException
	{
		em.getTransaction().begin();
		Product rupc = (Product)em.createQuery("from Product as p where p.upc = :upc")
				.setParameter("upc", upc)
				.getSingleResult();
		em.getTransaction().commit();
		
		if(rupc == null) {
			throw new DAOException("UPC Not Found " + upc);
		}
		
		return rupc;
	}

	@Override
	public List<Product> retrieveByCategory(int category) throws SQLException, DAOException
	{
		em.getTransaction().begin();
		List<Product> emps = (List<Product>)em.createQuery("from Product where category = :category")
			.setParameter("category", category)
			.getResultList();
		em.getTransaction().commit();
		return emps;
	}

}
