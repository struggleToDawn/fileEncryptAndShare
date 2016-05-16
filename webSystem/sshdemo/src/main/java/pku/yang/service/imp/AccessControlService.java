package pku.yang.service.imp;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pku.yang.dao.IAccessControlDao;
import pku.yang.dao.IAccessControlRepository;
import pku.yang.dao.IStrategyRepository;
import pku.yang.dao.IUserRepository;
import pku.yang.dao.imp.UserDao;
import pku.yang.model.AccessControl;
import pku.yang.model.AccessControlParams;
import pku.yang.model.Strategy;
import pku.yang.model.User;
import pku.yang.service.IAccessControlService;
import pku.yang.tool.AttrExpressToSql;

/**
 * 访问控制服务层
 * @author  chenbin
 * @Date    20160113
 * @Operate create
 * @address Peking University
 */

@Service
public class AccessControlService implements IAccessControlService {
	
	@Autowired
	private IAccessControlRepository accessControlRepository;
	
	@Autowired
	private IAccessControlDao accessControlDao;
	
	@Autowired
	private IStrategyRepository strategyRepository;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private IUserRepository userRepository;
	
	private AttrExpressToSql attrExpressToSql = new AttrExpressToSql();
	
	public AccessControlService() {}
		
	@Transactional
	public Strategy findStrategy(Integer strategy_id) {
		return strategyRepository.findOne(strategy_id);
	}

	@Override
	public Map<String,String> queryAccess(String id,Integer groupId,String path,String privilege){
		errorMsg.clear();
		if(privilege == null || "".endsWith(privilege)){
			setErrorMsg("40002", "请求参数错误，权限为空或者名称书写不正确");
			return null;
		}
		Map<String,String> resultMap= new HashMap<String, String>();
		if(checkParams(id,groupId,path)){
			beforeAction();
			Map<String,String> privilegeMap = queryPrivileges(id,groupId,path,privilege);
			if(privilegeMap != null){
				resultMap.put(privilege, privilegeMap.get(privilege));
			}
			afterAction();
		}
		return resultMap;	
	}
	
	@Override
	public Map<String,String> queryAccess(String id,Integer groupId,String path){
		errorMsg.clear();
		if(checkParams(id,groupId,path)){
			return queryPrivileges(id,groupId,path,null);
		}
		return null;
	}
	
	public List<Map<String,String>> queryPolicy(String id,Integer groupId,String path,String function){
		errorMsg.clear();
		if(checkParams(id,groupId,path)){
			return queryStrategys(id,groupId,path,function);
		}
		return null;
	}
	
	@Override
	@Transactional
	public int insertPolicy(AccessControlParams accessControlParams){
		
		errorMsg.clear();
		if(accessControlParams.getPropertyExpression() == null || "".endsWith(accessControlParams.getPropertyExpression())){
			setErrorMsg("40002", "请求参数错误，属性表达式为空或者名称书写不正确");
			return 0;
		}
		if(checkParams(accessControlParams.getUserId(),accessControlParams.getGroupId(),accessControlParams.getFileFolderId())){
			List<Strategy> strategies = new ArrayList<Strategy>();
			List<AccessControl> accessControls = new ArrayList<AccessControl>();
			
			Strategy strategy = new Strategy();
			Integer strategyID = null;
			Strategy oldStrategy = null;
			if((strategyID  = accessControlParams.getStrategyID()) != null){
				 oldStrategy = strategyRepository.getOne(strategyID);
				 
			}
			
			Integer allowCreateFloder = accessControlParams.getAllowCreateFloder();	
			Integer allowShareFloder = accessControlParams.getAllowShareFloder();	
			Integer allowDeleteFloder = accessControlParams.getAllowDeleteFloder();	
			Integer allowUploadFile = accessControlParams.getAllowUploadFile();	
			Integer allowDownloadFile = accessControlParams.getAllowDownloadFile();	
			Integer allowDeleteFile = accessControlParams.getAllowDeleteFile();	
			Integer operateWays = accessControlParams.getOperateWays();	
			Integer integrity = accessControlParams.getIntegrity();	
			
			strategy.setStrategyID(strategyID);
			strategy.setAllowCreateFloder(allowCreateFloder);
			strategy.setAllowShareFloder(allowShareFloder);
			strategy.setAllowDeleteFloder(allowDeleteFloder);
			strategy.setAllowUploadFile(allowUploadFile);
			strategy.setAllowDownloadFile(allowDownloadFile);
			strategy.setAllowDeleteFile(allowDeleteFile);
			strategy.setOperateWays(operateWays);
			strategy.setIntegrity(integrity);
			strategy.setPropertyExpression(accessControlParams.getPropertyExpression());
			
			AccessControl accessControl = new AccessControl();
			
			if(oldStrategy != null){
				strategy.setAccessControls(oldStrategy.getAccessControls());
			}else{
				
				accessControl.setGroupId(accessControlParams.getGroupId());
				accessControl.setPath(accessControlParams.getFileFolderId());
				accessControl.getStrategys().add(strategy);
				strategy.getAccessControls().add(accessControl);
				accessControls.add(accessControl);
			}
			strategies.add(strategy);
			
			
			if(oldStrategy == null){
				accessControlDao.saveStratege(strategy);
				accessControlDao.saveAccessControl(accessControl);
			}else{
				accessControlDao.mergeStratege(strategy);
			}
			return 1;
		}
		return 0;
	}
	
