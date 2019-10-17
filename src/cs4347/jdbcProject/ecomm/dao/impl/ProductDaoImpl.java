package cs4347.jdbcProject.ecomm.dao.impl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import cs4347.jdbcProject.ecomm.dao.ProductDAO;
import cs4347.jdbcProject.ecomm.entity.Customer;
import cs4347.jdbcProject.ecomm.entity.Product;
import cs4347.jdbcProject.ecomm.testing.DataSourceManager;
import cs4347.jdbcProject.ecomm.util.DAOException;

public class ProductDaoImpl implements ProductDAO
{

	@Override
	public Product create(Connection connection, Product product) throws SQLException, DAOException {
		DataSource ds = null;
		
		try {
			ds = DataSourceManager.getDataSource();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Connection con = ds.getConnection();
		try {
			String query = "INSERT INTO Product (category, description, name, upc)" + "VALUES (?, ?, ?, ?)";
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setLong(1, product.getProdCategory());
			stmt.setString(2, product.getProdDescription());
			stmt.setString(3, product.getProdName());
			stmt.setString(4, product.getProdUPC());
			
			stmt.execute();
			
			con.close();
			
		} catch (Exception e) {
			System.err.println("Caught an exception");
			System.err.println(e.getMessage());
		}
		return null;
	} 

	@Override
	public Product retrieve(Connection connection, Long id) throws SQLException, DAOException {
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
			ResultSet rs = stmt.executeQuery("SELECT * FROM Product WHERE id=" + id);
			
			
			if(rs.next()) {
				Product product = new Product();
				
				
				product.setId( rs.getLong("id"));
				product.setProdCategory( rs.getInt("ProdCategory"));
				product.setProdDescription( rs.getString("ProdDescription"));
				product.setProdName( rs.getString("ProdName"));
				product.setProdUPC( rs.getString("ProdUPC"));
				
			}
		}  catch (SQLException ex) {
            ex.printStackTrace();
		}
		return product;
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
