package pku.yang.controller;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Stack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import pku.yang.model.File;
import pku.yang.model.Folder;
import pku.yang.service.IFileService;
import pku.yang.service.IFolderService;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import pku.yang.tool.DESUtil;
@Controller
@RequestMapping("/folder")
public class FolderController {
	@Autowired
	private IFolderService folderService;
	
	@Autowired
	private IFileService fileService;
	
	//完整性可选设置，通过发送token，文件id和”setTPA”，对文件完整性进行设置，添加记录，并返回成功信息。
	@ResponseBody
	@RequestMapping(value = "/setTPA", method = RequestMethod.POST)
	public String createFolder(@RequestParam String token,
			@RequestParam String fileid) {
		List<File> filelist = fileService.getFilesByUserId(token);
		for(int i=0;i<filelist.size();i++){
			if(filelist.get(i).getFolderId().equals(fileid)){
				
			}
		}
		try{
			String uid = DESUtil.getUidBytoken(token);
		}catch(Exception e){
			e.printStackTrace();
		}
		JSONObject result = new JSONObject();
		result.put("result", 0);
		return result.toJSONString();
	}
	
	//-----以下是正式的接口代码-----//
	@ResponseBody
	@RequestMapping(value = "/createdirectory", method = RequestMethod.POST)
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
	
	//------add by caoke----//
	//创建共享文件夹权限检查,发送token，父级目录id以及“Cancreatedirectory”，返回是否有管理员权限能够创建，//
	@ResponseBody
	@RequestMapping(value = "/Cancreatedirectory", method = RequestMethod.GET)
	public String Cancreatedirectory(@RequestParam String token,
			@RequestParam String parentid) {
		JSONObject result = new JSONObject();
		result.put("code", 0);
		result.put("data", true);
		return result.toJSONString();
	}
	
	//删除文件权限检查，发送文件id，token，“Candeletefile”关键字，返回是否有管理员权限能够删除//
	@ResponseBody
	@RequestMapping(value = "/Candeletefile", method = RequestMethod.GET)
	public String Candeletefile(@RequestParam String token,
			@RequestParam String Fileid) {
		JSONObject result = new JSONObject();
		result.put("code", 0);
		result.put("data", true);
		return result.toJSONString();
	}
	
	//删除文件夹权限检查，发送文件夹id，token，“CandeleteDirctory”关键字，返回是否有管理员权限能够删除，若能删除，将文件夹下的所有文件都删除掉
	@ResponseBody
	@RequestMapping(value = "/CandeleteDirctory", method = RequestMethod.GET)
	public String CandeleteDirctory(@RequestParam String token,
			@RequestParam String Fileid) {
		JSONObject result = new JSONObject();
		result.put("code", 0);
		result.put("data", true);
		return result.toJSONString();
	}
	
	//上传文件权限检查，发送父级目录id，token，“Canuploadfile”关键字，返回上传类型（ABE或TPA或普通）。
	@ResponseBody
	@RequestMapping(value = "/Canuploadfile", method = RequestMethod.GET)
	public String Canuploadfile(@RequestParam String token,
			@RequestParam String parentid) {
		JSONObject result = new JSONObject();
		Folder folder = folderService.findFolderInfo(parentid);
		String TPA = folder.getIntegrityType();
		result.put("code", 0);
		if(TPA.equals("1")){
			result.put("data", "TPA");
		}else{
			result.put("data", "normal");
		}
		
		return result.toJSONString();
	}
	
	//下载文件权限检查，发送文件id，token，“Candownloadfile”关键字，返回下载文件的类型（ABE或普通）
	@ResponseBody
	@RequestMapping(value = "/Candownloadfile", method = RequestMethod.GET)
	public String Candownloadfile(@RequestParam String token,
			@RequestParam String Fileid) {
		JSONObject result = new JSONObject();
		result.put("code", 0);
		result.put("data", "ABE");
		return result.toJSONString();
	}
	
	//文件完整性验证权限检查，发送文件id，token，“checkfile”关键字，返回该文件是否支持完整性验证
	@ResponseBody
	@RequestMapping(value = "/checkfile", method = RequestMethod.POST)
	public String checkfile(@RequestParam String token,
			@RequestParam String Fileid) {
		JSONObject result = new JSONObject();
		result.put("code", 0);
		result.put("data", true);
		return result.toJSONString();
	}
	
