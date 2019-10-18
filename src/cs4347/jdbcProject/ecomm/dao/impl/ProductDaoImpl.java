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
	private final String insertProductSQL = "INSERT INTO PRODUCT (name, description, category, upc) VALUES (?, ?, ?, ?)";

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(Connection connection, Product product) throws SQLException, DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Connection connection, Long id) throws SQLException, DAOException {
		// TODO Auto-generated method stub
		return 0;
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
