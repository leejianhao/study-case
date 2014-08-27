package org.leejianhao.basic.model;

import java.util.List;

/**
 * 分页对象
 * @author leejianaho
 *
 * @param <T>
 */
public class Pager<T> {
	/**
	 * 分页大小
	 */
	private int size;
	/**
	 * 分页的起始页
	 */
	private int offset;
	/**
	 * 总记录数
	 */
	private long total;
	/**
	 * 分页的数量
	 */
	private List<T> dates;
	
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public List<T> getDates() {
		return dates;
	}
	public void setDates(List<T> dates) {
		this.dates = dates;
	}
	
	
}