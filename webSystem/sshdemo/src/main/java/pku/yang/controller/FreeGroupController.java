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
import pku.yang.model.Message;
import pku.yang.service.IFolderService;
import pku.yang.service.IFreeGroupService;
import pku.yang.service.IMessageService;

@Controller
@RequestMapping("/freegroup")
public class FreeGroupController {

	@Autowired
	private IFreeGroupService freeGroupService;

	@Autowired
	private IMessageService messageservice;
	// 将需要的服务注解进来
	@Autowired
	private IFolderService folderService;

	// 创建自由群组
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add_fg(String fg_id, String fg_name, String fg_manager, String fg_userlist, String storgeid,
			HttpServletRequest request) {
		freeGroupService.add_fg(fg_id, fg_name, fg_manager, fg_userlist, storgeid);
		String[] user_id = fg_userlist.trim().split("，");
		System.out.println(fg_userlist);
		for (int i = 0; i < user_id.length; i++) {
			System.out.println(user_id.length);
			System.out.println(user_id[i] + " " + fg_id);
			messageservice.add_mess(String.valueOf(i), user_id[i], fg_id, "0");
			// 增加消息列表
		}
		// folderService.addFolder(id, name, fatherId, storageId, storageType,
		// whetherRoot, creater, createDate, shareType, integrityType);
		//创建一个自由空间跟目录，保存目录id，可以在创建自由空间前创建，然后将id存入自由空间。
		//将id放回给用户，同时其他用户同意加入后，将id放回给各个用户
		
		//建一个

		return "freegroupmanage/add_fg";
	}

	// 添加自由群组用户
	@RequestMapping(value = "/add_user", method = RequestMethod.POST)
	String add_user(@RequestParam String fg_id, @RequestParam String user_id) {
		freeGroupService.add_user(fg_id, user_id);
		return "freegroupmanage/add_user";
	}

	
	//自由群组列表
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String listFg(Model model) {
		int code = 200;
		List<FreeGroup> list = freeGroupService.fg_list();
		System.out.println("ssssss");
		System.out.println("controller:" + list);
		model.addAttribute("fglist", list);
		JSONObject resultjson = new JSONObject();
		JSONArray dorjsonarray = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			JSONObject json1 = new JSONObject();
			json1.put("id", list.get(i).getFg_id());
			json1.put("name", list.get(i).getFg_name());
			dorjsonarray.add(json1);
		}
		resultjson.put("code", code);
		resultjson.put("content", dorjsonarray);
		model.addAttribute("jsonArray", resultjson);
		return "freegroupmanage/list_fg";
	}

	// 删除自由群组
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete_fg(String fg_id) {
		
		freeGroupService.delete_fg(fg_id);
		return "freegroupmanage/delete_fg";
	}

	// 查找消息
	@RequestMapping(value = "/searchmess", method = RequestMethod.GET)
	public String search_mess(Model model, @RequestParam String user_id) {
		List<Message> list_usermess = messageservice.search_mess_byuser(user_id);
		int code = 200;
		model.addAttribute("list1", list_usermess);
		for (int i = 0; i < list_usermess.size(); i++) {
			System.out.println(i);
			System.out.println(list_usermess.get(i).toString());
		}
		JSONObject resultjson = new JSONObject();
		JSONArray dorjsonarray = new JSONArray();
		for (int i = 0; i < list_usermess.size(); i++) {
			JSONObject json1 = new JSONObject();
			json1.put("mess_id", list_usermess.get(i).getMess_id());
			json1.put("user_id", list_usermess.get(i).getUser_id());
			json1.put("fg_id", list_usermess.get(i).getFg_id());
			json1.put("state", list_usermess.get(i).getState());

			dorjsonarray.add(json1);
		}
		resultjson.put("code", code);
		resultjson.put("content", dorjsonarray);
		model.addAttribute("jsonArray", resultjson);
		System.out.println(resultjson.toJSONString());
		return "freegroupmanage/search_mess_byuser";
	}

	// 根据应答，修改消息状态
	@RequestMapping(value = "/respondmess", method = RequestMethod.POST)
	public String respond_mess(@RequestParam String mess_id, @RequestParam String state) {

		if (state.equals("1")) {
			System.out.println("state=======" + state);
			System.out.println("messid=======" + mess_id);
			//更新状态
			messageservice.save_mess_state(mess_id, state);

		}
		if (state.equals("0")) {
			//删除该用户消息，代表该用户不在该群组
			messageservice.delete_mess(mess_id);
		}
		return "freegroupmanage/respond_mess";
	}

}
