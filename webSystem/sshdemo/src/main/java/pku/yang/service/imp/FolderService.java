package pku.yang.service.imp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pku.yang.dao.imp.FolderDao;
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
	public void deleteFolder(String folderID){
		folderdao.deleteFolder(folderID);
	}
	
	@Override
	public List<Folder> FolderList(){
		List<Folder> folderlist = folderdao.getList("from Folder");
		System.out.println("FolderService:"+folderlist);
		return folderlist;
	}
}
