package application;


import java.util.Date;
import java.util.List;
import java.util.Scanner;

import model.dao.DAOFactory;
import model.dao.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

public class Main {

	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		
		
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
		
		System.out.println();

		System.out.println("--- TEST : findAll Seller ---");
		List<Seller> sellersAll = sellerDAO.findAll();
		for(Seller sellerTemp : sellersAll)
			System.out.println(sellerTemp);
		
		System.out.println();

		System.out.println("--- TEST : Insert Seller ---");
		Seller sellerNew = new Seller("Martin", "martin@gmail.com", new Date(), 4000, department);
		sellerDAO.insert(sellerNew);
		System.out.println("Insert successful, seller with id: " + sellerNew);
		
		System.out.println();

		System.out.println("--- TEST : Update Seller ---");
		seller = sellerDAO.findById(1);
		seller.setName("Bob Davies");
		sellerDAO.update(seller);
		System.out.println("Update successful ");
		
		System.out.println();

		System.out.println("--- TEST : Delete Seller ---");
		System.out.println("Inform an id to delete: ");
		int id = scanner.nextInt();
		
		sellerDAO.deleteById(id);;
		System.out.println("Delete successful ");
		
		scanner.close();
	}

}
