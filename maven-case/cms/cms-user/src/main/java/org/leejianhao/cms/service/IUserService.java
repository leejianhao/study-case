package org.leejianhao.cms.service;

import java.util.List;

import org.leejianhao.basic.model.Pager;
import org.leejianhao.cms.model.Group;
import org.leejianhao.cms.model.Role;
import org.leejianhao.cms.model.User;

public interface IUserService {
	/**
	 * 添加用户，需要判断用户是否存在，如果存在抛出异常
	 * @param user 用户对象
	 * @param rids 用户的所有角色信息
	 * @param gids 用户的所有组信息
	 */
	public void add(User user,Integer[] rids, Integer[] gids);
	
	/**
	 * 删除用户，注意需要把用户和角色和组的关系删除
	 * 如果用户存在相应文章不能删除
	 * @param id
	 */
	public void delete(int id);
	
	/**
	 * 用户的更新，如果rids中的角色在用户中已经存在，就不做操作
	 * 如果rids中的角色在用户中不存在就添加，如果用户中的角色不存在于rids中需要进行删除
	 * 对于group而言同样要做操作
	 * @param user
	 * @param rids
	 * @param gids
	 */
	public void update(User user,Integer[] rids, Integer[] gids);
	
	public void update(User user);
	
	public void updatePwd(int uid, String oldPwd, String newPwd);
	/**
	 * 更新用户状态
	 * @param id
	 */
	public void updateStatus(int id);

	/**
	 * 列表用户
	 */
	public Pager<User> findUser(); 
		
	/**
	 * 获取用户信息
	 * @param id
	 */
	public User load(int id);
	
	/**
	 * 获取用户的所有角色信息
	 * @param id
	 * @return
	 */
	public List<Role> listUserRoles(int id);
	
	/**
	 * 获取用户的所有组信息
	 * @param id
	 * @return
	 */
	public List<Group> listUserGroups(int id);
	
	/**
	 * 获取用户的所有角色id
	 * @param id
	 * @return
	 */
	public List<Integer> listUserRoleIds(int id);
	
	/**
	 * 获取用户所有组id
	 * @param id
	 * @return
	 */
	public List<Integer> listUserGroupIds(int id);
	
	/**
	 * 列出该组下的所有用户
	 * @param gid
	 * @return
	 */
	public List<User> listGroupUsers(int gid);
	
	/**
	 * 列出该角色的所有用户
	 * @param gid
	 * @return
	 */
	public List<User> listRoleUsers(int rid);  
	
	public User login(String username, String password);
}