	@Override
	@Transactional
	public Map<String,String> deletePolicy(Integer policyId) {
		Map<String,String> resultMap= new HashMap<String, String>();
		Strategy strategy = strategyRepository.findOne(policyId);
		if(strategy == null){
			setErrorMsg("40002", "请求ID错误，该策略不存在");
			return null;
		}
		try{
			Set<AccessControl> accessControls = strategy.getAccessControls();
			for(AccessControl ac : accessControls){
				accessControlDao.deleteAccessControl(ac.getAccessContorlId());
			}
			accessControlDao.deleteStrategy(policyId);
			resultMap.put("20000", "数据库操作成功，策略已删除");
		}catch(Exception e){
			setErrorMsg("40003", "数据库操作失败，策略删除失败");
		}
		return resultMap;
	}

	@Override
	public Map<String,List<String>> conflictdetection(AccessControlParams accessControlParams) {
		
		Integer groupId = accessControlParams.getGroupId();
		String path = accessControlParams.getFileFolderId();
		String propertyExpression = accessControlParams.getPropertyExpression();
		
		if(groupId == null){
			setErrorMsg("40002", "请求参数错误，组ID为空或者名称书写不正确");
			return null;
		}
		
		if(path == null || "".endsWith(path)){
			setErrorMsg("40002", "请求参数错误，文件/目录ID为空或者名称书写不正确");
			return null;
		}
		
		if(propertyExpression == null || "".endsWith(propertyExpression)){
			setErrorMsg("40002", "请求参数错误，属性表达式为空或者名称书写不正确");
			return null;
		}
		
		List<User> newUserList = getUsersByAttrExpressinon(propertyExpression);
		
		List<AccessControl> accessControls = accessControlRepository.getByGroupIdAndPath(groupId, path);
		
		Map<String,List<String>> conflictMap = new HashMap<String, List<String>>();
		
		for (AccessControl ac : accessControls) {
			Set<Strategy> strategies = ac.getStrategys();
			for (Strategy strategy : strategies) {
				String attrExpress = strategy.getPropertyExpression();
				List<User> policyAttrExpressUsers = getUsersByAttrExpressinon(attrExpress);
				
				List<String> privilegeConflictList = null;
				for(User user : policyAttrExpressUsers){
					for(User newUser : newUserList){
						if(user.getUser_pid() == newUser.getUser_pid()){
							privilegeConflictList = privilegeConflict(accessControlParams,strategy);
							
							if(privilegeConflictList.size() > 0){
								conflictMap.put(user.getUser_pid(), privilegeConflictList);
							}
						}
					}
				}
			}
		}
	return conflictMap;
	}
	
	
	
	private List<User> getUsersByAttrExpressinon(String attributeExpression){
		Map<String,StringBuffer> hqlMap = attrExpressToSql.getSql(attributeExpression);
		String studentHql = hqlMap.get("0").toString();
		String tercherHql = hqlMap.get("1").toString();
		List<User> studentList = accessControlDao.getUsers(studentHql);
		List<User> teacherList = accessControlDao.getUsers(tercherHql);
		studentList.addAll(teacherList);
		return studentList;		
	}
			
	public List<String> privilegeConflict(AccessControlParams accessControlParams,Strategy strategy){
	
		List<String> privilegeConflictList = new ArrayList<String>();
		Integer allowCreateFloder = accessControlParams.getAllowCreateFloder();	
		Integer allowShareFloder = accessControlParams.getAllowShareFloder();	
		Integer allowDeleteFloder = accessControlParams.getAllowDeleteFloder();	
		Integer allowUploadFile = accessControlParams.getAllowUploadFile();	
		Integer allowDownloadFile = accessControlParams.getAllowDownloadFile();	
		Integer allowDeleteFile = accessControlParams.getAllowDeleteFile();	
		if(strategy.getAllowCreateFloder() != allowCreateFloder){
			privilegeConflictList.add("allowCreateFloder");
		}
		if(strategy.getAllowShareFloder() != allowShareFloder){
			privilegeConflictList.add("allowShareFloder");
		}
		if(strategy.getAllowDeleteFloder() != allowDeleteFloder){
			privilegeConflictList.add("allowDeleteFloder");
		}
		if(strategy.getAllowUploadFile() != allowUploadFile){
			privilegeConflictList.add("allowUploadFile");
		}
		if(strategy.getAllowDownloadFile() != allowDownloadFile){
			privilegeConflictList.add("allowDownloadFile");
		}
		if(strategy.getAllowDeleteFile() != allowDeleteFile){
			privilegeConflictList.add("allowDeleteFile");
		}
		return privilegeConflictList;
	}

	
	private boolean checkParams(String id,Integer groupId,String path){
			
			if(id == null || "".endsWith(id)){
				setErrorMsg("40002", "请求参数错误，用户ID为空或者名称书写不正确"); 
				return false;
			}
			if(groupId == null){
				setErrorMsg("40002", "请求参数错误，组ID为空或者名称书写不正确");
				return false;
			}
			if(path == null || "".endsWith(path)){
				setErrorMsg("40002", "请求参数错误，文件/目录ID为空或者名称书写不正确");
				return false;
			}
		return true;
	}
	
