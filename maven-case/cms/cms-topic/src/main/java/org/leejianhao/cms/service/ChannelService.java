package org.leejianhao.cms.service;

import java.util.List;

import javax.inject.Inject;

import org.leejianhao.cms.dao.IChannelDao;
import org.leejianhao.cms.model.Channel;
import org.leejianhao.cms.model.ChannelTree;
import org.leejianhao.cms.model.ChannelType;
import org.leejianhao.cms.model.CmsException;
import org.springframework.stereotype.Service;

@Service("channelService")
public class ChannelService implements IChannelService {

	@Inject
	private IChannelDao channelDao;
	
	public void add(Channel channel, Integer pid) {
		int orders = channelDao.getMaxOrderByParent(pid);
		if(pid !=null && pid>0) {
			Channel pc = channelDao.load(pid);
			if(pc==null) throw new CmsException("要添加栏目的父类对象不正确！");
			channel.setParent(pc);
		}
		channel.setOrders(orders+1);
		channelDao.add(channel);
	}

	public void update(Channel channel) {
		channelDao.update(channel);
	}

	public void delete(int id) {
		// TODO 1.需要判断是否存在子栏目
		List<Channel> cs = channelDao.listByParent(id);
		if(cs !=null && cs.size()>0) throw new CmsException("要删除 的栏目还有子栏目，无法删除！");
		//TODO 2.需要判断是否存在文章
		//TODO 3.需要删除和组的关联关系
		channelDao.delete(id);
	}

	public void clearTopic(int id) {
		//TODO 实现文章模块之后才实现该方法
	}

	public Channel load(int id) {
		return channelDao.load(id);
	}

	public List<Channel> listByParent(Integer pid) {
		return channelDao.listByParent(pid);
	}

	@Override
	public List<ChannelTree> generateTree() {
		return channelDao.generateTree();
	}

	@Override
	public List<ChannelTree> generateTreeByParent(Integer pid) {
		return channelDao.generateTreeByParent(pid);
	}
	
	@Override
	public void updateSort(Integer[] ids) {
		channelDao.updateSort(ids);
	}
	@Override
	public List<Channel> listPublishChannel() {
		return channelDao.listPublishChannel();
	}
	@Override
	public List<Channel> listTopNavChannel() {
		return channelDao.listTopNavChannel();
	}
	@Override
	public List<Channel> listAllIndexChannel(ChannelType ct) {
		return channelDao.listAllIndexChannel(ct);
	}

	@Override
	public Channel loadFirstChannelByNav(int cid) {
		return channelDao.loadFirstChannelByNav(cid);
	}

	@Override
	public List<Channel> listUseChannelByParent(Integer cid) {
		return channelDao.listUseChannelByParent(cid);
	}

	@Override
	public List<Channel> listChannelByType(ChannelType ct) {
		return channelDao.listChannelByType(ct);
	}
	
}
