package org.leejianhao.basic.dao;

import java.util.List;
import java.util.Map;

import org.leejianhao.basic.dao.IBaseDao;
import org.leejianhao.basic.model.Pager;
import org.leejianhao.basic.model.User;

public interface IUserDao extends IBaseDao<User> {

	List<User> list(String string, Object[] objects);

	List<User> list(String string, Object[] objects, Map<String, Object> alias);

	Pager<User> find(String string, Object[] objects);

	Pager<User> find(String string, Object[] objects, Map<String, Object> alias);

	List<User> listUserBySql(String string, Object[] objects, Class<User> class1,
			boolean b);

	List<User> listUserBySql(String string, Object[] objects,
			Map<String, Object> alias, Class<User> class1, boolean b);

	Pager<User> findUserBySql(String string, Object[] objects, Class<User> class1,
			boolean b);

	Pager<User> findUserBySql(String string, Object[] objects,
			Map<String, Object> alias, Class<User> class1, boolean b);

}
