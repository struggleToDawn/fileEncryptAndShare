package pku.yang.service;

import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;
import pku.yang.model.File;

public interface IFileService {
	void addFile(String id,String name,String folder,
			String owner, String uploadtime,String expname,
			String cloudpath);
	void deleteFile(String file_id);
	List<File> FileList();
	List<File> getFilesByUserId(String user_id);
	void saveFile(File file);
}