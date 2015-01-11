package org.leejianhao.cms.dao;

import java.util.List;

import org.leejianhao.basic.dao.IBaseDao;
import org.leejianhao.cms.model.Role;

public interface IRoleDao extends IBaseDao<Role> {
	public List<Role> listRole();
	public void deleteRoleUsers(int rid);
}
