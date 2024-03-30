package application;

import java.util.Scanner;

import model.dao.DAOFactory;
import model.dao.DepartmentDAO;
import model.entities.Department;

public class MainDepTest {

	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		
		DepartmentDAO departmentDAO = DAOFactory.createDepartmentDAO();
		
		System.out.println("--- TEST : Insert Department ---");
		Department DepartmentNew = new Department("Gardening");
		departmentDAO.insert(DepartmentNew);
		scanner.close();
	}

}
