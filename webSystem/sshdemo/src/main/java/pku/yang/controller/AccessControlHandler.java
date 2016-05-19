package pku.yang.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import pku.yang.model.AccessControlParams;
import pku.yang.service.IAccessControlService;

/**
 * 访问控制Controller
 * @author  chenbin
 * @Date    20160113
 * @Operate create
 * @address Peking University
 */

@Controller
public class AccessControlHandler {
	
	@Autowired
	private IAccessControlService accessControlService;
	
	private Map<String,String> errorMsg = new HashMap<String, String>();
	Map<String,Map<String,String>> resultMap = new HashMap<String, Map<String,String>>();
	Map<String,List<Map<String,String>>> resultMapList = new HashMap<String, List<Map<String,String>>>();
	Map<String,String> state = new HashMap<String, String>();
	

	@ResponseBody
	@RequestMapping("/queryaccess/{token}/{groupId}/{fileFolderId}/{privilege}")
	public Map<String,Map<String,String>>  queryAccess(
			@PathVariable("token") 				String token,
			@PathVariable("groupId")			Integer groupid,
			@PathVariable("fileFolderId") 		String path,
			@PathVariable("privilege") 			String privilege)
	{
		
		beforeAction();
		Map<String, String> queryPrivileges = accessControlService.queryAccess(token, groupid, path, privilege); 
		return afterAction(queryPrivileges);
		
	}
	
	@ResponseBody
	@RequestMapping(value="/queryaccess")
	public Map<String,Map<String,String>> queryAccess(AccessControlParams accessControlParams){
		
		beforeAction();
		resultMap.clear(); 
		Map<String, String> queryPrivileges = accessControlService.queryAccess(
													 accessControlParams.getToken(), 
													 accessControlParams.getGroupId(), 
													 accessControlParams.getFileFolderId(),
													 accessControlParams.getPrivilege());
		return afterAction(queryPrivileges);
	}
	
	@ResponseBody
	@RequestMapping("/queryaccessall/{token}/{groupId}/{fileFolderId}")
	public Map<String,Map<String,String>> queryAccessAll(
			@PathVariable("token") 				String token,
			@PathVariable("groupId") 			Integer groupid,
			@PathVariable("fileFolderId") 		String path)
	{
		beforeAction();
		Map<String, String> queryPrivileges = accessControlService.queryAccess(token, groupid, path);
		return afterAction(queryPrivileges);
	}
	
	@ResponseBody
	@RequestMapping(value="/queryaccessall")
	public Map<String,Map<String,String>> queryAccessAll(AccessControlParams accessControlParams){
		beforeAction();
		Map<String, String> queryPrivileges = accessControlService.queryAccess(
				 									accessControlParams.getToken(), 
				 									accessControlParams.getGroupId(), 
				 									accessControlParams.getFileFolderId());
		return afterAction(queryPrivileges);
	}
	
	@ResponseBody
	@RequestMapping("/queryattrexpress/{token}/{groupId}/{fileFolderId}")
	public Map<String,List<Map<String,String>>> queryAttrExpress(
			@PathVariable("token") 				String token,
			@PathVariable("groupId") 			Integer groupid,
			@PathVariable("fileFolderId") 		String path)
	{
		beforeAction();
		List<Map<String,String>> policys = accessControlService.queryPolicy(token,groupid,path,"queryattrexpress");
		return afterAction(policys);
	}
	
	@ResponseBody
	@RequestMapping(value="/queryattrexpress")
	public Map<String,List<Map<String,String>>> queryAttrExpress(AccessControlParams accessControlParams){
		beforeAction();
		List<Map<String,String>> policys = accessControlService.queryPolicy(
													 accessControlParams.getToken(), 
													 accessControlParams.getGroupId(), 
													 accessControlParams.getFileFolderId(),
													 "queryattrexpress");
		
		return afterAction(policys);
	}
	
	@ResponseBody
	@RequestMapping(value="/querypolicy")
	public Map<String,List<Map<String,String>>> querypolicy(AccessControlParams accessControlParams){
		beforeAction();
		List<Map<String,String>> policys = accessControlService.queryPolicy(
													 accessControlParams.getToken(), 
													 accessControlParams.getGroupId(), 
													 accessControlParams.getFileFolderId(),
													 "querypolicy");
		
		return afterAction(policys);
	}
	
	@ResponseBody
	@RequestMapping(value="/insertpolicy")
	public Map<String,Map<String,String>> insertPolicy(AccessControlParams accessControlParams){
		 beforeAction();
		 int num = accessControlService.insertPolicy(accessControlParams);
		 Map<String,String> ret = new HashMap<String, String>();
		 if(num == 0) ret.put("40003", "数据库操作失败，插入/更新失败");
		 else ret.put("20000", "插入/更新成功");
		 return afterAction(ret);
	}
	
	@ResponseBody
	@RequestMapping(value="/modifypolicy")
	public Map<String,Map<String,String>> modifyPolicy(AccessControlParams accessControlParams){
		 beforeAction();
		 int num = accessControlService.insertPolicy(accessControlParams);
		 Map<String,String> ret = new HashMap<String, String>();
		 if(num == 0) ret.put("40003", "数据库操作失败，插入/更新失败");
		 else ret.put("20000", "插入/更新成功");
		 return afterAction(ret);
	}
	

	@ResponseBody
	@RequestMapping(value="/deletepolicy")
	public Map<String,Map<String,String>> deletePolicy(AccessControlParams accessControlParams){
		 beforeAction();
		 Map<String,String> ret = accessControlService.deletePolicy(
				 								Integer.parseInt(accessControlParams.getPolicyId()),
				 								accessControlParams.getToken());
		 return afterAction(ret);
	}
	
	@ResponseBody
	@RequestMapping(value="/conflictdetection")
	public Map<String,List<String>> conflictdetection(AccessControlParams accessControlParams){
		
		Map<String,List<String>> ret = new HashMap<String, List<String>>();
		
		beforeAction();
		ret =  accessControlService.conflictdetection(accessControlParams);
		errorMsg = accessControlService.getErrorMsg();
		if(errorMsg.size() != 0) {
			List<String> errList = new ArrayList<String>();
			errList.add(errorMsg.get("系统错误"));
			ret.put("40000", errList);
		}
		return ret;
	}
	
	
	
	private void beforeAction(){
		errorMsg.clear();
		state.clear();
		resultMap.clear(); 
		resultMapList.clear();
	}
	private Map<String, Map<String,String>> afterAction(Map<String, String> retMap){
		errorMsg = accessControlService.getErrorMsg();
		if(errorMsg.size() == 0){
			state.put("20000", "ok");
			resultMap.put("code", state);
			resultMap.put("ret",retMap);
		}else{
			state.put("40000", "error");
			resultMap.put("code", state);
			resultMap.put("ret",errorMsg);
		}
		 return resultMap;
	}
	
	private Map<String, List<Map<String,String>>> afterAction(List<Map<String, String>> retList){
		errorMsg = accessControlService.getErrorMsg();
		List<Map<String, String>> stateList = new ArrayList<Map<String,String>>();
		if(errorMsg.size() == 0){
			state.put("20000", "ok");
			stateList.add(state);
			resultMapList.put("code", stateList);
			resultMapList.put("ret",retList);
		}else{
			state.put("40000", "error");
			stateList.add(state);
			resultMapList.put("code", stateList);
			List<Map<String, String>> errorList = new ArrayList<Map<String,String>>();
			errorList.add(errorMsg);
			resultMapList.put("ret",errorList);
		}
		 return resultMapList;
	}
}
