package pku.yang.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import pku.yang.model.FreeGroup;
import pku.yang.service.IFreeGroupService;

@Controller
@RequestMapping("/freegroup")
public class FreeGroupController {
	
	@Autowired
	private IFreeGroupService freeGroupService;
	
	//@Autowired
	//需要哪层的服务，就把它的接口注解进来就可以。

	//增加自由群组
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add_fg( String fg_id, 
			 String fg_name, 
			 String fg_manager, 
			 String fg_userlist, 
			 String storgeid,
			 HttpServletRequest request) {
		freeGroupService.add_fg(fg_id, fg_name, fg_manager, fg_userlist, storgeid);
		return "freegroupmanage/add_fg";
	}
	
	
	
	//增加自由群组成员
	@RequestMapping(value = "/add_user", method = RequestMethod.POST)
	String add_user(@RequestParam String fg_id, 
			@RequestParam String user_id) {
		freeGroupService.add_user(fg_id, user_id);
		return "freegroupmanage/add_user";
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String listFg(Model model) {
		int code=200;
		List<FreeGroup> list = freeGroupService.fg_list();
		System.out.println("ssssss");
		System.out.println("controller:"+list);
		model.addAttribute("fglist",list);
		JSONObject resultjson = new JSONObject();
		JSONArray dorjsonarray = new JSONArray();
		for(int i=0;i<list.size();i++){
			JSONObject json1 = new JSONObject();
			json1.put("id",list.get(i).getFg_id());
			json1.put("name", list.get(i).getFg_name());
			dorjsonarray.add(json1);
		}
		resultjson.put("code",code);
		resultjson.put("content", dorjsonarray);
		model.addAttribute("jsonArray",resultjson);
		return "freegroupmanage/list_fg";
	}
	
	//删除自由群组
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete_fg(@RequestParam String fg_id) {
		freeGroupService.delete_fg(fg_id);
		return "freegroupmanage/delete_fg";
	}

}
