package model.dao;

import java.util.List;

import model.entities.Department;
import model.entities.Seller;

public interface SellerDAO {
	
	void insert(Seller d);
	void update(Seller d);
	void deleteById(int id);
	Seller findById(int id);
	List<Seller> findAll();
	List<Seller> findByDepartment(Department department);

}