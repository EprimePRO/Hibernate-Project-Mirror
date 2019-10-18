package cs4347.jdbcProject.ecomm.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cs4347.jdbcProject.ecomm.dao.CreditCardDAO;
import cs4347.jdbcProject.ecomm.entity.Address;
import cs4347.jdbcProject.ecomm.entity.CreditCard;
import cs4347.jdbcProject.ecomm.util.DAOException;


public class CreditCardDaoImpl implements CreditCardDAO
{
	private static final String insertSQL = 
			"INSERT INTO CreditCard (ccNumber, expdate, securitycode) "
			+ "VALUES (?, ?, ?);";
	
	private final static String selectID = "SELECT id, ccNumber, expdate, securitycode"
	        + "FROM creditcard where id = ?";
	
	private final static String deleteSQL = "DELETE FROM creditcard WHERE ID = ?;";
	

	@Override
	public CreditCard create(Connection connection, CreditCard creditCard, Long customerID)
			throws SQLException, DAOException {

		PreparedStatement ps = null;
		try {
				ps = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, creditCard.getCcNumber());
				ps.setString(2, creditCard.getExpDate());
				ps.setString(3, creditCard.getSecurityCode());

				int res = ps.executeUpdate();
				if(res != 1) {
					throw new DAOException("Create Did Not Update Expected Number Of Rows");
				}

				return creditCard;
			}
			finally {
				if (ps != null && !ps.isClosed()) {
					ps.close();
				}
			}
	}

	@Override
	public CreditCard retrieveForCustomerID(Connection connection, Long customerID) throws SQLException, DAOException {
		
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

			CreditCard card = new CreditCard();
			card.setCcNumber(rs.getString("ccNumber"));
			card.setExpDate(rs.getString("expdate"));
			card.setSecurityCode(rs.getString("securitycode"));
			return card;
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