	//通过token以及“listAllFile”，返回该用户所有文件（包含所有空间文件）
		@ResponseBody
		@RequestMapping(value = "/listAllFile", method = RequestMethod.POST)
		public String listAllFile(@RequestParam String token) {
			JSONObject result = new JSONObject();
			JSONArray data = new JSONArray();
			result.put("code",0);
			try{
				String uid = DESUtil.getUidBytoken(token);
				List<File> filelist = fileService.getFilesByUserId(uid);
				for(int i=0;i<filelist.size();i++){
					JSONObject temp = new JSONObject();
					temp.put("fileid", filelist.get(i).getFile_id());
					temp.put("parentid", filelist.get(i).getFolderId());
					temp.put("filename", filelist.get(i).getFile_name());
					temp.put("type","file");
					temp.put("date", filelist.get(i).getUpload_time());
					temp.put("size",0);
					temp.put("TPA",0);
					temp.put("share",0);
					data.add(temp);
				}
				List<Folder> folderlist = folderService.getFoldersByUserId(token);
				for(int i=0;i<folderlist.size();i++){
					JSONObject temp = new JSONObject();
					temp.put("fileid", folderlist.get(i).getFolderID());
					temp.put("parentid", folderlist.get(i).getFatherID());
					temp.put("filename", folderlist.get(i).getName());
					temp.put("type","directory");
					temp.put("date", folderlist.get(i).getCreateDate());
					temp.put("size",0);
					if(folderlist.get(i).getIntegrityType().equals("1")){
						temp.put("TPA","TPA");
					}else{
						temp.put("TPA","normal");
					}
					if(folderlist.get(i).getShareType().equals("1")){
						temp.put("share","ABE");
					}else{
						temp.put("share","normal");
					}
					data.add(temp);
					
				}
				result.put("data", true);
			}catch(Exception e){
				e.printStackTrace();
			}
			//-----获取文件列表-----//
			
			return result.toJSONString();
		}
	
	//发送文件id，“listfile”和token，返回文件id下的所有文件
	@ResponseBody
	@RequestMapping(value = "/listfile", method = RequestMethod.POST)
	public String listfile(@RequestParam String token,
			@RequestParam String Fileid) {
		JSONObject result = new JSONObject();
		JSONArray data = new JSONArray();
		result.put("code",0);
		//-----获取文件列表-----//
		List<File> filelist = fileService.getFilesByUserId(token);
		for(int i=0;i<filelist.size();i++){
			if(filelist.get(i).getFolderId().equals(Fileid)){
				JSONObject temp = new JSONObject();
				temp.put("fileid", filelist.get(i).getFile_id());
				temp.put("parentid", filelist.get(i).getFolderId());
				temp.put("filename", filelist.get(i).getFile_name());
				temp.put("type","file");
				temp.put("date", filelist.get(i).getUpload_time());
				temp.put("size",0);
				temp.put("TPA",0);
				temp.put("share",0);
				data.add(temp);
			}
		}
		List<Folder> folderlist = folderService.getFoldersByUserId(token);
		for(int i=0;i<folderlist.size();i++){
			if(folderlist.get(i).getFatherID().equals(Fileid)){
				JSONObject temp = new JSONObject();
				temp.put("fileid", folderlist.get(i).getFolderID());
				temp.put("parentid", folderlist.get(i).getFatherID());
				temp.put("filename", folderlist.get(i).getName());
				temp.put("type","directory");
				temp.put("date", folderlist.get(i).getCreateDate());
				temp.put("size",0);
				if(folderlist.get(i).getIntegrityType().equals("1")){
					temp.put("TPA","TPA");
				}else{
					temp.put("TPA","normal");
				}
				if(folderlist.get(i).getShareType().equals("1")){
					temp.put("share","ABE");
				}else{
					temp.put("share","normal");
				}
				data.add(temp);
			}	
		}
		result.put("data", true);
		return result.toJSONString();
	}
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
	
	//删除文件，发送token，文件id以及“deletefile”关键字，返回删除文件之后的文件目录信息（删除文件后，请在数据表中删除记录）
	@RequestMapping(value = "/deletefile", method = RequestMethod.POST)
	public String deleteFolder(@RequestParam String id,
			@RequestParam String token) {
		JSONObject result = new JSONObject();
		result.put("code", 0);
		result.put("data", true);
		Stack<String> stack = new Stack<String>();
		stack.push(id);
		String newid;
		List<File> filelist = fileService.getFilesByUserId(token);
		List<Folder> folderlist = folderService.getFoldersByUserId(token);
		while((newid=stack.pop())!=null){
			for(int i=0;i<filelist.size();i++){
				if(filelist.get(i).getFolderId().equals(newid)){
					fileService.deleteFile(filelist.get(i).getFile_id());
				}
			}
			for(int i=0;i<folderlist.size();i++){
				if(folderlist.get(i).getFatherID().equals(newid)){
					stack.push(folderlist.get(i).getFolderID());
					folderService.deleteFolder(folderlist.get(i).getFolderID());
				}	
			}
		}
		return result.toJSONString();
	}
}
