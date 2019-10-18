package cs4347.jdbcProject.ecomm.dao.impl;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import cs4347.jdbcProject.ecomm.dao.ProductDAO;
import cs4347.jdbcProject.ecomm.entity.Product;
import cs4347.jdbcProject.ecomm.util.DAOException;

public class ProductDaoImpl implements ProductDAO
{
	private final static String insertProductSQL = "INSERT INTO PRODUCT (name, description, category, upc) VALUES (?, ?, ?, ?);";
	private final static String selectQuery = "SELECT id, name, description, category, upc FROM product where customer_id =?;";


	@Override
	public Product create(Connection connection, Product product) throws SQLException, DAOException {

		if(product.getId()!=null) {
			throw new DAOException("Trying to insert address with NON-NULL ID");
		}

		PreparedStatement ps = null;

		try {
			ps = connection.prepareStatement(insertProductSQL, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, product.getProdName());
			ps.setString(2, product.getProdDescription());
			ps.setInt(3, product.getProdCategory());
			ps.setString(4, product.getProdUPC());

			int result = ps.executeUpdate();

			if(result != 1) {
				throw new DAOException("Create Did Not Update Expected Number Of Rows");
			}

			// REQUIREMENT: Copy the generated auto-increment primary key to the
			// customer ID.
			ResultSet keyRS = ps.getGeneratedKeys();
			keyRS.next();
			int lastKey = keyRS.getInt(1);
			product.setId((long) lastKey);

			return product;
		} finally {
			if(ps != null && !ps.isClosed()) {
				ps.close();
			}
		}
	}

	@Override
	public Product retrieve(Connection connection, Long id) throws SQLException, DAOException {
		if(id == null) {
			throw new DAOException("Trying to retrieve Customer with NULL ID");
		}

		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(selectQuery);
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			if (!rs.next()) {
				return null;
			}

			Product prod = new Product();
			prod.setId(rs.getLong("id"));
			prod.setProdName(rs.getString("name"));
			prod.setProdDescription(rs.getString("description"));
			prod.setProdCategory(rs.getInt("category"));
			prod.setProdUPC(rs.getString("upc"));
			return prod;
		}
		finally {
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		}
	}

	final static String updateSQL = "UPDATE product SET name=?, description=?, category=?, upc=? "
	        + "WHERE id = ?;";

	@Override
	public int update(Connection connection, Product product) throws SQLException, DAOException {
		if (product.getId() == null) {
			throw new DAOException("Trying to update product with NULL ID");
		}

		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(updateSQL);
			ps.setString(1, product.getProdName());
			ps.setString(2, product.getProdDescription());
			ps.setInt(3, product.getProdCategory());
			ps.setString(4, product.getProdUPC());
			ps.setLong(5, product.getId());

			int rows = ps.executeUpdate();
			return rows;
		}
		finally {
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		}
	}

	private final static String deleteSQL = "DELETE FROM product WHERE id = ?;";

	@Override
	public int delete(Connection connection, Long id) throws SQLException, DAOException {
		if (id == null) {
			throw new DAOException("Trying to delete product with NULL ID");
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
	public List<Product> retrieveByCategory(Connection connection, int category) throws SQLException, DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product retrieveByUPC(Connection connection, String upc) throws SQLException, DAOException {
		// TODO Auto-generated method stub
		return null;
	}

}
