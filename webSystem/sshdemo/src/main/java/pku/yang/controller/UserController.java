package pku.yang.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pku.yang.model.User;
import pku.yang.service.IUserService;



@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private IUserService userService;
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(Model model,String id, String password, HttpServletRequest request ){
		User user = userService.login(id, password);
		if(user == null) return "error";
		model.addAttribute(user);
		return "userInfo";
	}
	

}
