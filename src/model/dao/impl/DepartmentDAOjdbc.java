package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
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
		
	}

	@Override
	public void deleteById(int id) {
		
	}

	@Override
	public Department findById(int id) {
		return null;
	}

	@Override
	public List<Department> findAll() {
		return null;
	}

}
