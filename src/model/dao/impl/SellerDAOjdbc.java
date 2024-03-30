package model.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
		PreparedStatement prepared = null;
		
		try {
			prepared = connection.prepareStatement(	
					"INSERT INTO seller "
					+ "(Name, Email, BirthDate, BaseSalary, DepartmentId) " 
					+ "VALUES " 
					+ "(?, ?, ?, ?, ?) ", Statement.RETURN_GENERATED_KEYS);
			
			prepared.setString(1, s.getName());
			prepared.setString(2, s.getEmail());
			prepared.setDate(3, new Date(s.getBirthDate().getTime()));
			prepared.setDouble(4, s.getSalary());
			prepared.setInt(5, s.getDepartment().getId());
			
			int rowsAffected = prepared.executeUpdate();
			if(rowsAffected > 0) {
				ResultSet rs = prepared.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);
					s.setId(id);
				}
				DB.closeResultSet(rs);
			}
			else {
				throw new DBException("Insert failed: no rows affected");
			}
			
		}
		catch (SQLException sqle) {
			throw new DBException(sqle.getMessage());
		}
		finally {
			DB.closeStatement(prepared);
		}
	}

	@Override
	public void update(Seller s) {
		PreparedStatement prepared = null;
		
		try {
			prepared = connection.prepareStatement(	
					"UPDATE seller " 
					+ "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? " 
					+ "WHERE Id = ? ");
			
			prepared.setString(1, s.getName());
			prepared.setString(2, s.getEmail());
			prepared.setDate(3, new Date(s.getBirthDate().getTime()));
			prepared.setDouble(4, s.getSalary());
			prepared.setInt(5, s.getDepartment().getId());
			prepared.setInt(6, s.getId());
			
			prepared.executeUpdate();
					
		}
		catch (SQLException sqle) {
			throw new DBException(sqle.getMessage());
		}
		finally {
			DB.closeStatement(prepared);
		}
	}

	@Override
	public void deleteById(int id) {
		PreparedStatement prepared = null;
		
		try {
			prepared = connection.prepareStatement(	
					"DELETE FROM seller " 
					+ "WHERE Id = ?");
			
			prepared.setInt(1, id);
						
			int rowsAffected = prepared.executeUpdate();
			if(rowsAffected == 0) {
				throw new DBException("No rows were affected");
			}
					
		}
		catch (SQLException sqle) {
			throw new DBException(sqle.getMessage());
		}
		finally {
			DB.closeStatement(prepared);
		}		
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
		PreparedStatement prepared = null;
		ResultSet rs = null;
		
		try {
			prepared = connection.prepareStatement(	
					"SELECT seller.*,department.Name as DepName " 
					+ "FROM seller INNER JOIN department " 
					+ "ON seller.DepartmentId = department.Id "
					+ "ORDER BY Name");
			
			rs = prepared.executeQuery();
			
			List<Seller> sellers = new ArrayList<>();
			Map<Integer, Department> mapDep = new HashMap<>();
			while(rs.next()) {
				
				Department dep = mapDep.get(rs.getInt("DepartmentId"));
				if(dep == null) {
					dep = instantiateDepartment(rs);
					mapDep.put(rs.getInt("DepartmentId"), dep);
				}
				Seller seller = instantiateSeller(rs, dep);
				sellers.add(seller);
			}
			return sellers;
			
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
	public List<Seller> findByDepartment(Department department) {
		PreparedStatement prepared = null;
		ResultSet rs = null;
		
		try {
			prepared = connection.prepareStatement(	
					"SELECT seller.*,department.Name as DepName " 
					+ "FROM seller INNER JOIN department " 
					+ "ON seller.DepartmentId = department.Id "
					+ "WHERE DepartmentId = ? "
					+ "ORDER BY Name");
			
			prepared.setInt(1, department.getId());
			rs = prepared.executeQuery();
			
			List<Seller> sellers = new ArrayList<>();
			Map<Integer, Department> mapDep = new HashMap<>();
			while(rs.next()) {
				
				Department dep = mapDep.get(rs.getInt("DepartmentId"));
				if(dep == null) {
					dep = instantiateDepartment(rs);
					mapDep.put(rs.getInt("DepartmentId"), dep);
				}
				Seller seller = instantiateSeller(rs, dep);
				sellers.add(seller);
			}
			return sellers;
			
		}
		catch (SQLException sqle) {
			throw new DBException(sqle.getMessage());
		}
		finally {
			DB.closeStatement(prepared);
			DB.closeResultSet(rs);
		}
	}

}
