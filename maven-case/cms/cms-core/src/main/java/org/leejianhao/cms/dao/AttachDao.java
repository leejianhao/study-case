package org.leejianhao.cms.dao;

import java.util.List;

import org.leejianhao.basic.dao.BaseDao;
import org.leejianhao.basic.model.Pager;
import org.leejianhao.cms.model.Attachment;
import org.springframework.stereotype.Repository;

@Repository("attachmentDao")
public class AttachDao extends BaseDao<Attachment> implements IAttachmentDao {

	private String getAttachmentSelect() {
		return "select new Attachment(a.id,a.newName,a.oldName,a.type,a.suffix,a.size,a.isIndexPic,a.isImg,a.isAttach,a.topic.id)";
		
	}
	@Override
	public Pager<Attachment> findNoUseAttachment() {
		String hql = "select a from Attachment a where a.topic is null";
		return this.find(hql);
	}

	@Override
	public void clearNoUseAttachment() {
		String hql = "delete Attachment a where a.topic is null";
		this.updateByHql(hql);

	}

	@Override
	public void deleteByTopic(int tid) {
		String hql = "delete Attachment a where a.topic.id = ?";
		this.updateByHql(hql, tid);
	}

	@Override
	public List<Attachment> listByTopic(int tid) {
		String hql = getAttachmentSelect()+" from Attachment a where a.topic.id=?";
		return this.list(hql,tid);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Attachment> listIndexPic(int num) {
		String hql = getAttachmentSelect()+" from Attachment a where a.isIndexPic=? and a.topic.status=1";
		return this.getSession().createQuery(hql).setParameter(0, 1)
				.setFirstResult(0).setMaxResults(num).list();
		
	}

	@Override
	public Pager<Attachment> findChannelPic(int cid) {
		String hql = getAttachmentSelect()+" from Attachment a where a.topic.status=1 and"+
				" a.topic.channel.id=? and a.id=a.topic.channelPicId";
		return this.find(hql,cid);
	}

	@Override
	public List<Attachment> listAttachByTopic(int tid) {
		return this.list(getAttachmentSelect()+" from Attachment a where a.topic.id = ? and a.isAttach=1",tid);
	}

	@Override
	public Pager<Attachment> listAllIndexPic() {
		String hql = getAttachmentSelect()+" from Attachment a where a.isImg=? and a.topic.status=1";
		return this.find(hql,1);
	}

	@Override
	public long findNoUseAttachmentNum() {
		String hql = "select count(*) from Attachment a where a.topic is null";
		return (Long)this.getSession().createQuery(hql).uniqueResult();
	}
	
}