	private Map<String,String>  queryPrivileges(String id,int groupId,String path,String privilege){
		
		privileges.clear();
		privileges.put("allowCreateFloder", "0");
		privileges.put("allowShareFloder", "0");
		privileges.put("allowDeleteFloder", "0");
		privileges.put("allowUploadFile", "0");
		privileges.put("allowDownloadFile", "0");
		privileges.put("allowDeleteFile", "0");
		
		List<AccessControl> accessControls = accessControlRepository.getByGroupIdAndPath(groupId, path);
		User newUser = userRepository.getByUser_pid(id);
		if(newUser == null){
			setErrorMsg("40001", "用户不存在");
			return null;
		}
		
		for (AccessControl ac : accessControls) {
			Set<Strategy> strategies = ac.getStrategys();
			for (Strategy strategy : strategies) {
				
				List<User> userList = getUsersByAttrExpressinon(strategy.getPropertyExpression());
				for(User user : userList){
					if(user.getUser_pid().equals(newUser.getUser_pid())){
						privileges = isAllowed(strategy,privileges);
						if(privilege != null){
							if("1".equals(privileges.get(privilege))){
								return privileges;
							}
						}
					}
				}
			}
		}
		return privileges;
	}
	
	private Map<String,String> isAllowed(Strategy strategy,Map<String,String> privileges){
		
		Class<Strategy> clazz = Strategy.class;
			Field[] fields=clazz.getDeclaredFields();
			String arrtibuteName = "";
			String privilegeValue = "";
			for (int i = 0; i < fields.length; i++) {
				fields[i].setAccessible(true);
				arrtibuteName = String.valueOf(fields[i]);
				arrtibuteName = arrtibuteName.substring(arrtibuteName.lastIndexOf(".")+1);
				try {
					privilegeValue = String.valueOf(fields[i].get(strategy));
				} catch (Exception e) {
					setErrorMsg("50002","获取权限失败，属性表达式包含非法字符");
				} 
				if("1".equals(privilegeValue) && "0".equals(privileges.get(arrtibuteName))){
					privileges.replace(arrtibuteName, "1") ;
				}
			}
		return privileges;
	}
	
	
	private List<Map<String,String>>  queryStrategys(String id,int groupId,String path,String function){
		
		List<AccessControl> accessControls = accessControlRepository.getByGroupIdAndPath(groupId, path);
		List<Map<String,String>> policys = new ArrayList<Map<String,String>>();
		User newUser = userRepository.getByUser_pid(id);
		if(newUser == null){
			setErrorMsg("40001", "用户不存在");
			return null;
		}
		for (AccessControl ac : accessControls) {
			Set<Strategy> strategies = ac.getStrategys();
			for (Strategy strategy : strategies) {
				
				List<User> userList = getUsersByAttrExpressinon(strategy.getPropertyExpression());
				for(User user : userList){
					if(user.getUser_pid().equals(newUser.getUser_pid())){
						Map<String,String> policy = new HashMap<String, String>();
						if("queryattrexpress".equals(function)){
							policy.put(strategy.getStrategyID()+"", strategy.getPropertyExpression());	
						}else{
							policy.put("strategyID",strategy.getStrategyID()+"");
							policy.put("allowCreateFloder",strategy.getAllowCreateFloder()+"");
							policy.put("allowShareFloder", strategy.getAllowShareFloder()+"");
							policy.put("allowDeleteFloder", strategy.getAllowDeleteFloder()+"");
							policy.put("allowUploadFile", strategy.getAllowUploadFile()+"");
							policy.put("allowDownloadFile", strategy.getAllowDownloadFile()+"");
							policy.put("allowDeleteFile", strategy.getAllowDeleteFile()+"");
							policy.put("operateWays", strategy.getOperateWays()+"");
							policy.put("integrity", strategy.getIntegrity()+"");
							policy.put("propertyExpression", strategy.getPropertyExpression());
						}
						policys.add(policy);
					}
					
				}
				
			}
			
		}
		return policys;
	}
	
	private void beforeAction(){
		accessControlDao.clear();
	}
	private void afterAction(){
		if(accessControlDao.getErrorMsg().size() != 0){
			errorMsg.putAll(accessControlDao.getErrorMsg());
		}
	}
	
	private Map<String,String> errorMsg = new HashMap<String, String>();
	private Map<String,String> privileges = new HashMap<String,String>();
	
	public Map<String,String> getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String code,String msg) {
		this.errorMsg.put(code, msg);
	}
}
