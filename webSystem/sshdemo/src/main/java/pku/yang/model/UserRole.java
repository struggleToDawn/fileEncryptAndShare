package pku.yang.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户角色表
 * @author summer
 *
 */

public class UserRole {
	
	/**用户ID*/
	
	private String userID;
	
	/**用户角色*/
	
	private Role role;
	
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(int rolenum) {
		Role role = new Role();
		role.setSysMng((rolenum&4)!=0);
		role.setGroupMng((rolenum&2)!= 0);
		role.setCommonUser((rolenum&1)!=0);
		this.role = role;
	}
}
