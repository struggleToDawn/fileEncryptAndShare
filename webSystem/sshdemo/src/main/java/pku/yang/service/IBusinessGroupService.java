package pku.yang.service;


import java.util.List;
import pku.yang.model.BusinessGroup;


public interface IBusinessGroupService {


	BusinessGroup findGroupInfo(String GroupID);
	String addGroup(String name, String adminId, String storageId,
			String adminAttrs, String uAttrs ,String uids,String ctime);
	
	void saveGroup(BusinessGroup businessGroup);
	void deleteGroup(String groupID);
	
	List<BusinessGroup> getBussinessGroupList();
	

	
}
