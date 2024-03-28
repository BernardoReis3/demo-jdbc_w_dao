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
				Department department = instantiateDepartment(rs);			
				Seller seller = instantiateSeller(rs, department);
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

	private Seller instantiateSeller(ResultSet rs, Department department) throws SQLException {
		Seller obj = new Seller(); 
		obj.setId(rs.getInt("Id")); 
		obj.setName(rs.getString("Name")); 
		obj.setEmail(rs.getString("Email")); 
		obj.setSalary(rs.getDouble("BaseSalary")); 
		obj.setBirthDate(rs.getDate("BirthDate")); 
		obj.setDepartment(department); 
		return obj; 
	}


	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department(); 
		dep.setId(rs.getInt("DepartmentId")); 
		dep.setName(rs.getString("DepName")); 
		return dep;
	}


	@Override
	public List<Seller> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
