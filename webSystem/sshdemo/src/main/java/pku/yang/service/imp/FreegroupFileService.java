package pku.yang.service.imp;

import org.springframework.beans.factory.annotation.Autowired;

import pku.yang.dao.imp.FreeGroupDao;
import pku.yang.dao.imp.FreegroupFileDao;
import pku.yang.model.FreeGroup;
import pku.yang.model.FreegroupFile;
import pku.yang.service.IFreeGroupService;
import pku.yang.service.IFreegroupFileService;

public class FreegroupFileService implements IFreegroupFileService{

	@Autowired
	private FreegroupFileDao fgfilepdao;
	
	@Override
	public void save_fgfile(FreegroupFile fgfile) {
		// TODO Auto-generated method stub
		fgfilepdao.save(fgfile);
	}

	@Override
	public void delete_fgfile(String fgfile_id) {
		// TODO Auto-generated method stub
		fgfilepdao.delete(fgfile_id);
		
	}

	@Override
	public FreegroupFile search_fgfile_info(String fgfile_id) {
		// TODO Auto-generated method stub
		FreegroupFile fgfile=fgfilepdao.getByID(fgfile_id);
		return fgfile;
	}

	@Override
	public String add_fgfile(String fileid, String folderid) {
		// TODO Auto-generated method stub
		FreegroupFile fgfile=new FreegroupFile();
		fgfile.setFile_id(fileid);
		fgfile.setFolder_id(folderid);
		String fgfile_id=fgfile.getFgfile_id();
		return fgfile_id;
	}

}
