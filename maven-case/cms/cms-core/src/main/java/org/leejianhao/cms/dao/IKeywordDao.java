package org.leejianhao.cms.dao;

import java.util.List;

import org.leejianhao.basic.dao.IBaseDao;
import org.leejianhao.basic.model.Pager;
import org.leejianhao.cms.model.Keyword;

public interface IKeywordDao extends IBaseDao<Keyword> {
	
	/**
	 * 添加或者更新关键字
	 */
	public void saveOrUpdate(String name);
	
	/**
	 * 根据引用次数获取关键字，获取大于等于num次的关键字
	 * @return
	 */
	public List<Keyword> listChiefKeyword(int num);
	
	/**
	 * 查找没有使用的关键字
	 * @return
	 */
	public Pager<Keyword> findNoUseKeyword();
	/**
	 * 清空没有使用的关键字
	 */
	public void clearNoUseKeyword();
	
	/**
	 * 查找正在被引用的关键字
	 */
	public List<Keyword> findUseKeyword();
	
	/**
	 * 模糊查询
	 * @param con
	 * @return
	 */
	public List<String> listKeywordStringByCon(String con);
	
	/**
	 * 根据某个条件从keyword表中查询关键字
	 * @param con
	 * @return
	 */
	public List<Keyword> listKeywordByCon(String con);
	
}
