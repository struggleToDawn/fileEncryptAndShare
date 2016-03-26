package pku.yang.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pku.yang.dao.imp.UserDao;
import pku.yang.model.Student;
import pku.yang.model.Teacher;
import pku.yang.model.User;
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
			throw new Exception("�û�������");
		}
		if(user!= null && password.equals(user.getPassword())){
			return user;
		}
		return null;	
	}

	@Override
	public Pagination<User> findUsers(String type, String userID, String name,
			int page, int pagesize) throws Exception {
		Pagination<User> pagination;
		try{
			pagination = userdao.findUsers(type, userID, name,page,pagesize);
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("���Ϸ���ҳ��");
		}
		return pagination;
	}

	@Override
	public Student findStudentInfo(String userID) {
			return userdao.getStudentByID(userID);
	}
	
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

	@Override
	public void deleteUser(String pid) {
		// TODO Auto-generated method stub
//		userdao.deleteUser(pid);
//		userdao.deleteTeacher(pid);
		userdao.deletestudent(pid);
		
	}

	@Override
	public void addStudent(String id, String name, int age, String teacherID,
			String department, String academy, String studyGroup,
			String courses) {
		this.savestudent(id, name, age, teacherID, department, academy, studyGroup, courses);
		this.addUser(id, name, "0");
		
	}

	@Override
	public void addTeacher(String id, String name, int age, String title,
			String duty, String department, String studyGroup,
			String courses) {
		this.addTeacher(id, name, age, title, duty, department, studyGroup, courses);
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
