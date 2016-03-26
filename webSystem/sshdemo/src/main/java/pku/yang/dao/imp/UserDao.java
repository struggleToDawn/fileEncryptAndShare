package pku.yang.dao.imp;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import pku.yang.dao.IUserDao;
import pku.yang.model.User;
import pku.yang.model.Student;
import pku.yang.model.Teacher;
import pku.yang.tool.Pagination;

@Repository
public class UserDao implements IUserDao {
	@Autowired
	private HibernateTemplate hibernateTemplate;

	public User getByID(String id) {
		User user = new User();
		user.setUserID(id);
		User result = (User) hibernateTemplate.findByExample(user).get(0);
		return result;
	}

	@Override
	public void save(User user) {
		hibernateTemplate.saveOrUpdate(user);
	}

	@Override
	public void deleteUser(String id) {
		hibernateTemplate.delete(hibernateTemplate.get(User.class, id));
		
	}

	@Override
	public void saveStudent(Student student) {
		hibernateTemplate.saveOrUpdate(student);
		hibernateTemplate.flush();
		
	}

	@Override
	public void saveTeacher(Teacher teacher) {
		hibernateTemplate.saveOrUpdate(teacher);
		
	}

	@Override
	public void deletestudent(String id) {
		hibernateTemplate.delete(hibernateTemplate.get(Student.class, id));
		
	}

	@Override
	public void deleteTeacher(String id) {
		hibernateTemplate.delete(hibernateTemplate.get(Student.class, id));
		
	}

	@Override
	public Student getStudentByID(String id) {
		Student student = (Student)hibernateTemplate.get(Student.class, id);
		return student;
	}

	@Override
	public Teacher getTeacherByID(String id) {
		Teacher teacher = (Teacher)hibernateTemplate.get(Teacher.class, id);
		return teacher;
	}

	@Override
	public Pagination<User> findUsers(String type, String id, String name,int page,int pagesize) {
		Pagination<User> pagination = new Pagination<>();
		DetachedCriteria criteria = DetachedCriteria.forClass(User.class); 
		StringBuilder query = new StringBuilder();
		query.append("select count(*) from User as user where 1=1");
		if(type != null && !type.equals("")){
			criteria.add(Restrictions.eq("type", type));
			query.append("and user.type ="+type);
		}
		if(id != null && !id.equals("")){
			criteria.add(Restrictions.like("userID", id, MatchMode.ANYWHERE));
			query.append("and user.userID like '%"+id+"%'");
		}
		if(name != null && !name.equals("")){
			criteria.add(Restrictions.like("username", name, MatchMode.ANYWHERE));
			query.append("and user.username like '%"+name+"%'");
		}
		int count =((Long) hibernateTemplate.iterate(query.toString()).next()).intValue();
		pagination.setPagenum(count/pagesize+(count%pagesize == 0 ? 0:1));
		pagination.setPage(page);
		pagination.setPagesize(pagesize);
		@SuppressWarnings("unchecked")
		List<User> users = (List<User>) hibernateTemplate.findByCriteria(criteria, (page-1)*pagesize, pagesize);
		pagination.setList(users);
		return pagination;
	}	

}
