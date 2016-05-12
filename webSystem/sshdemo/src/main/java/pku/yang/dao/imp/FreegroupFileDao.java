package pku.yang.dao.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import pku.yang.dao.IFreeGroupDao;
import pku.yang.dao.IFreegroupFileDao;
import pku.yang.model.FreeGroup;
import pku.yang.model.FreegroupFile;


@Repository
public class FreegroupFileDao implements IFreegroupFileDao{

	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	
	@Override
	public void save(FreegroupFile fgfile) {
		// TODO Auto-generated method stub
		hibernateTemplate.saveOrUpdate(fgfile);
		
	}

	@Override
	public void delete(String fgfile_id) {
		// TODO Auto-generated method stub
		hibernateTemplate.delete(hibernateTemplate.get(FreegroupFile.class, fgfile_id));
		
	}

	@Override
	public FreegroupFile getByID(String fgfile_id) {
		// TODO Auto-generated method stub
		FreegroupFile fgfile = (FreegroupFile)hibernateTemplate.get(FreegroupFile.class, fgfile_id);
		return fgfile;
	}

}
