package org.leejianhao.cms.service;

import java.util.List;

import javax.inject.Inject;

import org.leejianhao.basic.model.Pager;
import org.leejianhao.cms.dao.IChannelDao;
import org.leejianhao.cms.dao.IGroupDao;
import org.leejianhao.cms.dao.IUserDao;
import org.leejianhao.cms.model.Channel;
import org.leejianhao.cms.model.ChannelTree;
import org.leejianhao.cms.model.CmsException;
import org.leejianhao.cms.model.Group;
import org.leejianhao.cms.model.GroupChannel;
import org.leejianhao.cms.model.User;
import org.springframework.stereotype.Service;

@Service("groupService")
public class GroupService implements IGroupService {

	public IGroupDao getGroupDao() {
		return groupDao;
	}
	
	@Inject
	public void setGroupDao(IGroupDao groupDao) {
		this.groupDao = groupDao;
	}

	public IUserDao getUserDao() {
		return userDao;
	}
	@Inject
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}
	

	public IChannelDao getChannelDao() {
		return channelDao;
	}

	@Inject
	public void setChannelDao(IChannelDao channelDao) {
		this.channelDao = channelDao;
	}

	private IGroupDao groupDao;
	private IUserDao userDao;
	private IChannelDao channelDao;
	
	@Override
	public void add(Group group) {
		groupDao.add(group);
	}

	@Override
	public void delete(int id) {
		List<User> users = userDao.listGroupUsers(id);
		if(users!=null && !users.isEmpty()) {
			throw new CmsException("删除的组中还有用户，不能删除");
		}
		groupDao.delete(id);
	}

	@Override
	public Group load(int id) {
		return groupDao.load(id);
	}

	@Override
	public void update(Group group) {
		groupDao.update(group);
	}

	@Override
	public List<Group> listGroup() {
		return groupDao.listGroup();
	}

	@Override
	public Pager<Group> findGroup() {
		return groupDao.findGroup();
	}

	@Override
	public void deleteGroupUsers(int id) {
		groupDao.deleteGroupUsers(id);
	}

	@Override
	public void addGroupChannel(int gid, int cid) {
		Group g = groupDao.load(gid);
		Channel c = channelDao.load(cid);
		if(c==null || g==null) throw new CmsException("要添加的组频道关联对象不存在");
		groupDao.addGroupChannel(g, c);
	}

	@Override
	public GroupChannel loadGroupChannel(int gid, int cid) {
		return groupDao.loadGroupChannel(gid, cid);
	}

	@Override
	public void clearGroupChannel(int gid) {
		groupDao.clearGroupChannel(gid);
	}

	@Override
	public void deleteGroupChannel(int gid, int cid) {
		groupDao.deleteGroupChannel(gid, cid);
	}

	@Override
	public List<Integer> listGroupChannelIds(int gid) {
		return groupDao.listGroupChannelIds(gid);
	}

	@Override
	public List<ChannelTree> generateGroupChannelTree(int gid) {
		return groupDao.generateGroupChannelTree(gid);
	}

	@Override
	public List<ChannelTree> generateUserChannelTree(int gid) {
		return groupDao.generateUserChannelTree(gid);
	}

}
