package pku.yang.service;

import java.util.List;
import java.util.Map;

import pku.yang.model.AccessControlParams;

/**
 * 访问控制服务层接口
 * @author  chenbin
 * @Date    20160113
 * @Operate create
 * @address Peking University
 */

public interface IAccessControlService {
	
	public Map<String,String> queryAccess(String token,Integer groupId,String path,String privilege);
	
	public Map<String,String> queryAccess(String token,Integer groupId,String path);
	
	public List<Map<String,String>> queryPolicy(String token,Integer groupId,String path,String function);
	
	public int insertPolicy(AccessControlParams accessControlParams);
	
	public Map<String,String> deletePolicy(Integer policyId,String token);
	
	public Map<String,List<String>> conflictdetection(AccessControlParams accessControlParams);
	
	public Map<String,String> getErrorMsg();

}
