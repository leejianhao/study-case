package org.leejianhao.cms.dao;

import java.util.List;

import org.leejianhao.basic.dao.BaseDao;
import org.leejianhao.cms.model.Role;
import org.springframework.stereotype.Repository;

@Repository("roleDao")
public class RoleDao extends BaseDao<Role> implements IRoleDao {

	@Override
	public List<Role> listRole() {
		return this.list("from Role");
	}

	@Override
	public void deleteRoleUsers(int rid) {
		this.updateByHql("delete from UserRole ur where ur.role.id=?", rid);
	}

}
