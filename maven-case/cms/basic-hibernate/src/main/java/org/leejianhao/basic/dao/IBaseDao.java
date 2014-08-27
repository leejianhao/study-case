package org.leejianhao.basic.dao;

import java.util.List;
import java.util.Map;

import org.leejianhao.basic.model.Pager;

/**
 * 公共的DAO处理对象，这个对象中包含了Hibernate的所有几本操作和对SQL的操作
 * @author leejianhao
 *
 * @param <T>
 */
public interface IBaseDao<T> {
	
	/**
	 * 添加对象
	 * @param t
	 * @return
	 */
	public T add(T t);
	/**
	 * 更新对象
	 * @param t
	 */
	public void update(T t);
	/**
	 * 根据id删除对象
	 * @param id
	 */
	public void delete(int id);
	/**
	 * 根据id加载对象
	 * @param id
	 * @return
	 */
	public T load(int id);
	
	/**
	 * 不分页列表对象
	 * @param hql 查询对象的hql
	 * @param args 查询参数
	 * @return 一组不分页的列表对象
	 */
	public List<T> list(String hql, Object[] args);
	public List<T> list(String hql, Object args);
	public List<T> list(String hql);
	
	/**
	 * 基于别名和查询参数的混合列表对象
	 * @param hql 查询对象的hql
	 * @param args 查询参数
	 * @param alias 别名对象
	 * @return
	 */
	public List<T> list(String hql, Object[] args, Map<String,Object> alias);
	public List<T> listByAlias(String hql, Map<String,Object> alais);
	
	/**
	 * 分页列表对象
	 * @param hql 查询对象的hql
	 * @param args 查询参数
	 * @return 一组不分页的列表对象
	 */
	public Pager<T> find(String hql, Object[] args);
	public Pager<T> find(String hql, Object args);
	public Pager<T> find(String hql);
	
	/**
	 * 基于别名和查询参数的混合列表对象
	 * @param hql 查询对象的hql
	 * @param args 查询参数
	 * @param alias 别名对象
	 * @return
	 */
	public Pager<T> find(String hql, Object[] args, Map<String,Object> alias);
	public Pager<T> findByAlias(String hql, Map<String,Object> alais);
	
	/**
	 * 根据hql查询一组 对象
	 * @param hql
	 * @param args
	 * @return
	 */
	public Object queryObject(String hql, Object[] args);
	public Object queryObject(String hql, Object args);
	public Object queryObject(String hql);
	public Object queryObject(String hql, Object[] args, Map<String, Object> alias);
	public Object queryObjectByAlias(String hql, Map<String, Object> alias);
	
	/**
	 * 根据hql更新对象
	 * @param hql
	 * @param args
	 */
	public void updateByHql(String hql, Object[] args);
	public void updateByHql(String hql, Object args);
	public void updateByHql(String hql);
	
	/**
	 * 根据SQL查询对象，不包含关联对象
	 * ps:bysql不应该使用泛型，根据查询结果思考得出
	 * @param sql 查询的sql语句
	 * @param args 查询的条件
	 * @param clz	查询的实体对象
	 * @param hasEntity 该对象是否是一个hibernate所管理的实体，如果不是需要使用setResultTransform查询
	 * @return 一组对象
	 */
	public List<Object> listBySql(String sql, Object[] args, Class<Object> clz, boolean hasEntity);
	public List<Object> listBySql(String sql, Object args, Class<Object> clz, boolean hasEntity);
	public List<Object> listBySql(String sql, Class<Object> clz, boolean hasEntity);
	public List<Object> listBySql(String sql, Object[] args, Map<String,Object> alias, Class<Object> clz, boolean hasEntity);
	public List<Object> listByAliasSql(String sql, Map<String,Object> alias, Class<Object> clz, boolean hasEntity);
	
	/**
	 * 根据SQL查询对象，不包含关联对象
	 * @param sql 查询的sql语句
	 * @param args 查询的条件
	 * @param clz	查询的实体对象
	 * @param hasEntity 该对象是否是一个hibernate所管理的实体，如果不是需要使用setResultTransform查询
	 * @return 一组对象
	 */
	public Pager<Object> findBySql(String sql, Object[] args, Class<Object> clz, boolean hasEntity);
	public Pager<Object> findBySql(String sql, Object args, Class<Object> clz, boolean hasEntity);
	public Pager<Object> findBySql(String sql, Class<Object> clz, boolean hasEntity);
	public Pager<Object> findBySql(String sql, Object[] args, Map<String,Object> alias, Class<Object> clz, boolean hasEntity);
	public Pager<Object> findByAliasSql(String sql, Map<String,Object> alias, Class<Object> clz, boolean hasEntity);
	
}