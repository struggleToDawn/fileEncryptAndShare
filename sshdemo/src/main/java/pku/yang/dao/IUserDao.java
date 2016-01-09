package pku.yang.dao;

import pku.yang.model.User;

public interface IUserDao {
	
	User getByID(String id);

}
