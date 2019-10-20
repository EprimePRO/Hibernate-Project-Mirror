package cs4347.jdbcProject.ecomm.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cs4347.jdbcProject.ecomm.dao.PurchaseDAO;
import cs4347.jdbcProject.ecomm.entity.CreditCard;
import cs4347.jdbcProject.ecomm.entity.Purchase;
import cs4347.jdbcProject.ecomm.services.PurchaseSummary;
import cs4347.jdbcProject.ecomm.util.DAOException;

public class PurchaseDaoImpl implements PurchaseDAO
{
	
	private static final String insertSQL = 
			"INSERT INTO Purchase (productID, customerID, purchaseDate, purchaseAmt) "
			+ "VALUES (?, ?, ?, ?);";
	
	private final static String selectID = "SELECT id, productID, customerID, purchaseDate, purchaseAmt "
	        + "FROM Purchase where id = ?";
	
	private final static String deleteSQL = "DELETE FROM purchase WHERE ID = ?;";
	
	private final static String updateSQL = "UPDATE purchase SET productID = ?, customerID = ?, purchaseDate = ?, purchaseAmt = ? "
	        + "WHERE id = ?;";
	
	private final static String selectListC = "SELECT id, productID, customerID, purchaseDate, purchaseAmt "
			        + "FROM Purchase where customerID = ?;";
	
	private final static String selectListP = "SELECT id, productID, customerID, purchaseDate, purchaseAmt "
	        + "FROM Purchase where productID = ?;";
	
	private final static String selectSummary = "SELECT id, productID, customerID, purchaseDate, purchaseAmt "
	        + "FROM Purchase where customerID = ?;";
	

	@Override
	public Purchase create(Connection connection, Purchase purchase) throws SQLException, DAOException {
		
		if(purchase.getId()!=null) {
			throw new DAOException("Trying to insert purchase with NON-NULL ID");
		}
		
		PreparedStatement ps = null;
		try {
				ps = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, purchase.getProductID());
				ps.setLong(2, purchase.getCustomerID());
				ps.setDate(3, purchase.getPurchaseDate());
				ps.setDouble(4, purchase.getPurchaseAmount());

				int res = ps.executeUpdate();
				if(res != 1) {
					throw new DAOException("Create Did Not Update Expected Number Of Rows");
				}

				// REQUIREMENT: Copy the generated auto-increment primary key to the
				// ID.
				ResultSet keyRS = ps.getGeneratedKeys();
				keyRS.next();
				int lastKey = keyRS.getInt(1);
				purchase.setId((long)lastKey);

				return purchase;
			}
			finally {
				if (ps != null && !ps.isClosed()) {
					ps.close();
				}
			}
	}

	@Override
	public Purchase retrieve(Connection connection, Long id) throws SQLException, DAOException {
		if (id == null) {
			throw new DAOException("Trying to retrieve Customer with NULL ID");
		}

		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(selectID);
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			if (!rs.next()) {
				return null;
			}

			Purchase pur = new Purchase();
			pur.setId(rs.getLong("id"));
			pur.setProductID(rs.getLong("productID"));
			pur.setCustomerID(rs.getLong("customerID"));
			pur.setPurchaseDate(rs.getDate("purchaseDate"));
			pur.setPurchaseAmount(rs.getDouble("purchaseAmt"));
			return pur;
		}
		finally {
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		}
	}

	@Override
	public int update(Connection connection, Purchase purchase) throws SQLException, DAOException {
		
		if (purchase.getId() == null) {
			throw new DAOException("Trying to update purchase with NULL ID");
		}

		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(updateSQL);
			ps.setLong(1, purchase.getProductID());
			ps.setLong(2, purchase.getCustomerID());
			ps.setDate(3, purchase.getPurchaseDate());
			ps.setDouble(4, purchase.getPurchaseAmount());
			ps.setLong(5, purchase.getId());

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
	public int delete(Connection connection, Long id) throws SQLException, DAOException {
		if (id == null) {
			throw new DAOException("Trying to delete purchaase with NULL ID");
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
	public List<Purchase> retrieveForCustomerID(Connection connection, Long customerID)
			throws SQLException, DAOException {
		
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(selectListC);
			ps.setLong(1, customerID);
			ResultSet rs = ps.executeQuery();

			List<Purchase> result = new ArrayList<Purchase>();
			while (rs.next()) {
				Purchase pur = new Purchase();
				pur.setId(rs.getLong("id"));
				pur.setProductID(rs.getLong("productID"));
				pur.setCustomerID(rs.getLong("customerID"));
				pur.setPurchaseDate(rs.getDate("purchaseDate"));
				pur.setPurchaseAmount(rs.getDouble("purchaseAmt"));
				result.add(pur);
			}
			return result;
		}
		finally {
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		}
	}

	@Override
	public List<Purchase> retrieveForProductID(Connection connection, Long productID)
			throws SQLException, DAOException {
		
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(selectListP);
			ps.setLong(1, productID);
			ResultSet rs = ps.executeQuery();

			List<Purchase> result = new ArrayList<Purchase>();
			while (rs.next()) {
				Purchase pur = new Purchase();
				pur.setId(rs.getLong("id"));
				pur.setProductID(rs.getLong("productID"));
				pur.setCustomerID(rs.getLong("customerID"));
				pur.setPurchaseDate(rs.getDate("purchaseDate"));
				pur.setPurchaseAmount(rs.getDouble("purchaseAmt"));
				result.add(pur);
			}
			return result;
		}
		finally {
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		}
	}

	@Override
	public PurchaseSummary retrievePurchaseSummary(Connection connection, Long customerID)
			throws SQLException, DAOException {
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(selectSummary);
			ps.setLong(1, customerID);
			ResultSet rs = ps.executeQuery();

			PurchaseSummary result = new PurchaseSummary();
			while (rs.next()) {
				Purchase pur = new Purchase();
				pur.setId(rs.getLong("id"));
				pur.setProductID(rs.getLong("productID"));
				pur.setCustomerID(rs.getLong("customerID"));
				pur.setPurchaseDate(rs.getDate("purchaseDate"));
				pur.setPurchaseAmount(rs.getDouble("purchaseAmt"));
				
				//not done, need to get the summary
			}
			return result;
		}
		finally {
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		}
	}
	
}
