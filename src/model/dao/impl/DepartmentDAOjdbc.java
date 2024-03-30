package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DBException;
import model.dao.DepartmentDAO;
import model.entities.Department;

public class DepartmentDAOjdbc implements DepartmentDAO{

	private Connection connection;
	
	public DepartmentDAOjdbc(Connection connection) {
		this.connection = connection;
	}
	
	@Override
	public void insert(Department d) {
		PreparedStatement prepared = null;
		
		try {
			prepared = connection.prepareStatement(
					"INSERT INTO department "
					+ "(Id, Name) " 
					+ "VALUES " 
					+ "(?, ?) ", Statement.RETURN_GENERATED_KEYS);
			
			prepared.setInt(1, d.getId());
			prepared.setString(2, d.getName());
			
			int rowsAffected = prepared.executeUpdate();
			if(rowsAffected == 0) {
				throw new SQLException("No rows were affected");
			}
			else {
				System.out.println("Insert successful, rows affected: " + rowsAffected);
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
	public void update(Department d) {
		PreparedStatement prepared = null;
		
		try {
			prepared = connection.prepareStatement(	
					"UPDATE department " 
					+ "SET Name = ? " 
					+ "WHERE Id = ? ");
			
			prepared.setString(1, d.getName());
			prepared.setInt(2, d.getId());
		
			
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
					"DELETE FROM department " 
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
	public Department findById(int id) {
		PreparedStatement prepared = null;
		ResultSet rs = null;
		
		try {
			prepared = connection.prepareStatement(	
					"SELECT * FROM department WHERE Id = ?");
		
			prepared.setInt(1, id);
			rs = prepared.executeQuery();
			if(rs.next()) {
				Department department = instantiateDepartment(rs);	
				return department;
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
	
	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department(); 
		dep.setId(rs.getInt("Id")); 
		dep.setName(rs.getString("Name")); 
		return dep;
	}

	@Override
	public List<Department> findAll() {
		PreparedStatement prepared = null;
		ResultSet rs = null;
		
		try {
			prepared = connection.prepareStatement(	
					"SELECT * " 
					+ "FROM department " 
					);
			
			rs = prepared.executeQuery();
			
			List<Department> departments = new ArrayList<>();
			while(rs.next()) {							
				Department dep = instantiateDepartment(rs);			
				departments.add(dep);
			}
			return departments;
			
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
