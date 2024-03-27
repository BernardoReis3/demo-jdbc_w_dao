package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.mysql.cj.xdevapi.PreparableStatement;

import db.DB;
import db.DBException;
import model.dao.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

public class SellerDAOjdbc implements SellerDAO{
	
	private Connection connection;
	
	public SellerDAOjdbc(Connection connection) {
		this.connection = connection;
	}
	 

	@Override
	public void insert(Seller s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Seller s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Seller findById(int id) {
		PreparedStatement prepared = null;
		ResultSet rs = null;
		
		try {
			prepared = connection.prepareStatement(	
					"SELECT seller.*,department.Name as DepName " 
					+ "FROM seller INNER JOIN department " 
					+ "ON seller.DepartmentId = department.Id " 
					+ "WHERE seller.Id = ? ");
			
			prepared.setInt(1, id);
			rs = prepared.executeQuery();
			if(rs.next()) {
				Department department = new Department();
				department.setId(rs.getInt("DepartmentId"));
				department.setName(rs.getString("DepName"));
				
				Seller seller = new Seller();
				seller.setId(rs.getInt("Id"));
				seller.setName(rs.getString("Name"));
				seller.setEmail(rs.getString("Email"));
				seller.setBirthDate(rs.getDate("BirthDate"));
				seller.setSalary(rs.getDouble("BaseSalary"));
				seller.setDepartment(department);
				return seller;
			}
			return null;
			
		}
		catch (SQLException sqle) {
			throw new DBException(sqle.getMessage());
		}
		finally {
			DB.closeStatement(prepared);
			DB.closeResultSet(rs);
		}
		
	}

	@Override
	public List<Seller> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
