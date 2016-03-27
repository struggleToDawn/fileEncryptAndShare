package pku.yang.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="businessgroup")


public class BusinessGroup {

	@Id
	@Column(name="id")
	private String id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="adminId")
	private String adminId;
	
	@Column(name="storageId")
	private int storageId;
	
	
	@Column(name="adminAttrs")
	private String adminAttrs;
	
	@Column(name="uAttrs")
	private String uAttrs;
	
	@Column(name="ctime")
	private String ctime;
	
	@Column(name="utime")
	private String utime;
	
	public String getCtime(){
		return this.ctime;
	}
	
	public void setCtime(String ctime){
		this.ctime = ctime;
	}
	
	public String getUtime(){
		return this.utime;
	}
	
	public void setUptime(String utime){
		this.utime = utime;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public int getStorageId(){
		return storageId;
	}
	
	public void setStorageId(int storageId){
		this.storageId = storageId;
	}
	
	public String getID() {
		return id;
	}
	
	public void setID(String ID){
		this.id = ID;
	}

	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	public String getAdminAttrs() {
		return adminAttrs;
	}

	public void setAdminAttrs(String adminAttrs) {
		this.adminAttrs = adminAttrs;
	}

	public String getUAttrs() {
		return uAttrs ;
	}
	
	public void setUAttrs(String uAttrs) {
		this.uAttrs = uAttrs ;
	}

}