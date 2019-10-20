package cs4347.jdbcProject.ecomm.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cs4347.jdbcProject.ecomm.dao.AddressDAO;
import cs4347.jdbcProject.ecomm.entity.Address;
import cs4347.jdbcProject.ecomm.util.DAOException;

public class AddressDaoImpl implements AddressDAO
{
	private static final String insertSQL = 
			"INSERT INTO ADDRESS (address1, address2, city, state, zipcode) "
			+ "VALUES (?, ?, ?, ?, ?);";
	
	private final static String selectID = "SELECT id, address1, address2, city, state, zipcode"
	        + "FROM address where id = ?";
	
	private final static String deleteSQL = "DELETE FROM ADDRESS WHERE ID = ?;";
	
	
	@Override
	public Address create(Connection connection, Address address, Long customerID) throws SQLException, DAOException {
		
		PreparedStatement ps = null;
		try {
				ps = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, address.getAddress1());
				ps.setString(2, address.getAddress2());
				ps.setString(3, address.getCity());
				ps.setString(4, address.getState());
				ps.setString(5, address.getZipcode());

				int res = ps.executeUpdate();
				if(res != 1) {
					throw new DAOException("Create Did Not Update Expected Number Of Rows");
				}

				// REQUIREMENT: Copy the generated auto-increment primary key to the
				// ID.
				ResultSet keyRS = ps.getGeneratedKeys();
				keyRS.next();
				int lastKey = keyRS.getInt(1);
				address.setID(lastKey);

				return address;
			}
			finally {
				if (ps != null && !ps.isClosed()) {
					ps.close();
				}
			}
		
		
		return null;
	}

	@Override
	public Address retrieveForCustomerID(Connection connection, Long customerID) throws SQLException, DAOException {
		
		if (customerID == null) {
			throw new DAOException("Trying to retrieve Customer with NULL ID");
		}

		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(selectID);
			ps.setLong(1, customerID);
			ResultSet rs = ps.executeQuery();
			if (!rs.next()) {
				return null;
			}

			Address add = new Address();
			add.setAddress1(rs.getString("address1"));
			add.setAddress2(rs.getString("address2"));
			add.setCity(rs.getString("city"));
			add.setState(rs.getString("state"));
			add.setZipcode(rs.getString("zipcode"));
			return add;
		}
		finally {
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		}
	}

	@Override
	public void deleteForCustomerID(Connection connection, Long customerID) throws SQLException, DAOException {
		
		if (customerID == null) {
			throw new DAOException("Trying to delete address with NULL ID");
		}

		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(deleteSQL);
			ps.setLong(1, customerID);

			int rows = ps.executeUpdate();
		}
		finally {
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		}
	}

}
