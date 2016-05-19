package pku.yang.model;

/**
 * 访问控制参数封装
 * @author  chenbin
 * @Date    20160113
 * @Operate create
 * @address Peking University
 */
public class AccessControlParams {
	
	private String userId;
	private String token;
	private String username;
	private Integer groupId;
	private String fileFolderId;
	private String privilege;
	private Integer strategyID;
	private String 	propertyExpression;	
	private Integer allowCreateFloder;	
	private Integer allowShareFloder;	
	private Integer allowDeleteFloder;	
	private Integer allowUploadFile;	
	private Integer allowDownloadFile;	
	private Integer allowDeleteFile;	
	private Integer operateWays;		
	private Integer integrity;	
	private String policyId;
	
	
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getPolicyId() {
		return policyId;
	}
	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getFileFolderId() {
		return fileFolderId;
	}
	public void setFileFolderId(String fileFolderId) {
		this.fileFolderId = fileFolderId;
	}
	
	public Integer getStrategyID() {
		return strategyID;
	}
	public void setStrategyID(Integer strategyID) {
		this.strategyID = strategyID;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public String getPropertyExpression() {
		return propertyExpression;
	}
	public void setPropertyExpression(String propertyExpression) {
		this.propertyExpression = propertyExpression;
	}
	public Integer getAllowCreateFloder() {
		return allowCreateFloder;
	}
	public void setAllowCreateFloder(Integer allowCreateFloder) {
		this.allowCreateFloder = allowCreateFloder;
	}
	public Integer getAllowShareFloder() {
		return allowShareFloder;
	}
	public void setAllowShareFloder(Integer allowShareFloder) {
		this.allowShareFloder = allowShareFloder;
	}
	public Integer getAllowDeleteFloder() {
		return allowDeleteFloder;
	}
	public void setAllowDeleteFloder(Integer allowDeleteFloder) {
		this.allowDeleteFloder = allowDeleteFloder;
	}
	public Integer getAllowUploadFile() {
		return allowUploadFile;
	}
	public void setAllowUploadFile(Integer allowUploadFile) {
		this.allowUploadFile = allowUploadFile;
	}
	public Integer getAllowDownloadFile() {
		return allowDownloadFile;
	}
	public void setAllowDownloadFile(Integer allowDownloadFile) {
		this.allowDownloadFile = allowDownloadFile;
	}
	public Integer getAllowDeleteFile() {
		return allowDeleteFile;
	}
	public void setAllowDeleteFile(Integer allowDeleteFile) {
		this.allowDeleteFile = allowDeleteFile;
	}
	public Integer getOperateWays() {
		return operateWays;
	}
	public void setOperateWays(Integer operateWays) {
		this.operateWays = operateWays;
	}
	public Integer getIntegrity() {
		return integrity;
	}
	public void setIntegrity(Integer integrity) {
		this.integrity = integrity;
	}
	public String getPrivilege() {
		return privilege;
	}
	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}	
	
}
