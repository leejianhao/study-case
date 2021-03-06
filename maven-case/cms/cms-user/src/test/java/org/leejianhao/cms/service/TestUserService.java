package org.leejianhao.cms.service;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.easymock.EasyMock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.leejianhao.basic.model.Pager;
import org.leejianhao.cms.dao.IGroupDao;
import org.leejianhao.cms.dao.IRoleDao;
import org.leejianhao.cms.dao.IUserDao;
import org.leejianhao.cms.model.CmsException;
import org.leejianhao.cms.model.Group;
import org.leejianhao.cms.model.Role;
import org.leejianhao.cms.model.RoleType;
import org.leejianhao.cms.model.User;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-beans.xml")
public class TestUserService {
	@Inject
	private IUserService userService;
	@Inject
	private IRoleDao roleDao;
	@Inject
	private IUserDao userDao;
	@Inject
	private IGroupDao groupDao;
	
	private User baseUser = new User(1,"admin1","123","admin1","admin1@126.com","110",0);
	
	@Test
	public void testDelete() {
		reset(userDao);
		int uid = 2;
		userDao.deleteUserGroups(uid);
		expectLastCall();
		userDao.deleteUserRoles(uid);
		expectLastCall();
		userDao.delete(uid);
		expectLastCall();
		replay(userDao);
		userService.delete(uid);
		verify(userDao);
		
	}
	
	@Test
	public void testUpdateStatus() {
		reset(userDao);
		int uid = 2;
		expect(userDao.load(uid)).andReturn(baseUser);
		userDao.update(baseUser);
		expectLastCall();
		replay(userDao);
		userService.updateStatus(uid);
		assertEquals(baseUser.getStatus(), 1);
		verify(userDao);
	}
	
	@Test(expected=CmsException.class)
	public void testUpdateStautsNoUser() {
		reset(userDao);
		int uid = 2;
		expect(userDao.load(uid)).andReturn(null);
		userDao.update(baseUser);
		expectLastCall();
		replay(userDao);
		userService.updateStatus(uid);
		assertEquals(baseUser.getStatus(), 1);
		verify(userDao);
	}
	
	@Test
	public void testFindUser() {
		reset(userDao);
		expect(userDao.findUser()).andReturn(new Pager<User>());
		expectLastCall();
		replay(userDao);	
		userService.findUser();
		verify(userDao);
	}
	
	@Test
	public void testAdd() {
		reset(userDao,groupDao,roleDao);
		Integer[] rids = {1,2};
		Integer[] gids = {2,3};
		Role r = new Role(1,"管理员",RoleType.ROLE_ADMIN);
		Group g = new Group(2,"财务处");
		expect(userDao.loadByUsername("admin1")).andReturn(null);
		expect(userDao.add(baseUser)).andReturn(baseUser);
		expect(roleDao.load(rids[0])).andReturn(r);
		userDao.addUserRole(baseUser, r);
		expectLastCall();
		r.setId(2);
		expect(roleDao.load(rids[1])).andReturn(r);
		userDao.addUserRole(baseUser, r);
		expectLastCall();
		expect(groupDao.load(gids[0])).andReturn(g);
		userDao.addUserGroup(baseUser, g);
		expectLastCall();
		g.setId(3);
		expect(groupDao.load(gids[1])).andReturn(g);
		userDao.addUserGroup(baseUser, g);
		expectLastCall();
		replay(userDao,groupDao,roleDao);
		
		userService.add(baseUser, rids, gids);
		verify(userDao,groupDao,roleDao);
	}
	
	@Test
	public void testAdd2() {
		reset(userDao,groupDao,roleDao);
		Integer[] rids = {1,2};
		Integer[] gids = {2,3};
		Role r = new Role(1,"管理员",RoleType.ROLE_ADMIN);
		Group g = new Group(2,"财务处");
		
		expect(userDao.loadByUsername("admin1")).andReturn(null);
		//添加role
		expect(userDao.add(baseUser)).andReturn(baseUser);
		//动态参数 
		expect(roleDao.load(rids[EasyMock.gt(0)])).andReturn(r).times(2);
		userDao.addUserRole(baseUser, r);
		expectLastCall().times(2);
		
		//添加group
		expect(groupDao.load(gids[EasyMock.gt(0)])).andReturn(g).times(2);
		userDao.addUserGroup(baseUser, g);
		expectLastCall().times(2);
		
		replay(userDao,groupDao,roleDao);
		
		userService.add(baseUser, rids, gids);
		verify(userDao,groupDao,roleDao);
	}
	
	@Test(expected=CmsException.class)
	public void testAddNoRole() {
		reset(userDao,groupDao,roleDao);
		Integer[] rids = {1,2};
		Integer[] gids = {2,3};
		Role r = new Role(1,"管理员",RoleType.ROLE_ADMIN);
		Group g = new Group(2,"财务处");
		
		expect(userDao.loadByUsername("admin1")).andReturn(null);
		//添加role
		expect(userDao.add(baseUser)).andReturn(baseUser);
		//动态参数 
		expect(roleDao.load(rids[EasyMock.gt(0)])).andReturn(null).times(2);
		userDao.addUserRole(baseUser, r);
		expectLastCall().times(2);
		
		//添加group
		expect(groupDao.load(gids[EasyMock.gt(0)])).andReturn(g).times(2);
		userDao.addUserGroup(baseUser, g);
		expectLastCall().times(2);
		
		replay(userDao,groupDao,roleDao);
		
		userService.add(baseUser, rids, gids);
		verify(userDao,groupDao,roleDao);
	}
	
	@Test(expected=CmsException.class)
	public void testAddNoGroup() {
		reset(userDao,groupDao,roleDao);
		Integer[] rids = {1,2};
		Integer[] gids = {2,3};
		Role r = new Role(1,"管理员",RoleType.ROLE_ADMIN);
		Group g = new Group(2,"财务处");
		
		expect(userDao.loadByUsername("admin1")).andReturn(null);
		//添加role
		expect(userDao.add(baseUser)).andReturn(baseUser);
		//动态参数 
		expect(roleDao.load(rids[EasyMock.gt(0)])).andReturn(r).times(2);
		userDao.addUserRole(baseUser, r);
		expectLastCall().times(2);
		
		//添加group
		expect(groupDao.load(gids[EasyMock.gt(0)])).andReturn(null).times(2);
		userDao.addUserGroup(baseUser, g);
		expectLastCall().times(2);
		
		replay(userDao,groupDao,roleDao);
		
		userService.add(baseUser, rids, gids);
		verify(userDao,groupDao,roleDao);
	}
	
	@Test
	public void testUpdateUser() {
		reset(userDao,groupDao,roleDao);
		Integer[] nids = {1,2};
		List<Integer> eids = Arrays.asList(2,3);
		Role r = new Role();
		Group g = new Group();
		
		
		//验证获取存在的角色id和组id
		expect(userDao.listUserRoleIds(baseUser.getId())).andReturn(eids);
		expect(userDao.listUserGroupIds(baseUser.getId())).andReturn(eids);
		expect(roleDao.load(1)).andReturn(r);
		//验证添加角色和组是否正确
		userDao.addUserRole(baseUser, r);
		expectLastCall();
		expect(groupDao.load(1)).andReturn(g);
		userDao.addUserGroup(baseUser, g);
		expectLastCall();
		
		//验证删除角色和组
		userDao.deleteUserRole(baseUser.getId(), 3);
		userDao.deleteUserGroup(baseUser.getId(), 3);
		
		replay(userDao,groupDao,roleDao);
		userService.update(baseUser, nids, nids);
		verify(userDao,groupDao,roleDao);
		
	}
}
