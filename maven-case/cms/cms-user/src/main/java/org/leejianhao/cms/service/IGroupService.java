package org.leejianhao.cms.service;

import java.util.List;

import org.leejianhao.basic.model.Pager;
import org.leejianhao.cms.model.Group;

public interface IGroupService {
	public void add(Group group);
	public void delete(int id);
	public Group load(int id);
	public void update(Group group);
	
	public List<Group> listGroup();
	public Pager<Group> findGroup();
	public void deleteGroupUsers(int id);
}
