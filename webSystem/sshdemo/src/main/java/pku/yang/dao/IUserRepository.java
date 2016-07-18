package pku.yang.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import pku.yang.model.User;


/**
 * 用户Dao
 * @author  chenbin
 * @Date    20160113
 * @Operate create
 * @address Peking University
 */

public interface IUserRepository extends JpaRepository<User, Integer>,JpaSpecificationExecutor<User>{
 
	User getByUsername(String username);
	User getByTypeAndUserID(String type,String userId);
	
	
	@Query("SELECT u FROM User u WHERE u.user_pid = ?1")
	User getByUser_pid(String id);
}
