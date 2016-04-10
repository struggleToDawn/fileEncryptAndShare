package pku.yang.dao;

import java.util.List;

import pku.yang.model.Message;

public interface IMessageDao {
	
	void save(Message mess);
	void save_state(String mess_id,String state);
	void delete(String mess_id);
	Message getByID(String mess_id);
	List<Message> getList(String hql);
	List<Message> usermess(String user_id);
	
}
