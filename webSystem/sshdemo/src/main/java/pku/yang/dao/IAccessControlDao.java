package pku.yang.dao;

import java.util.List;
import java.util.Map;

import pku.yang.model.AccessControl;
import pku.yang.model.Strategy;
import pku.yang.model.User;

/**
 * 访问控制Dao接口
 * @author  chenbin
 * @Date    20160113
 * @Operate create
 * @address Peking University
 */

public interface IAccessControlDao {
	
	
	public void saveStratege(Strategy strategy);
	
	public void mergeStratege(Strategy strategy);
	
	public void saveAccessControl(AccessControl accessControl);
	
	public void saveAccessControlAndStrategy(AccessControl accessControl,Strategy strategy);
	
	public int deleteAccessControl(int accessControlId);
	
	public int deleteStrategy(int strategyId);
	
	public List<User> getUsers(String hql);
	
	public Map<String,String> getErrorMsg();
	
	public void clear();
}
