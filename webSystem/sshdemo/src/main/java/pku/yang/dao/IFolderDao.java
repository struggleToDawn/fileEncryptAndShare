package pku.yang.dao;

import java.util.List;

import pku.yang.model.Folder;

public interface IFolderDao {
	void save(Folder folder);
	void deleteFolder(String Fid);
	Folder getByID(String id);
	List<Folder> getList(String hql);
}
