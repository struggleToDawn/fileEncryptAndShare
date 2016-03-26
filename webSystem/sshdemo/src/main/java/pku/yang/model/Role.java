package pku.yang.model;

/**
 * 用户角色
 * @author summer
 *
 */
public class Role {
	/** 系统管理员 1XX*/
	private boolean isSysMng;
	
	/**群组管理员 X1X*/
	private boolean isGroupMng;
	
	/**普通用户 XX1*/
	private boolean isCommonUser;

	public boolean isSysMng() {
		return isSysMng;
	}

	public void setSysMng(boolean isSysMng) {
		this.isSysMng = isSysMng;
	}

	public boolean isGroupMng() {
		return isGroupMng;
	}

	public void setGroupMng(boolean isGroupMng) {
		this.isGroupMng = isGroupMng;
	}

	public boolean isCommonUser() {
		return isCommonUser;
	}

	public void setCommonUser(boolean isCommonUser) {
		this.isCommonUser = isCommonUser;
	}
	

}
