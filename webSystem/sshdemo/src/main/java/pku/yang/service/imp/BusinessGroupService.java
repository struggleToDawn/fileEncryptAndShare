package pku.yang.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pku.yang.dao.IBusinessGroupDao;
import pku.yang.dao.imp.BusinessGroupDao;
import pku.yang.model.BusinessGroup;
import pku.yang.model.Folder;
import pku.yang.service.IBusinessGroupService;


@Service
public class BusinessGroupService implements IBusinessGroupService{

	@Autowired
	private IBusinessGroupDao businessGroupDao;

	@Override
	public	String addGroup(String name, String adminId, String storageId,
			String adminAttrs, String uAttrs , String uids,String ctime){
			
			BusinessGroup businessGroup = new BusinessGroup();
			businessGroup.setAdminAttrs(adminAttrs);
//			businessGroup.setID(id);
			businessGroup.setAdminId(adminId);
			businessGroup.setUAttrs(uAttrs);
			businessGroup.setName(name);
			businessGroup.setUids(uids);
			businessGroup.setStorageId(storageId);
			businessGroup.setCtime(ctime);
			businessGroupDao.addGroup(businessGroup);
			return businessGroup.getID();
	}

	

	@Override
	public void deleteGroup(String groupID) {
		// TODO Auto-generated method stub
		businessGroupDao.deleteBusinessGroup(groupID);
	}

	@Override
	public List<BusinessGroup> getBussinessGroupList() {
		// TODO Auto-generated method stub
		List<BusinessGroup> grouplist = businessGroupDao.getList("from BusinessGroup");
		return grouplist;
	}

	@Override
	public BusinessGroup findGroupInfo(String GroupID) {
		// TODO Auto-generated method stub
		BusinessGroup group = businessGroupDao.getByID(GroupID);
		return group;
	}



	@Override
	public void saveGroup(BusinessGroup businessGroup) {
		// TODO Auto-generated method stub
		businessGroupDao.addGroup(businessGroup);
	}



	@Override
	public BusinessGroup findGroupInfoBySid(String sid) {
		// TODO Auto-generated method stub
		return businessGroupDao.getBySID(sid);
	}

	
}
