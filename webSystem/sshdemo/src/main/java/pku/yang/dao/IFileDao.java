package pku.yang.dao;

import java.util.List;

import pku.yang.model.File;

public interface IFileDao {
	void save (File file);
	void deleteFile(String file_id);
	List<File> getList(String hql);
}
