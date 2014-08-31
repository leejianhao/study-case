package org.leejianhao.cms.dao;

import java.util.List;
import java.util.Map;

import org.leejianhao.basic.dao.BaseDao;
import org.leejianhao.basic.model.Pager;
import org.leejianhao.cms.model.Role;
import org.springframework.stereotype.Repository;

@Repository("roleDao")
public class RoleDao extends BaseDao<Role> implements IRoleDao {

}
