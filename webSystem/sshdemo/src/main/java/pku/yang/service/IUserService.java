package pku.yang.service;

import pku.yang.model.Student;
import pku.yang.model.Teacher;
import pku.yang.model.User;
import pku.yang.tool.Pagination;

public interface IUserService {
	User login(String id, String name) throws Exception;

	Pagination<User> findUsers(String type, String userID, String name, int page,
			int pagesize) throws Exception;

	Student findStudentInfo(String userID);
	
	Teacher findTeaherInfo(String userID);

	void savestudent(String id, String name, int age, String teacherID,
			String department, String academy, String studyGroup,
			String courses);

	void addStudent(String id, String name, int age, String teacherID,
			String department, String academy, String studyGroup,
			String courses);

	void saveTeacher(String id, String name, int age, String title,
			String duty, String department, String studyGroup,
			String courses);

	void addTeacher(String id, String name, int age, String title,
			String duty, String department, String studyGroup,
			String courses);

	void deleteUser(String pid);
	
	boolean isExistUser(String id);
}
