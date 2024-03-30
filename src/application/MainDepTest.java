package application;

import java.util.List;
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
		
		System.out.println("");
		
		System.out.println("--- TEST : Update Department ---");
		Department department = departmentDAO.findById(1);
		department.setName("Movies");
		departmentDAO.update(department);
		System.out.println("Update successful ");
		
		System.out.println("");
		
		System.out.println("--- TEST : Delete Department ---");
		System.out.println("Inform an id to delete: ");
		int id = scanner.nextInt();
		departmentDAO.deleteById(id);;
		System.out.println("Delete successful ");
		
		System.out.println();
		
		System.out.println("--- TEST : findAll Department ---");
		List<Department> departmentsAll = departmentDAO.findAll();
		for(Department dep : departmentsAll)
			System.out.println(dep);

		System.out.println("");

		scanner.close();
	}

}
