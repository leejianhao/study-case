/**
 * 
 */
package org.leejianhao.basic.dao;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.leejianhao.basic.model.Pager;
import org.leejianhao.basic.model.SystemContext;

/**
 * @author leejianhao
 *
 */
@SuppressWarnings("unchecked")
public class BaseDao<T> implements IBaseDao<T> {
	
	private SessionFactory sessionFactory;
	
	private Class<Object> clz;
	/**
	 * 创建一个Class的对象来获取泛型的class 
	 */
	public Class<Object> getClz() {
		if(clz==null) {
			//获取泛型的Class对象
			clz=(Class<Object>)(((ParameterizedType)(this.getClass().getGenericSuperclass())).getActualTypeArguments()[0]);
			
		}
		return clz;
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	@Inject
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	protected Session getSession() {
		return sessionFactory.openSession();
	}
	/* (non-Javadoc)
	 * @see org.leejianhao.basic.dao.IBaseDao#add(java.lang.Object)
	 */
	@Override
	public T add(T t) {
		getSession().save(t);
		return t;
	}

	/* (non-Javadoc)
	 * @see org.leejianhao.basic.dao.IBaseDao#update(java.lang.Object)
	 */
	@Override
	public void update(T t) {
		getSession().update(t);
	}

	/* (non-Javadoc)
	 * @see org.leejianhao.basic.dao.IBaseDao#delete(int)
	 */
	public void delete(int id) {
		getSession().delete(this.load(id));
	}

	/* (non-Javadoc)
	 * @see org.leejianhao.basic.dao.IBaseDao#load(int)
	 */
	@Override
	public T load(int id) {
		return (T)getSession().load(getClz(), id);
	}

	/* (non-Javadoc)
	 * @see org.leejianhao.basic.dao.IBaseDao#list(java.lang.String, java.lang.Object[])
	 */
	@Override
	public List<T> list(String hql, Object[] args) {
		
		return this.list(hql,args,null);
	}

	/* (non-Javadoc)
	 * @see org.leejianhao.basic.dao.IBaseDao#list(java.lang.String, java.lang.Object)
	 */
	@Override
	public List<T> list(String hql, Object args) {
		return this.list(hql,new Object[]{args});
	}

	/* (non-Javadoc)
	 * @see org.leejianhao.basic.dao.IBaseDao#list(java.lang.String)
	 */
	@Override
	public List<T> list(String hql) {
		return this.list(hql,null);
	}
	
	private String initSort(String hql) {
		String order = SystemContext.getOrder();
		String sort = SystemContext.getSort();
		if(StringUtils.isNotBlank(sort)) {
			hql += " order by "+sort;
			if(!"desc".equals(order)) hql+=" asc";
			else hql+=" desc";
		}
		return hql;
	}
	
	@SuppressWarnings("rawtypes")
	private void setAliasParameter(Query query, Map<String, Object> alias) {
		if(alias != null) { 
			Set<String> keys = alias.keySet();
			for(String key : keys) {
				Object val = alias.get(key);
				if(val instanceof Collection) {
					//查询条件是列表
					query.setParameterList(key, (Collection)val);
				} else {
					query.setParameter(key, val);
				}
			} 
		}
	}
	
	private void setParameter(Query query, Object[] args) {
		if(args!=null &&args.length>0) {
			int index = 0;
			for(Object arg :args) {
				query.setParameter(index++, arg);
			}
		}
	}
	/* (non-Javadoc)
	 * @see org.leejianhao.basic.dao.IBaseDao#list(java.lang.String, java.lang.Object[], java.util.Map)
	 */
	@Override
	public List<T> list(String hql, Object[] args, Map<String, Object> alias) {
		hql = initSort(hql);
		
		Query query = getSession().createQuery(hql);
		setAliasParameter(query, alias);
		setParameter(query, args);
		
 		return query.list();
	}

	/* (non-Javadoc)
	 * @see org.leejianhao.basic.dao.IBaseDao#list(java.lang.String, java.util.Map)
	 */
	@Override
	public List<T> listByAlias(String hql, Map<String, Object> alias) {
		return this.list(hql,null,alias);
	}

	/* (non-Javadoc)
	 * @see org.leejianhao.basic.dao.IBaseDao#find(java.lang.String, java.lang.Object[])
	 */
	@Override
	public Pager<T> find(String hql, Object[] args) {
		
		return this.find(hql,args,null);
	}

	/* (non-Javadoc)
	 * @see org.leejianhao.basic.dao.IBaseDao#find(java.lang.String, java.lang.Object)
	 */
	@Override
	public Pager<T> find(String hql, Object args) {
		return this.find(hql,new Object[]{args});
	}

	/* (non-Javadoc)
	 * @see org.leejianhao.basic.dao.IBaseDao#find(java.lang.String)
	 */
	@Override
	public Pager<T> find(String hql) {
		return this.find(hql,null);
	}

	@SuppressWarnings("rawtypes")
	private void setPagers(Query query, Pager pages) {
		Integer pageSize = SystemContext.getPageSize();
		Integer pageOffset = SystemContext.getPageOffset();
		if(pageOffset==null || pageOffset<0) pageOffset = 15;
		if(pageSize==null || pageSize<0) pageSize = 15;
		pages.setOffset(pageOffset);
		pages.setSize(pageSize);
		query.setFirstResult(pageOffset).setMaxResults(pageSize);
				
	}
	
	private String getCountHql(String hql, boolean isHql) {
		String e = hql.substring(hql.indexOf("from"));
		String c = "select count(*) "+e;
		if(isHql)c.replaceAll("fetch", "");
		return c;
	}
	/* (non-Javadoc)
	 * @see org.leejianhao.basic.dao.IBaseDao#find(java.lang.String, java.lang.Object[], java.util.Map)
	 */
	@Override
	public Pager<T> find(String hql, Object[] args, Map<String, Object> alias) {
		hql = initSort(hql);
		String cq = getCountHql(hql, true);
		cq = initSort(hql);
		Query cquery = getSession().createQuery(cq);
		Query query = getSession().createQuery(hql);
		//设置别名
		setAliasParameter(query, alias);
		setAliasParameter(cquery, alias);
		//设置参数
		setParameter(query, args);
		setParameter(cquery, args);
		Pager<T> pages = new Pager<T>();
		setPagers(query,pages);
		List<T> dates = query.list();
		pages.setDates(dates);
		long total = (Long)cquery.uniqueResult();
		pages.setTotal(total);
		return pages;
	}

	/* (non-Javadoc)
	 * @see org.leejianhao.basic.dao.IBaseDao#find(java.lang.String, java.util.Map)
	 */
	@Override
	public Pager<T> findByAlias(String hql, Map<String, Object> alias) {
		return this.find(hql,null,alias);
	}

	/* (non-Javadoc)
	 * @see org.leejianhao.basic.dao.IBaseDao#queryObject(java.lang.String, java.lang.Object[])
	 */
	@Override
	public Object queryObject(String hql, Object[] args) {
		return this.queryObject(hql,args,null);
	}

	/* (non-Javadoc)
	 * @see org.leejianhao.basic.dao.IBaseDao#queryObject(java.lang.String, java.lang.Object)
	 */
	@Override
	public Object queryObject(String hql, Object args) {
		return this.queryObject(hql,new Object[]{args});
	}

	/* (non-Javadoc)
	 * @see org.leejianhao.basic.dao.IBaseDao#queryObject(java.lang.String)
	 */
	@Override
	public Object queryObject(String hql) {
		return this.queryObject(hql,null);
	}
	
	@Override
	public Object queryObject(String hql, Object[] args,
			Map<String, Object> alias) {
		Query query = getSession().createQuery(hql);
		setAliasParameter(query, alias);
		setParameter(query, args);
		return query.uniqueResult();
	}

	@Override
	public Object queryObjectByAlias(String hql, Map<String, Object> alias) {
		return this.queryObject(hql,null,alias);
	}
	
	/* (non-Javadoc)
	 * @see org.leejianhao.basic.dao.IBaseDao#updateByHql(java.lang.String, java.lang.Object[])
	 */
	@Override
	public void updateByHql(String hql, Object[] args) {
		Query query = getSession().createQuery(hql);
		setParameter(query, args);
		query.executeUpdate();
	}

	/* (non-Javadoc)
	 * @see org.leejianhao.basic.dao.IBaseDao#updateByHql(java.lang.String, java.lang.Object)
	 */
	@Override
	public void updateByHql(String hql, Object args) {
		this.updateByHql(hql, new Object[]{args});
	}

	/* (non-Javadoc)
	 * @see org.leejianhao.basic.dao.IBaseDao#updateByHql(java.lang.String)
	 */
	@Override
	public void updateByHql(String hql) {
		this.updateByHql(hql,null);
	}

	/* (non-Javadoc)
	 * @see org.leejianhao.basic.dao.IBaseDao#listBySql(java.lang.String, java.lang.Object[], java.lang.Class, boolean)
	 */
	@Override
	public List<Object> listBySql(String sql, Object[] args, Class<Object> clz,
			boolean hasEntity) {
		return this.listBySql(sql, args, clz, hasEntity);
	}

	/* (non-Javadoc)
	 * @see org.leejianhao.basic.dao.IBaseDao#listBySql(java.lang.String, java.lang.Object, java.lang.Class, boolean)
	 */
	@Override
	public List<Object> listBySql(String sql, Object args, Class<Object> clz,
			boolean hasEntity) {
		return this.listBySql(sql, new Object[]{args}, clz, hasEntity);
	}

	/* (non-Javadoc)
	 * @see org.leejianhao.basic.dao.IBaseDao#listBySql(java.lang.String, java.lang.Class, boolean)
	 */
	@Override
	public List<Object> listBySql(String sql, Class<Object> clz, boolean hasEntity) {
		return this.listBySql(sql, null, clz, hasEntity);
	}

	/* (non-Javadoc)
	 * @see org.leejianhao.basic.dao.IBaseDao#listBySql(java.lang.String, java.lang.Object[], java.util.Map, java.lang.Class, boolean)
	 */
	@Override
	public List<Object> listBySql(String sql, Object[] args,
			Map<String, Object> alias, Class<Object> clz, boolean hasEntity) {
		sql = initSort(sql);
		SQLQuery sq = getSession().createSQLQuery(sql);
		setAliasParameter(sq, alias);
		setParameter(sq, args);
		if(hasEntity) {
			sq.addEntity(clz);
		}else {
			sq.setResultTransformer(Transformers.aliasToBean(clz));
		}
		return sq.list();
	}

	/* (non-Javadoc)
	 * @see org.leejianhao.basic.dao.IBaseDao#listBySql(java.lang.String, java.util.Map, java.lang.Class, boolean)
	 */
	@Override
	public List<Object> listByAliasSql(String sql, Map<String, Object> alias,
			Class<Object> clz, boolean hasEntity) {
		return this.listBySql(sql, null, alias, clz, hasEntity);
	}

	/* (non-Javadoc)
	 * @see org.leejianhao.basic.dao.IBaseDao#findBySql(java.lang.String, java.lang.Object[], java.lang.Class, boolean)
	 */
	@Override
	public Pager<Object> findBySql(String sql, Object[] args, Class<Object> clz,
			boolean hasEntity) {
		return this.findBySql(sql,args,null, clz, hasEntity);
	}

	/* (non-Javadoc)
	 * @see org.leejianhao.basic.dao.IBaseDao#findBySql(java.lang.String, java.lang.Object, java.lang.Class, boolean)
	 */
	@Override
	public Pager<Object> findBySql(String sql, Object args, Class<Object> clz,
			boolean hasEntity) {
		return this.findBySql(sql,new Object[]{args}, clz, hasEntity);
	}

	/* (non-Javadoc)
	 * @see org.leejianhao.basic.dao.IBaseDao#findBySql(java.lang.String, java.lang.Class, boolean)
	 */
	@Override
	public Pager<Object> findBySql(String sql, Class<Object> clz, boolean hasEntity) {
		return this.findBySql(sql,null, clz, hasEntity);
	}

	/* (non-Javadoc)
	 * @see org.leejianhao.basic.dao.IBaseDao#findBySql(java.lang.String, java.lang.Object[], java.util.Map, java.lang.Class, boolean)
	 */
	@Override
	public Pager<Object> findBySql(String sql, Object[] args,
			Map<String, Object> alias, Class<Object> clz, boolean hasEntity) {
		String cq = getCountHql(sql,false);
		cq = initSort(cq);
		sql = initSort(sql);
		SQLQuery sq = getSession().createSQLQuery(sql);
		SQLQuery cquery = getSession().createSQLQuery(cq);
		setAliasParameter(sq, alias);
		setAliasParameter(cquery, alias);
		setParameter(sq, args);
		setParameter(cquery, args);
		Pager<Object> pages = new Pager<Object>();
		setPagers(sq, pages);
		
		if(hasEntity) {
			sq.addEntity(clz);
		} else {
			sq.setResultTransformer(Transformers.aliasToBean(clz));
		}
		List<Object> dates = sq.list();
		pages.setDates(dates);
		long total = (Long)cquery.uniqueResult();
		pages.setTotal(total);
		return pages;
	}

	/* (non-Javadoc)
	 * @see org.leejianhao.basic.dao.IBaseDao#findBySql(java.lang.String, java.util.Map, java.lang.Class, boolean)
	 */
	@Override
	public Pager<Object> findByAliasSql(String sql, Map<String, Object> alias,
			Class<Object> clz, boolean hasEntity) {
		return this.findBySql(sql,null,alias, clz, hasEntity);
	}


}