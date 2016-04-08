package pku.yang.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pku.yang.dao.imp.UserDao;
import pku.yang.model.Student;
import pku.yang.model.Teacher;
import pku.yang.model.User;
import pku.yang.model.UserType;
import pku.yang.service.IUserService;
import pku.yang.tool.Pagination;

/**
 * 
 * @author summer
 *
 */
@Service
public class UserService implements IUserService{
	@Autowired
	private UserDao userdao;

	/*
	 * (non-Javadoc)
	 * @see pku.yang.service.IUserService#login(java.lang.String, java.lang.String)
	 */
	public User login(String id, String password) throws Exception {
		User user;
		try{
			user =userdao.getByID(id); 
		}
		catch(Exception e){
			throw new Exception("no user found");
		}
		if(user!= null && password.equals(user.getPassword())){
			return user;
		}
		return null;	
	}

	/*
	 * (non-Javadoc)
	 * @see pku.yang.service.IUserService#findUsers(java.lang.String, java.lang.String, java.lang.String, int, int)
	 */
	@Override
	public Pagination<User> findUsers(String type, String userID, String name,
			int page, int pagesize) throws Exception {
		Pagination<User> pagination;
		try{
			pagination = userdao.findUsers(type, userID, name,page,pagesize);
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("no user found");
		}
		return pagination;
	}

	/*
	 * (non-Javadoc)
	 * @see pku.yang.service.IUserService#findStudentInfo(java.lang.String)
	 */
	@Override
	public Student findStudentInfo(String userID) {
			return userdao.getStudentByID(userID);
	}
	
	/*
	 * (non-Javadoc)
	 * @see pku.yang.service.IUserService#findTeaherInfo(java.lang.String)
	 */
	@Override
	public Teacher findTeaherInfo(String userID){
		return userdao.getTeacherByID(userID);
	}

	/*
	 * (non-Javadoc)
	 * @see pku.yang.service.IUserService#savestudent(java.lang.String, java.lang.String, int, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.util.List)
	 */
	@Override
	public void savestudent(String id, String name, int age, String teacherID,
			String department, String academy, String studyGroup,
			String courses) {
		Student student = new Student();
		student.setId(id);
		student.setName(name);
		student.setAge(age);
		student.setTeacherID(teacherID);
		student.setDepartment(department);
		student.setStudygroup(studyGroup);
		student.setAcademy(academy);
		student.setCouses(courses);
		userdao.saveStudent(student);
	}

	/*
	 * (non-Javadoc)
	 * @see pku.yang.service.IUserService#saveTeacher(java.lang.String, java.lang.String, int, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.util.List)
	 */
	@Override
	public void saveTeacher(String id, String name, int age, String title,
			String duty, String department, String studyGroup,
			String courses) {
		Teacher teacher = new Teacher();
		teacher.setId(id);
		teacher.setName(name);
		teacher.setAge(age);
		teacher.setTitle(title);
		teacher.setDuty(duty);
		teacher.setDepartment(department);
		teacher.setStudyGroup(studyGroup);
		teacher.setCourses(courses);
		userdao.saveTeacher(teacher);
	}

	/*
	 * (non-Javadoc)
	 * @see pku.yang.service.IUserService#deleteUser(java.lang.String, java.lang.String)
	 */
	@Override
	public void deleteUser(String userID,String type) {
		if(type.equals(UserType.Student.toString())){
			userdao.deletestudent(userID);
		}
		else if(type.equals(UserType.Teacher.toString())){
			userdao.deleteTeacher(userID);
		}
		userdao.deleteUser(userID);
	}

	/*
	 * (non-Javadoc)
	 * @see pku.yang.service.IUserService#addStudent(java.lang.String, java.lang.String, int, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void addStudent(String id, String name, int age, String teacherID,
			String department, String academy, String studyGroup,
			String courses) {
		//TODO  ID uniqueness֤
		this.savestudent(id, name, age, teacherID, department, academy, studyGroup, courses);
		this.addUser(id, name, "0");
		
	}

	/*
	 * (non-Javadoc)
	 * @see pku.yang.service.IUserService#addTeacher(java.lang.String, java.lang.String, int, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void addTeacher(String id, String name, int age, String title,
			String duty, String department, String studyGroup,
			String courses) {
		//TODO  ID uniqueness֤
		this.saveTeacher(id, name, age, title, duty, department, studyGroup, courses);
		this.addUser(id, name, "1");
		
	}
	
	private void addUser(String id,String name,String type){
		//TODO  password strategy
		User user = new User();
		user.setUserID(id);
		user.setUsername(name);
		user.setType(type);
		user.setPassword("666666");
		userdao.save(user);
	}

	/*
	 * (non-Javadoc)
	 * @see pku.yang.service.IUserService#isExistUser(java.lang.String)
	 */
	@Override
	public boolean isExistUser(String id) {
		User user;
		try{
			user = userdao.getByID(id);
		}
		catch(Exception e){
			return false;
		}
		return (user != null);
	}
}
