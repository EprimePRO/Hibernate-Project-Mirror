package cs4347.jdbcProject.ecomm.dao.impl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import cs4347.jdbcProject.ecomm.dao.CustomerDAO;
import cs4347.jdbcProject.ecomm.entity.Address;
import cs4347.jdbcProject.ecomm.entity.Customer;
import cs4347.jdbcProject.ecomm.testing.DataSourceManager;
import cs4347.jdbcProject.ecomm.util.DAOException;

public class CustomerDaoImpl implements CustomerDAO
{

	@Override
	public Customer create(Connection connection, Customer customer) throws SQLException, DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Customer retrieve(Connection connection, Long id) throws SQLException, DAOException {
		DataSource ds = null;
		
		try {
			ds = DataSourceManager.getDataSource();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Connection con = ds.getConnection();
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Customer WHERE id=" + id + "INNER JOIN Address ON Customer.addressId = Address.id");
			
			
			if(rs.next()) {
				Customer customer = new Customer();
				
				
				customer.setId( rs.getLong("id"));
				customer.setFirstName( rs.getString("firstName"));
				customer.setLastName( rs.getString("lastName"));
				customer.setGender( rs.getString("gender").charAt(0));
				customer.setEmail( rs.getString("email"));
				customer.setDob(rs.getDate("dob"));
				
			}
		}  catch (SQLException ex) {
            ex.printStackTrace();
		
			
		}
		return null;
	}

	@Override
	public int update(Connection connection, Customer customer) throws SQLException, DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Connection connection, Long id) throws SQLException, DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Customer> retrieveByZipCode(Connection connection, String zipCode) throws SQLException, DAOException {
		DataSource ds = null;
		List<Customer> ll = new LinkedList<Customer>();
		
		try {
			ds = DataSourceManager.getDataSource();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Connection con = ds.getConnection();	//Instantiates Connection to SQL
		try {
			Statement stmt = con.createStatement();	//Creates SQL Statement
			//Retrieve all aspects from Customer where the Customer's Address has a certain Zip-Code
			ResultSet rs = stmt.executeQuery("SELECT * FROM Customer INNER JOIN Address ON Customer.addressID = Address.id WHERE zipcode =" + zipCode);
			
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
		}
		return ll;
		
	}

	@Override
	public List<Customer> retrieveByDOB(Connection connection, Date startDate, Date endDate)
			throws SQLException, DAOException {
		
		return null;
	}
	
}
