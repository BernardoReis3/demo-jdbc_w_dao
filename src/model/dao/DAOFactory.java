package model.dao;

import model.dao.impl.SellerDAOjdbc;

public class DAOFactory {

	public static SellerDAO createSellerDAO() {
		return new SellerDAOjdbc();
	}
}
