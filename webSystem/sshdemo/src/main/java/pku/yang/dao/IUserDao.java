package pku.yang.dao;

import pku.yang.model.User;
import pku.yang.model.Student;
import pku.yang.model.Teacher;
import pku.yang.tool.Pagination;

public interface IUserDao {
	
	User getByID(String id);
	void save(User user);
	void deleteUser(String userID);
	void saveStudent(Student student);
	void saveTeacher(Teacher teacher);
	void deletestudent(String id);
	void deleteTeacher(String id);
	Student getStudentByID(String id);
	Teacher getTeacherByID(String id);
	Pagination<User> findUsers(String type,String id,String name,int page, int pagesize);
}
