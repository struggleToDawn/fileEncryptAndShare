package pku.yang.service.imp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pku.yang.dao.imp.FolderDao;
import pku.yang.model.File;
import pku.yang.model.Folder;
import pku.yang.service.IFolderService;

@Service
public class FolderService implements IFolderService{
	@Autowired
	private FolderDao folderdao;
	
	@Override
	public Folder findFolderInfo(String folderID){
		return folderdao.getByID(folderID);
	}

	@Override
	public void saveFolder(Folder folder){
		folderdao.save(folder);
	}
	
	@Override
	public void addFolder(String folderID,String name,String fatherID,String storageID,
			String storageType,String whetherRoot,String creater,
			String createDate,String shareType,String integrityType){
		Folder folder= new Folder();
		folder.setFolderID(folderID);
		folder.setName(name);
		folder.setFatherID(fatherID);
		folder.setStorageID(storageID);
		folder.setStorageType(storageType);
		folder.setWhetherRoot(whetherRoot);
		folder.setCreater(creater);
		folder.setCreateDate(createDate);
		folder.setShareType(shareType);
		folder.setIntegrityType(integrityType);
		this.saveFolder(folder);
	}
	
	@Override
	public String createFolder(String uid,String name,String parentid){
		Folder folder= new Folder();
		String id = "0";
	
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		String unixtime = Long.toString(date.getTime());
		id = id + unixtime;
		int i = (int)Math.random()*1000;
		String randomNumber = Integer.toString(i);
		id = id + randomNumber;
		folder.setFolderID(id);
		folder.setName(name);
		folder.setFatherID(parentid);
		folder.setCreater(uid);
		this.saveFolder(folder);
		return id;
	}
	
	@Override
	public void deleteFolder(String folderID){
		folderdao.deleteFolder(folderID);
	}
	
	@Override
	public List<Folder> FolderList(){
		List<Folder> folderlist = folderdao.getList("from Folder");
		System.out.println("FolderService:"+folderlist);
		return folderlist;
	}
	//-----add by shengxiaoran-----//
	@Override
	public List<Folder> getFoldersByUserId(String user_id){
		String hql = "from Folder where creater="+user_id;
		List<Folder> filelist = folderdao.getList(hql);
		return filelist;
	}
	//-----add by shengxiaoran-----//
}
