package org.leejianhao.cms.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.leejianhao.basic.dao.BaseDao;
import org.leejianhao.basic.model.Pager;
import org.leejianhao.basic.util.PinyinUtil;
import org.leejianhao.cms.model.Keyword;
import org.springframework.stereotype.Repository;

@Repository("keywordDao")
public class KeywordDao extends BaseDao<Keyword> implements IKeywordDao {
	
	@Override
	public void saveOrUpdate(String name) {
		Keyword k = (Keyword) this.queryObject(" from Keyword where name=?",name);
		if(k==null) {
			k = new Keyword();
			k.setName(name);
			k.setName(PinyinUtil.str2Pinyin(name, null));
			k.setNameShortPy(PinyinUtil.strFirst2Pinyin(name));
			this.add(k);
		}else {
			k.setTimes(k.getTimes()+1);
		}
				
				
	}
	
	@Override
	public List<Keyword> listChiefKeyword(int num) { 
		String hql = "from Keyword where times>=?";
		return this.list(hql,num);
	}
	
	private Map<String, Integer> getExistKeyword2Map() {
		String hql = "select t.keyword from Topic t";
		List<String> allKeys = this.getSession().createQuery(hql).list();
		Map<String,Integer> keys = new HashMap<String, Integer>();
		for(String ak:allKeys) {
			String[] ks = ak.split("\\|");
			for(String k:ks) {
				if(k.trim().equals(""))continue;
				if(keys.containsKey(k)) {
					keys.put(k, keys.get(k)+1);
				} else {
					keys.put(k, 1);
				}
			}
		}
		return keys;
	}
	
	private Set<String> getExistkeywords() {
		return getExistKeyword2Map().keySet();
	}
	
	@Override
	public Pager<Keyword> findNoUseKeyword() {
		String hql = "from Keyword where name not in (:ks)";
		//得到一组存在的关键字名称
		Set<String> ks = getExistkeywords();
		Map<String,Object> alias = new HashMap<String, Object>();
		alias.put("ks", ks);
		return this.findByAlias(hql, alias);
	}

	@Override
	public void clearNoUseKeyword() {
		String hql = "delete Keyword k where k.name not in(:ks)";
		//得到一组存在的关键字名称
		Set<String> ks = getExistkeywords();
		this.getSession().createQuery(hql).setParameterList("ks", ks).executeUpdate();
	}

	@Override
	public List<Keyword> findUseKeyword() {
		Map<String, Integer> allkeys = getExistKeyword2Map();
		Set<String> keys = allkeys.keySet();
		List<Keyword> ks = new ArrayList<Keyword>();
		for(String k:keys) {
			 ks.add(new Keyword(k, allkeys.get(k)));
		}
		return ks;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> listKeywordStringByCon(String con) {
		String hql = "select name from Keyword where " +
				"name like '%"+con+"%' or nameFullPy like '%"+con+"%' or nameShortPy like '%"+con+"%'";
		return this.getSession().createQuery(hql).list();
	}
	
	@Override
	public List<Keyword> listKeywordByCon(String con) {
		String hql = "from Keyword where " +
				"name like '%"+con+"%' or nameFullPy like '%"+con+"%' or nameShortPy like '%"+con+"%'";
		return this.list(hql);
	}
	

}
 