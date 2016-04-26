package pku.yang.controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


import pku.yang.model.BusinessGroup;
import pku.yang.model.User;
import pku.yang.service.IBusinessGroupService;
import pku.yang.service.ISpaceService;
import pku.yang.service.ITokenService;
import pku.yang.service.IUserService;
import pku.yang.tool.DESUtil;


@Controller
@RequestMapping("/businessGroup")
public class BusinessGroupController {

	@Autowired
	private IBusinessGroupService businessGroup;
	@Autowired
	private ISpaceService spaceService;
	@Autowired
	private IUserService userService;
	
	@ResponseBody 
	@RequestMapping(value = "/ttt", method = RequestMethod.GET)
	public String ttt(
			@RequestParam String token
//			@RequestParam String uAttrs,
////			@RequestParam int storageId, 
//			@RequestParam String adminAttrs
			) throws Exception {
		
		
		String groupId ="";
		
		String uid = DESUtil.getUidBytoken("As6VkJPshb2AfJediKXVbQ==");
		
		return uid;
		
//		userService.addUserGroup("1501211004", "3,4,5");
		
//		
//		List<String> list = new ArrayList<String>();
//				
//		list = userService.getUserGroup("1501211004");
//		
//		for(int i= 0; i< list.size();i++){
//			groupId += "," + list.get(i);
		
//		}
//		return groupId.substring(1);
		
		
//		return "123";
//		return spaceService.findById("402881fb54433ecd0154433ed4d00000").getRoot();
		
		
//		return	spaceService.addSpace("ttt", 23, "12");

		
		
		//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		String ctime = df.format(new Date());
//		
//		businessGroup.addGroup(name,"3",22,adminAttrs,uAttrs,ctime);
		
//		return "home";
	}
	
	
	
	
	
	@ResponseBody 
	@RequestMapping(value="getServiceGroupFile",method=RequestMethod.GET)
	public String getServiceGroupFile(@RequestParam String token){
		
		
		
		JSONArray dorjsonarray = new JSONArray();
		JSONObject jsonData = new JSONObject();
		
		
		
		JSONObject obj = new JSONObject();
		
		obj.put("name", "java");
		obj.put("root", 10);
		dorjsonarray.add(obj);
		jsonData.put("code", 0);
		jsonData.put("data", dorjsonarray);
		
		return jsonData.toJSONString();
	
//		return "aaa";
	}
	
	
	@RequestMapping(value = "/addGroup", method = RequestMethod.POST)
	public String addGroup(
			@RequestParam String name,
			@RequestParam String uAttrs,
//			@RequestParam int storageId, 
			@RequestParam String adminAttrs
			) {
		
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String ctime = df.format(new Date());
		
		
		
		
		businessGroup.addGroup(name,"3",22,adminAttrs,uAttrs,ctime);
		
		return "home";
	}
	
	
	
	

	@RequestMapping(value = "/updateGroup", method = RequestMethod.POST)
	public String updateGroup(@RequestParam String id,
			@RequestParam String name,
			@RequestParam String uAttrs,
			@RequestParam int storageId, 
			@RequestParam String adminAttrs
			) {
		
		
		
		BusinessGroup groupModel = businessGroup.findGroupInfo(id);
			
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String utime = df.format(new Date());
		
		groupModel.setName(name);
		groupModel.setUAttrs(uAttrs);
		groupModel.setStorageId(storageId);
		groupModel.setAdminAttrs(adminAttrs);
		groupModel.setUptime(utime);
		
		businessGroup.saveGroup(groupModel);
		
		return "home";
	}

	@ResponseBody 
	@RequestMapping(value = "/data", method = RequestMethod.GET)
	public String getData( ) {
	
		List<BusinessGroup> list = businessGroup.getBussinessGroupList();
		
		JSONArray dorjsonarray = new JSONArray();
		for(int i=0;i<list.size();i++){
			JSONObject jsonData = new JSONObject();
			jsonData.put("id",list.get(i).getID());
			jsonData.put("name", list.get(i).getName());
			jsonData.put("adminAttr", list.get(i).getAdminAttrs());
			jsonData.put("uAttr", list.get(i).getUAttrs());
			jsonData.put("storageId", list.get(i).getStorageId());
			jsonData.put("ctime", list.get(i).getCtime());
			jsonData.put("utime", list.get(i).getUtime());
			
			String operation = "<a href = 'update?id="+list.get(i).getID() + "'>edit</a>   <a href = 'delete?id="+list.get(i).getID() + "'>delete</a>";
			jsonData.put("operation", operation);
			dorjsonarray.add(jsonData);
		}
		
		return dorjsonarray.toJSONString();

	}

	
	@RequestMapping(value ="/getList" , method = RequestMethod.GET)
	public String getList(){
		
		return "groupmanage/list_group";
	}
	
	
	@RequestMapping(value ="/update" , method = RequestMethod.GET)
	public String update(Model model,@RequestParam String id){
		
		BusinessGroup groupModel = businessGroup.findGroupInfo(id);
		model.addAttribute("businessGroup",groupModel);
		
		return "groupmanage/update_group";
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String deleteFolder(@RequestParam String id) {
		businessGroup.deleteGroup(id);
		return "groupmanage/list_group";
	}


}
