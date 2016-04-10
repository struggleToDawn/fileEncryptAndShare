package pku.yang.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import pku.yang.model.File;
import pku.yang.service.IFileService;

@Controller
@RequestMapping("/file")
public class FileController {
	@Autowired
	private IFileService fileService;
	
	//-----以下是正式的接口代码-----//
	@ResponseBody
	@RequestMapping(value = "/GetAllFileByUserId", method = RequestMethod.GET)
	public String GetAllFilebyId(@RequestParam String user_id){
			List<File> filelist = fileService.getFilesByUserId(user_id);
			JSONObject result = new JSONObject();
			JSONArray data = new JSONArray();
			for(int i=0;i<filelist.size();i++){
				JSONObject temp = new JSONObject();
				temp.put("fileid", filelist.get(i).getFile_id());
				temp.put("filename", filelist.get(i).getFile_name());
				data.add(temp);
			}
			result.put("code",0);
			result.put("data", data);
			return result.toJSONString();
	}
	//-----以下是测试代码-----//
	
	/**
	 * �����ļ�������Ϣ 
	 * 
	 * @param id
	 * @param name
	 * @param folder
	 * @param owner
	 * @param uploadtime
	 * @param expname
	 * @param cloudpath
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String saveFile(@RequestParam String id,
			@RequestParam String name,
			@RequestParam String folder,
			@RequestParam String owner,
			@RequestParam String uploadtime,
			@RequestParam String expname,
			@RequestParam String cloudpath) {
		System.out.println("Testlog: 111---addFile of FileController");
		fileService.addFile(id, name, folder, owner, uploadtime,expname, cloudpath);
		System.out.println("Testlog: 222---addFile of FileController");
		return "filemanage/add_file";
	}
	
	/**
	 * ��ѯ�ļ��б� TODO
	 * 
	 * @param id
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String listFile(Model model) {
		int code=200;
		List<File> list = fileService.FileList();
		model.addAttribute("filelist",list);
		JSONObject resultjson = new JSONObject();
		JSONArray dorjsonarray = new JSONArray();
		for(int i=0;i<list.size();i++){
			JSONObject json1 = new JSONObject();
			json1.put("id",list.get(i).getFile_id());
			json1.put("name", list.get(i).getFile_name());
			json1.put("folder", list.get(i).getFolderId());
			json1.put("owner", list.get(i).getOwner());
			json1.put("uploadtime", list.get(i).getUpload_time());
			json1.put("expname", list.get(i).getExp_name());
			json1.put("cloudpath", list.get(i).getCloud_path());
			dorjsonarray.add(json1);
		}
		resultjson.put("code",code);
		resultjson.put("content", dorjsonarray);
		model.addAttribute("jsonArray",resultjson);
		return "filemanage/list_file";
	}
	
	/**
	 * ɾ���ļ� TODO
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String deleteFolder(@RequestParam String id) {
		fileService.deleteFile(id);
		return "filemanage/add_file";
	}
}
