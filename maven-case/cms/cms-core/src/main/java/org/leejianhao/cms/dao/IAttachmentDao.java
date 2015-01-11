package org.leejianhao.cms.dao;

import java.util.List;

import org.leejianhao.basic.dao.IBaseDao;
import org.leejianhao.basic.model.Pager;
import org.leejianhao.cms.model.Attachment;

public interface IAttachmentDao extends IBaseDao<Attachment> {
	/**
	 * 获取没有被引用的附件
	 * @return
	 */
	public Pager<Attachment> findNoUseAttachment();
	
	public long findNoUseAttachmentNum();
	
	/**
	 * 清空没有被引用的附件
	 */
	public void clearNoUseAttachment();
	
	/**
	 * 删除某个文章的所有附件
	 * @param tid
	 */
	public void deleteByTopic(int tid);
	
	/**
	 * 获取某个文章 的附件
	 * @param tid
	 * @return
	 */
	public List<Attachment> listByTopic(int tid);
	
	/**
	 * 根据一个数量获取首页图片的附件信息
	 */
	public List<Attachment> listIndexPic(int num);
	
	/**
	 * 获取某个栏目中的附件图片信息
	 * @param cid
	 * @return
	 */
	public Pager<Attachment> findChannelPic(int cid);
	
	/**
	 * 获取某篇文章的属于附件类型的附件对象
	 * @param tid
	 * @return
	 */
	public List<Attachment> listAttachByTopic(int tid);
	
	/**
	 * 获取所有的新闻图片信息
	 * @return
	 */
	public Pager<Attachment> listAllIndexPic();
	
	
}
