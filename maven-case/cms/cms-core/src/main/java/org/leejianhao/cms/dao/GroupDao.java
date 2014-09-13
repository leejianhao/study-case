package org.leejianhao.cms.dao;

import java.util.List;

import org.leejianhao.basic.dao.BaseDao;
import org.leejianhao.basic.model.Pager;
import org.leejianhao.cms.model.Group;
import org.springframework.stereotype.Repository;

@Repository("groupDao")
public class GroupDao extends BaseDao<Group> implements IGroupDao {

	@Override
	public List<Group> listGroup() {
		return this.list("from Group");
	}

	@Override
	public Pager<Group> findGroup() {
		return this.find("from Group");
	}
 
	@Override
	public void deleteGroupUsers(int gid) {
		this.updateByHql("delete UserGroup ug where ug.user.id =?",gid);
	}

	
}
