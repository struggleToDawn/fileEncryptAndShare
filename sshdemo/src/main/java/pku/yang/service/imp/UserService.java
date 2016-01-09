package pku.yang.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pku.yang.dao.imp.UserDao;
import pku.yang.model.User;
import pku.yang.service.IUserService;

@Service
public class UserService implements IUserService{
	@Autowired
	private UserDao userdao;

	public User login(String id, String password) {
		User user = userdao.getByID(id);
		if(user!= null && password.equals(user.getPassword())){
			return user;
		}
		return null;	
	}

}
