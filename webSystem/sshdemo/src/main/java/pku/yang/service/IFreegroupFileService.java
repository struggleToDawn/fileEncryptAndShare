package pku.yang.service;

import pku.yang.model.FreeGroup;
import pku.yang.model.FreegroupFile;

public interface IFreegroupFileService {
	void save_fgfile(FreegroupFile fgfile);
	void delete_fgfile(String fgfile_id);
	FreegroupFile search_fgfile_info(String fgfile_id);
	String add_fgfile(String fileid,String folderid);

}
