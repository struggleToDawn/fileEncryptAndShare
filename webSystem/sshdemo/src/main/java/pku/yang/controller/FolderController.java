package pku.yang.controller;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import pku.yang.model.Folder;
import pku.yang.service.IFolderService;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("/folder")
public class FolderController {
	@Autowired
	private IFolderService folderService;
	//-----add by shengxiaoran-----//
	//-----以下是正式的接口代码-----//
	@ResponseBody
	@RequestMapping(value = "/createFolder", method = RequestMethod.POST)
	public String createFolder(@RequestParam String id,
			@RequestParam String name,
			@RequestParam String fatherId,
			@RequestParam String storageId, 
			@RequestParam String storageType,
			@RequestParam String whetherRoot, 
			@RequestParam String creater,
			@RequestParam String shareType,
			@RequestParam String integrityType) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String createDate = format.format(date);
		folderService.addFolder(id, name, fatherId, storageId, storageType, whetherRoot, creater, createDate, shareType, integrityType);
		JSONObject result = new JSONObject();
		result.put("result", 0);
		return result.toJSONString();
	}
	//-----以下是测试代码-----//
	//-----add by shengxiaoran-----//
	/**
	 * ����Ŀ¼������Ϣ 
	 * 
	 * @param id
	 * @param name
	 * @param fatherId
	 * @param storageId
	 * @param storageType
	 * @param whetherRoot
	 * @param creater
	 * @param createDate
	 * @param shareType
	 * @param integrityType
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String saveTeacher(@RequestParam String id,
			@RequestParam String name,
			@RequestParam String fatherId,
			@RequestParam String storageId, 
			@RequestParam String storageType,
			@RequestParam String whetherRoot, 
			@RequestParam String creater,
			@RequestParam String createDate,
			@RequestParam String shareType,
			@RequestParam String integrityType) {
		folderService.addFolder(id, name, fatherId, storageId, storageType, whetherRoot, creater, createDate, shareType, integrityType);
		/*System.out.println("ssssss");*/
		/*ModelAndView mav= new ModelAndView();
		mav.setViewName("folder/addsuccess");
		mav.addObject("Success",true);*/
		return "foldermanage/add_folder";
	}

	/**
	 * ��ѯĿ¼�б� TODO
	 * 
	 * @param id
	 * @param name
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String listFolder(Model model) {
		int code=200;
		List<Folder> list = folderService.FolderList();
		System.out.println("ssssss");
		System.out.println("controller:"+list);
		model.addAttribute("folderlist",list);
		JSONObject resultjson = new JSONObject();
		JSONArray dorjsonarray = new JSONArray();
		for(int i=0;i<list.size();i++){
			JSONObject json1 = new JSONObject();
			json1.put("id",list.get(i).getFolderID());
			json1.put("name", list.get(i).getName());
			dorjsonarray.add(json1);
		}
		resultjson.put("code",code);
		resultjson.put("content", dorjsonarray);
		model.addAttribute("jsonArray",resultjson);
		return "foldermanage/list_folder";
	}

	
	/**
	 * Ŀ¼������ TODO
	 * 
	 * @param id
	 * @param name
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value = "/rename", method = RequestMethod.GET)
	public String renameFolder(@RequestParam String id,
			@RequestParam String name) {
		Folder folder = folderService.findFolderInfo(id);
		folder.setName(name);
		folderService.saveFolder(folder);
		return "foldermanage/rename_folder";
	}
	
	/**
	 * Ŀ¼�ƶ� TODO
	 * 
	 * @param id
	 * @param fatherID
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value = "/move", method = RequestMethod.GET)
	public String moveFolder(@RequestParam String id,
			@RequestParam String fatherID) {
		Folder folder = folderService.findFolderInfo(id);
		folder.setFatherID(fatherID);
		folderService.saveFolder(folder);
		return "foldermanage/move_folder";
	}

	/**
	 * ɾ��Ŀ¼ TODO
	 * 
	 * @param id
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String deleteFolder(@RequestParam String id) {
		folderService.deleteFolder(id);
		return "foldermanage/delete_folder";
	}
}
