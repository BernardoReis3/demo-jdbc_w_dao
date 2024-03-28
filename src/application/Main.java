package application;


import java.util.List;

import model.dao.DAOFactory;
import model.dao.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

public class Main {

	public static void main(String[] args) {
		
		
		SellerDAO sellerDAO = DAOFactory.createSellerDAO();
		
		System.out.println("--- TEST : findById Seller ---");
		Seller seller = sellerDAO.findById(3);		
		System.out.println(seller);
		
		System.out.println();
		
		System.out.println("--- TEST : findByDepartment Seller ---");
		Department department = new Department(2, null);
		List<Seller> sellers = sellerDAO.findByDepartment(department);
		for(Seller sellerTemp : sellers)
			System.out.println(sellerTemp);
	}

}
