package pku.yang.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import pku.yang.model.AccessControl;

/**
 * 访问控制Dao
 * @author  chenbin
 * @Date    20160113
 * @Operate create
 * @address Peking University
 */

public interface IAccessControlRepository extends JpaRepository<AccessControl, Integer>,JpaSpecificationExecutor<AccessControl>{
 
	AccessControl getByAccessContorlId(Integer accessContorlId);
	
	List<AccessControl> getByGroupId(Integer groupId);
	
	List<AccessControl> getByPath(String path);
	
	List<AccessControl> getByStrategysIn(Integer[] strategyIds);
	
	List<AccessControl> getByGroupIdAndPath(Integer groupId,String path);

		
}
