package org.leejianhao.cms.service;

import java.util.List;

import javax.inject.Inject;

import org.leejianhao.cms.dao.IRoleDao;
import org.leejianhao.cms.dao.IUserDao;
import org.leejianhao.cms.model.CmsException;
import org.leejianhao.cms.model.Role;
import org.springframework.stereotype.Service;

@Service("roleService")
public class RoleService implements IRoleService {

	private IRoleDao roleDao;
	private IUserDao userDao;
	
	public IRoleDao getRoleDao() {
		return roleDao;
	}
	
	@Inject
	public void setRoleDao(IRoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public IUserDao getUserDao() {
		return userDao;
	}
	@Inject
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public void add(Role role) {
		roleDao.add(role);
	}

	@Override
	public void delete(int id) {
		List<org.leejianhao.cms.model.User> us = userDao.listRoleUsers(id);
		if(us!=null && !us.isEmpty()) throw new CmsException("删除的角色对象中还有用户，不能删除");
		roleDao.delete(id);
				
	}

	@Override
	public void update(Role role) {
		roleDao.update(role);
	}

	@Override
	public Role load(int id) {
		return roleDao.load(id);
	}

	@Override
	public List<Role> listRole() {
		return roleDao.listRole();
	}

	@Override
	public void deleteRoleUsers(int id) {
		roleDao.deleteRoleUsers(id);
	}

}
