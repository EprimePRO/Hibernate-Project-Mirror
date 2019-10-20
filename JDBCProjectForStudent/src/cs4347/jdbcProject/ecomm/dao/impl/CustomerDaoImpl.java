package cs4347.jdbcProject.ecomm.dao.impl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import cs4347.jdbcProject.ecomm.dao.CustomerDAO;

import cs4347.jdbcProject.ecomm.entity.Customer;
import cs4347.jdbcProject.ecomm.entity.Product;
import cs4347.jdbcProject.ecomm.util.DAOException;

public class CustomerDaoImpl implements CustomerDAO
{
	
	private static final String insertCustomerSQL = 
			"INSERT INTO Customer (firstName, lastName, dob, email, gender) "
			+ "VALUES (?, ?, ?, ?, ?);";
	
	private final static String selectQuery = "SELECT id, firstName, lastName, dob, email, gender FROM Customer where id =?;";
	
	
	
	

	@Override
	public Customer create(Connection connection, Customer customer) throws SQLException, DAOException {
		if(customer.getId()!=null) {
			throw new DAOException("Trying to insert customer with NON-NULL ID");
		}
		
		PreparedStatement ps = null;

		try {
			ps = connection.prepareStatement(insertCustomerSQL, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, customer.getFirstName());
			ps.setString(2, customer.getLastName());
			ps.setDate(3, customer.getDob());
			ps.setString(4, customer.getEmail());
			ps.setString(5, String.valueOf(customer.getGender()));

			int result = ps.executeUpdate();

			if(result != 1) {
				throw new DAOException("Create Did Not Update Expected Number Of Rows");
			}

			// REQUIREMENT: Copy the generated auto-increment primary key to the
			// customer ID.
			ResultSet keyRS = ps.getGeneratedKeys();
			keyRS.next();
			int lastKey = keyRS.getInt(1);
			customer.setId((long) lastKey);

			return customer;
		} finally {
			if(ps != null && !ps.isClosed()) {
				ps.close();
			}
		}
	}

	@Override
	public Customer retrieve(Connection connection, Long id) throws SQLException, DAOException {
		if(id == null) {
			throw new DAOException("Trying to retrieve customer with NULL ID");
		}

		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(selectQuery);
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			if (!rs.next()) {
				return null;
			}

			Customer cust = new Customer();
			cust.setId(rs.getLong("id"));
			cust.setFirstName(rs.getString("firstName"));
			cust.setLastName(rs.getString("lastName"));
			cust.setGender(rs.getString("gender").charAt(0));
			cust.setDob(rs.getDate("dob"));
			cust.setEmail(rs.getString("email"));
			return cust;
		}
		finally {
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		}
	}

	final static String updateSQL = "UPDATE customer SET firstName=?, lastName=?, dob=?, email=?, gender=? "
	        + "WHERE id = ?;";
	@Override
	public int update(Connection connection, Customer customer) throws SQLException, DAOException {
		if (customer.getId() == null) {
			throw new DAOException("Trying to update customer with NULL ID");
		}

		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(updateSQL);
			ps.setString(1, customer.getFirstName());
			ps.setString(2, customer.getLastName());
			ps.setDate(3, customer.getDob());
			ps.setString(4, customer.getEmail());
			ps.setString(5, String.valueOf(customer.getGender()));
			ps.setLong(6, customer.getId());

			int rows = ps.executeUpdate();
			return rows;
		}
		finally {
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		}
	}

	private final static String deleteSQL = "DELETE FROM customer WHERE id = ?;";
	
	@Override
	public int delete(Connection connection, Long id) throws SQLException, DAOException {
		if (id == null) {
			throw new DAOException("Trying to delete customer with NULL ID");
		}

		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(deleteSQL);
			ps.setLong(1, id);

			int rows = ps.executeUpdate();
			return rows;
		}
		finally {
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		}
	}

	
	@Override
	public List<Customer> retrieveByZipCode(Connection connection, String zipCode) throws SQLException, DAOException {
		DataSource ds = null;
		List<Customer> ll = new LinkedList<Customer>();
		PreparedStatement ps = null;	//Creates SQL Statement
		
		try {
			//Retrieve all aspects from Customer where the Customer's Address has a certain Zip-Code
			ps = connection.prepareStatement("SELECT * FROM Customer INNER JOIN Address ON Customer.addressID = Address.id WHERE zipcode =?");
			ps.setString(1, zipCode);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				Customer customer = new Customer();
				
				customer.setId( rs.getLong("id"));
				customer.setFirstName( rs.getString("firstName"));
				customer.setLastName( rs.getString("lastName"));
				customer.setGender( rs.getString("gender").charAt(0));
				customer.setEmail( rs.getString("email"));
				customer.setDob(rs.getDate("dob"));
				
				ll.add(customer);
			}
		} catch(SQLException e){
			e.printStackTrace();
		} finally {
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		}
		return ll;
		
	}

	private final static String retrieveDOBSQL = "SELECT id, firstName, lastName, dob, email, gender "
			+ "FROM Customer where dob BETWEEN ? AND ?;";
	
	public List<Customer> retrieveByDOB(Connection connection, Date startDate, Date endDate)throws SQLException, DAOException{
		if(startDate == null) {
			throw new DAOException("Trying to retrieve product with NULL START DATE");
		}
		if(endDate == null) {
			throw new DAOException("Trying to retrieve product with NULL END DATE");
		}
		List<Customer> ll = new LinkedList<Customer>();
	
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(retrieveDOBSQL);
			ps.setDate(1, startDate);
			ps.setDate(2,  endDate);
			ResultSet rs = ps.executeQuery();
			if (!rs.next()) {
				return null;
			}
			while(rs.next()) {
				Customer cust = new Customer();
				cust.setId(rs.getLong("id"));
				cust.setFirstName(rs.getString("firstName"));
				cust.setLastName(rs.getString("lastName"));
				cust.setGender(rs.getString("gender").charAt(0));
				cust.setDob(rs.getDate("dob"));
				cust.setEmail(rs.getString("email"));
				ll.add(cust);
			}
			return ll;
		}
		finally {
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		}
	}
	
}
