package pku.yang.dao.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import pku.yang.dao.IUserDao;
import pku.yang.model.User;

@Repository
public class UserDao implements IUserDao {
	@Autowired
	private HibernateTemplate hibernateTemplate;

	public User getByID(String id) {
		return (User) hibernateTemplate.get(User.class, id);
	}	

}
