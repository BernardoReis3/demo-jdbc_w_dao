package application;

import java.util.Date;

import model.dao.DAOFactory;
import model.dao.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

public class Main {

	public static void main(String[] args) {

		Department dep = new Department(3, "Fashion");
		System.out.println(dep);
		
		Seller seller = new Seller(22, "Tom", "tom@gmail.com", new Date(), 3500, dep);
		System.out.println(seller);
		
		
		SellerDAO sellerDAO = DAOFactory.createSellerDAO();
	}

}
