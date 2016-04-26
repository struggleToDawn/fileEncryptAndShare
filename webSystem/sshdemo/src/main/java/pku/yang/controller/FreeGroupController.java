package pku.yang.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import pku.yang.model.FreeGroup;
import pku.yang.model.Message;
import pku.yang.service.IFileService;
import pku.yang.service.IFolderService;
import pku.yang.service.IFreeGroupService;
import pku.yang.service.IMessageService;
import pku.yang.service.ISpaceService;
import pku.yang.tool.*;

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
	@Autowired
	private ISpaceService spaceservice;
	
	@Autowired
	private IFileService fileService;

	// 创建自由群组
	@ResponseBody
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add_fg(String token,String fg_name, String fg_userlist, String storageid,
			HttpServletRequest request) throws Exception {
		//String uuid=DESUtil.getUidBytoken(token);
		String uuid="12";
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String ctime = df.format(new Date());
		String rootid=folderService.createFolder(uuid, fg_name, "2", ctime);
		spaceservice.addSpace("storageid", fg_name, 200, rootid);
		String storage_id="111";
		String fg_id=freeGroupService.add_fg(fg_name, uuid, fg_userlist, storageid);
		System.out.println(fg_id);
		messageservice.add_mess(uuid, fg_id, "1");//将创建者加入到消息表，状态设置为1
		String[] user_id = fg_userlist.trim().split(",");
		System.out.println(fg_userlist);
		for (int i = 0; i < user_id.length; i++) {
			System.out.println(user_id.length);
			System.out.println(user_id[i] + " " + fg_id);
			messageservice.add_mess(user_id[i], fg_id, "0");
			// 增加消息列表
		}

	//	return "freegroupmanage/add_fg";
		int code=0;
		JSONObject resultjson = new JSONObject();
		JSONObject datajson = new JSONObject();
		datajson.put("fg_id", fg_id);
		datajson.put("storageid", storageid);
		datajson.put("rootid", rootid);
		resultjson.put("code", code);
		resultjson.put("data",datajson );
		return resultjson.toJSONString();
	}

	// 添加自由群组用户
	@RequestMapping(value = "/add_user", method = RequestMethod.POST)
	String add_user(@RequestParam String fg_id, @RequestParam String user_id) {
		freeGroupService.add_user(fg_id, user_id);
		return "freegroupmanage/add_user";
	}

	//得到该用户所属的所有自由群组
	@ResponseBody
	@RequestMapping(value = "/fgOfUser", method = RequestMethod.GET)
	public String fgOfUser(@RequestParam String token) throws Exception 
	{
		String uuid=DESUtil.getUidBytoken(token);
		//找到用户id为uuid且状态为1的message
		List<Message> list=messageservice.search_user_fg(uuid);
		int code=0;
		JSONObject resultjson = new JSONObject();
		JSONArray dorjsonarray = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			JSONObject json1 = new JSONObject();
			String fg_id=list.get(i).getFg_id();
			json1.put("fg_id", fg_id);
			FreeGroup fg= freeGroupService.search_fg_info(fg_id);
			json1.put("fg_name",fg.getFg_name());
			json1.put("storageid",fg.getStorgeid());
			//spaceservice.fg.getStorgeid()
			String rootid="abc";
			json1.put("rootid",rootid);
			dorjsonarray.add(json1);
		}
		resultjson.put("code", code);
		resultjson.put("content", dorjsonarray);
		//return "freegroupmanage/list_fg";
		return resultjson.toJSONString();
	}
	
	//所有的自由群组列表，暂时没用
	@ResponseBody
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String listFg(Model model) {
		int code = 0;
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
		//return "freegroupmanage/list_fg";
		return resultjson.toJSONString();
	}

	// 删除自由群组
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete_fg(String fg_id) {
		
		freeGroupService.delete_fg(fg_id);//删除群组信息
		FreeGroup fg=freeGroupService.search_fg_info(fg_id);
		
		//删除空间信息
		spaceservice.deleteSpace(fg.getStorgeid());
		
		//删除根目录
		folderService.deleteFolder(fg.getStorgeid());
		
		//删除消息表中的成员记录
		List<Message> list_fgmess = messageservice.search_mess_byfg(fg_id);
		JSONObject resultjson = new JSONObject();
		for (int i = 0; i < list_fgmess.size(); i++) {
			String mess_id=list_fgmess.get(i).getMess_id();
			messageservice.delete_mess(mess_id);		
		}	
		int code=0;
		boolean bool=true;
		resultjson.put("code", code);
		resultjson.put("data", bool);
		return resultjson.toJSONString();
	}

	
	// 查找消息
	@RequestMapping(value = "/searchmess", method = RequestMethod.GET)
	public String search_mess(Model model, @RequestParam String user_id) {
		List<Message> list_usermess = messageservice.search_mess_byuser(user_id);
		int code = 0;
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
	
	//文件夹和文件？？？
	//分享文件,类似上传  token,parentid,fileid，通过fileid可以定位到原文件，然后保存一条记录
	
	
	//删除文件 token,fileid（判断文件owner），提供service根据fileid找到file对象
	@ResponseBody
	@RequestMapping(value = "/deletefile", method = RequestMethod.GET)
	public String deleteFile(@RequestParam String token,
			@RequestParam String fileId) {
		JSONObject result = new JSONObject();
		//--get user id--//
		String uuid = "";
		try{
			uuid = DESUtil.getUidBytoken(token);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		fileService.deleteFile(fileId);
		result.put("code", 0);
		result.put("data", "success");
		return result.toJSONString();
	}
	
	
	//下载文件token,fileid
	//
	@ResponseBody
	@RequestMapping(value = "/downloadfile", method = RequestMethod.POST)
	public String downloadFile(@RequestParam String token,
			@RequestParam String fileId) {
		JSONObject result = new JSONObject();
		//--get user id--//
		String userId = "";
		try{
			userId = DESUtil.getUidBytoken(token);
		}catch(Exception e){
			e.printStackTrace();
		}
		result.put("code", 0);
		result.put("data", "success");
		return result.toJSONString();
	}

}
