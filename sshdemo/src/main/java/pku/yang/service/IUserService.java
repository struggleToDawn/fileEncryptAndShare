package pku.yang.service;

import pku.yang.model.User;

public interface IUserService {
	User login(String id, String name);
}
