package org.leejianhao.cms.dao;

import java.util.List;

import org.leejianhao.basic.dao.IBaseDao;
import org.leejianhao.basic.model.Pager;
import org.leejianhao.cms.model.Group;

public interface IGroupDao extends IBaseDao<Group> {
	public List<Group> listGroup();
	public Pager<Group> findGroup();
	public void deleteGroupUsers(int gid);
}
