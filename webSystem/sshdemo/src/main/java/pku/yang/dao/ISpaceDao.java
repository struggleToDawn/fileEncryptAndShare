package pku.yang.dao;

import java.util.List;

import pku.yang.model.Space;

public interface ISpaceDao {
	void saveSpace(Space space);
	void addSpace(Space space);
	void deleteSpaceByID(String spaceId);
	Space getSpaceByID(String spaceId);
	List<Space> getSpaceList(String hql);
}
