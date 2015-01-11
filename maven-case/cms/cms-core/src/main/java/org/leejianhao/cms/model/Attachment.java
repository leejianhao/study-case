package org.leejianhao.cms.model;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="t_attachment")
public class Attachment {
	private int id;
	/**
	 * 附件上传之后的名称
	 */
	private String newName;
	/**
	 * 附件的原始名称
	 */
	private String oldName;
	/**
	 * 附件的类型，这个类型和contentType类型一致
	 */
	private String type;
	/**
	 * 附件 的后缀名
	 */
	private String suffix;
	/**
	 * 附件的大小
	 */
	private long size;
	/**
	 * 该附件是否为主页图片
	 */
	private int isIndexPic;
	/**
	 * 该附件是否为图片类型
	 */
	private int isImg;
	
	/**
	 * 是否是附件信息，0表示不是，1表示是
	 * 如果是附件信息就在文章的附件进行显示
	 */
	private int isAttach;
	/**
	 * 附件所属文章
	 */
	private Topic topic;
	
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Column(name="new_name")
	public String getNewName() {
		return newName;
	}
	public void setNewName(String newName) {
		this.newName = newName;
	}
	@Column(name="old_name")
	public String getOldName() {
		return oldName;
	}
	public void setOldName(String oldName) {
		this.oldName = oldName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSuffix() {
		return suffix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	@Column(name="is_index_pic")
	public int getIsIndexPic() {
		return isIndexPic;
	}
	public void setIsIndexPic(int isIndexPic) {
		this.isIndexPic = isIndexPic;
	}
	@Column(name="is_img")
	public int getIsImg() {
		return isImg;
	}
	public void setIsImg(int isImg) {
		this.isImg = isImg;
	}
	
	@Column(name="is_attach")
	public int getIsAttach() {
		return isAttach;
	}
	public void setIsAttach(int isAttach) {
		this.isAttach = isAttach;
	}
	@ManyToOne
	@JoinColumn(name="tid")
	public Topic getTopic() {
		return topic;
	}
	public void setTopic(Topic topic) {
		this.topic = topic;
	}
	public Attachment() {
		
	}
	public Attachment(int id, String newName, String oldName, String type,
			String suffix, long size, int isIndexPic, int isImg, int isAttach, int tid) {
		super();
		this.id = id;
		this.newName = newName;
		this.oldName = oldName;
		this.type = type;
		this.suffix = suffix;
		this.size = size;
		this.isIndexPic = isIndexPic;
		this.isImg = isImg;
		this.isAttach = isAttach;
		this.topic = new Topic();
		this.topic.setId(tid);
	}
	 
	
}