package pku.yang.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import pku.yang.model.Student;
import pku.yang.model.Teacher;
import pku.yang.model.User;
import pku.yang.service.IUserService;
import pku.yang.tool.Pagination;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private IUserService userService;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(Model model, String id, String password,
			HttpServletRequest request) {
		User user;
		try {
			user = userService.login(id, password);
			model.addAttribute(user);
			request.getSession().setAttribute("sessionname", id);
			return "home";
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
			return "error";
		}
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(Model model, String id, String password,
			HttpServletRequest request) {
	
			request.getSession().removeAttribute("sessionname");
			
			return "../login";
		
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addUser(Model model) {
		model.addAttribute("readonly", false);
		return "usermanage/add_user";
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String getList(Model model) {
		return this.listUser(model, "", "", "", 1, 10);
	}

	/**
	 * ��ѯ�û��б�
	 * 
	 * @param type
	 * @param name
	 * @param userID
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public String listUser(Model model, @RequestParam(defaultValue = "") String type,
			@RequestParam(defaultValue = "") String name,
			@RequestParam(defaultValue = "") String userID,
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int pagesize) {
		Pagination<User> users;
		try {
			users = userService.findUsers(type, userID, name, page, pagesize);
			model.addAttribute("userList", users.getList());
			model.addAttribute("pagenum", users.getPagenum());
			model.addAttribute("pagesize", users.getPagesize());
			model.addAttribute("page", users.getPage());
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		model.addAttribute("type", type);
		model.addAttribute("name", name);
		model.addAttribute("userID",userID);
		return "usermanage/list_user";
	}
	
	@RequestMapping(value = "/edit/{userID}/{type}", method = RequestMethod.GET)
	public String editUser(Model model, @PathVariable String userID,
			@PathVariable String type){
		if (type.equals("0")) {
			Student student = userService.findStudentInfo(userID);
			model.addAttribute("user", student);
		} else if (type.equals("1")) {
			Teacher teacher = userService.findTeaherInfo(userID);
			model.addAttribute("user", teacher);
		}
		model.addAttribute("type", type);
		return "usermanage/edit_user";
	}

	/**
	 * �鿴�û���Ϣ
	 * 
	 * @param userID
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "/info/{userID}/{type}", method = RequestMethod.GET)
	public String UserInfo(Model model, @PathVariable String userID,
			@PathVariable String type) {
		if (type.equals("0")) {
			Student student = userService.findStudentInfo(userID);
			model.addAttribute("user", student);
		} else if (type.equals("1")) {
			Teacher teacher = userService.findTeaherInfo(userID);
			model.addAttribute("user", teacher);
		}
		model.addAttribute("type", type);
		return "usermanage/info_user";
	}

	/**
	 * ����ѧ��������Ϣ
	 * 
	 * @param id
	 * @param name
	 * @param age
	 * @param teacherID
	 * @param department
	 * @param academy
	 * @param studyGroup
	 * @param courses
	 * @return
	 */
	@RequestMapping(value = "/student", method = RequestMethod.POST)
	public String saveStudent(Model model,@RequestParam String id,
			@RequestParam String name,
			@RequestParam(defaultValue = "0") int age,
			@RequestParam String teacherID, @RequestParam String department,
			@RequestParam String academy, @RequestParam String studyGroup,
			@RequestParam String courses) {

		if (userService.isExistUser(id)) {
			userService.savestudent(id, name, age, teacherID, department,
					academy, studyGroup, courses);
		} else {
			userService.addStudent(id, name, age, teacherID, department,
					academy, studyGroup, courses);
		}

		return listUser(model, "", "", "", 1, 10);
	}

	/**
	 * �����ʦ������Ϣ
	 * 
	 * @param id
	 * @param name
	 * @param age
	 * @param title
	 * @param duty
	 * @param deparment
	 * @param studyGroup
	 * @param courses
	 * @return
	 */
	@RequestMapping(value = "/teacher", method = RequestMethod.POST)
	public String saveTeacher(Model model,@RequestParam String id,
			@RequestParam String name,
			@RequestParam(defaultValue = "0") int age,
			@RequestParam String title, @RequestParam String duty,
			@RequestParam String department, @RequestParam String studyGroup,
			@RequestParam String courses) {
		if (userService.isExistUser(id)) {
			userService.saveTeacher(id, name, age, title, duty, department,
					studyGroup, courses);
		} else {
			userService.addTeacher(id, name, age, title, duty, department,
					studyGroup, courses);
		}
		return listUser(model, "", "", "", 1, 10);
	}

	/**
	 * ɾ���û���Ϣ
	 * 
	 * @param pid
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView deleteUser(@RequestParam String id) {
		System.out.println(id);
		userService.deleteUser(id);
		return null;
	}


}
