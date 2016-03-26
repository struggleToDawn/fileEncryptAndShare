package pku.yang.service;


import java.util.List;
import pku.yang.model.BusinessGroup;


public interface IBusinessGroupService {


	BusinessGroup findGroupInfo(String GroupID);
	void addGroup(String id, String name, String adminId, int storageId,
			String adminAttrs, String uAttrs ,String ctime);
	
	void saveGroup(BusinessGroup businessGroup);
	void deleteGroup(String groupID);
	
	List<BusinessGroup> getBussinessGroupList();
	

	
}
