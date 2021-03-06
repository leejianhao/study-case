package org.leejianhao.basic.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.leejianhao.basic.model.Pager;
import org.leejianhao.basic.model.SystemContext;
import org.leejianhao.basic.model.User;
import org.leejianhao.basic.test.util.AbstractDbUnitTestCase;
import org.leejianhao.basic.test.util.EntitiesHelper;
import org.springframework.orm.hibernate4.SessionHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/beans.xml")
public class TestUserDao extends AbstractDbUnitTestCase{
	@Inject
	private IUserDao userDao;
	@Inject
	private SessionFactory sessionFactory;
	
	@Before
	public void setUp() throws DataSetException, SQLException, IOException {
		this.backupAllTable();	
		Session s = sessionFactory.openSession();
		TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(s));

	}
	
	@Test
	public void testLoad() throws DatabaseUnitException, SQLException {
		IDataSet ds = createDateSet("t_user");
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, ds);
		User u = userDao.load(1);
		EntitiesHelper.assertUser(u);
	}
	
	@Test(expected=ObjectNotFoundException.class)
	public void testdelete() throws DatabaseUnitException, SQLException {
		IDataSet ds = createDateSet("t_user");
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, ds);
		userDao.delete(1);
		User tu = userDao.load(1);
		System.out.println(tu.getUsername());
	}
	
	@Test
	public void testListByArgs() throws DatabaseUnitException, SQLException {
		IDataSet ds = createDateSet("t_user");
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, ds);
		SystemContext.setOrder("desc");
		SystemContext.setSort("id");
		List<User> expected = userDao.list("from  User where id >? and id <?", new Object[]{1,4});
		List<User> actual = Arrays.asList(new User(3,"admin3"),new User(2,"admin2"));
		EntitiesHelper.assertUsers(expected, actual);
	}
	
	@Test
	public void testListByArgsAndAlias() throws DatabaseUnitException, SQLException {
		IDataSet ds = createDateSet("t_user");
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, ds);
		SystemContext.setOrder("asc");
		SystemContext.setSort("id");
		Map<String, Object> alias = new HashMap<String, Object>();
		alias.put("ids", Arrays.asList(1,2,3,4,5,6,7,8,9));
		List<User> expected = userDao.list("from  User where id >? and id <? and id in (:ids)", new Object[]{1,4}, alias);
		List<User> actual = Arrays.asList(new User(2,"admin2"),new User(3,"admin3"));
		
		assertNotNull(expected);
		assertTrue(expected.size()==2);
		EntitiesHelper.assertUsers(expected, actual);
	}
	
	@Test
	public void testFindByArgs() throws DatabaseUnitException, SQLException {
		IDataSet ds = createDateSet("t_user");
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, ds);
		SystemContext.setOrder("desc");
		SystemContext.setSort("id");
		SystemContext.setPageSize(3);
		SystemContext.setPageOffset(0);
		Pager<User> expected = userDao.find("from  User where id >=? and id <=?", new Object[]{1,10});
		List<User> actuals = Arrays.asList(new User(10,"admin10"),new User(9,"admin9"),new User(8,"admin8"));
		assertNotNull(expected);
		assertTrue(expected.getTotal()==10);
		assertTrue(expected.getOffset()==0);
		assertTrue(expected.getSize()==3);
		EntitiesHelper.assertUsers(expected.getDatas(), actuals);
	}
	
	@Test
	public void testFindByArgsAndAlias() throws DatabaseUnitException, SQLException {
		IDataSet ds = createDateSet("t_user");
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, ds);
		SystemContext.removeOrder();
		SystemContext.removeSort();
		SystemContext.setPageSize(3);
		SystemContext.setPageOffset(0);
		Map<String, Object> alias = new HashMap<String, Object>();
		alias.put("ids", Arrays.asList(1,2,4,5,6,7,8,10));
		
		Pager<User> expected = userDao.find("from  User where id >=? and id <=? and id in (:ids)", new Object[]{1,10}, alias);
		List<User> actuals = Arrays.asList(new User(1,"admin1"),new User(2,"admin2"),new User(4,"admin4"));
		assertNotNull(expected);
		assertTrue(expected.getTotal()==8);
		assertTrue(expected.getOffset()==0);
		assertTrue(expected.getSize()==3);
		EntitiesHelper.assertUsers(expected.getDatas(), actuals);
	}
	
	@Test
	public void testListSqlByArgs() throws DatabaseUnitException, SQLException {
		IDataSet ds = createDateSet("t_user");
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, ds);
		SystemContext.setOrder("desc");
		SystemContext.setSort("id");
		List<User> expected = userDao.listUserBySql("select * from  t_user where id >? and id <?", new Object[]{1,4},User.class, true);
		List<User> actual = Arrays.asList(new User(3,"admin3"),new User(2,"admin2"));
		EntitiesHelper.assertUsers(expected, actual);
	}
	
	@Test
	public void testListSqlByArgsAndAlias() throws DatabaseUnitException, SQLException {
		IDataSet ds = createDateSet("t_user");
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, ds);
		SystemContext.setOrder("asc");
		SystemContext.setSort("id");
		Map<String, Object> alias = new HashMap<String, Object>();
		alias.put("ids", Arrays.asList(1,2,3,4,5,6,7,8,9));
		List<User> expected = userDao.listUserBySql("select * from  t_user where id >? and id <? and id in (:ids)", new Object[]{1,4}, alias,User.class, true);
		List<User> actual = Arrays.asList(new User(2,"admin2"),new User(3,"admin3"));
		
		assertNotNull(expected);
		assertTrue(expected.size()==2);
		EntitiesHelper.assertUsers(expected, actual);
	}
	
	@Test
	public void testfindSqlByArgs() throws DatabaseUnitException, SQLException {
		IDataSet ds = createDateSet("t_user");
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, ds);
		SystemContext.setOrder("desc");
		SystemContext.setSort("id");
		SystemContext.setPageSize(3);
		SystemContext.setPageOffset(0);
		Pager<User> expected = userDao.findUserBySql("select * from  t_user where id >=? and id <=?", new Object[]{1,10},User.class, true);
		List<User> actuals = Arrays.asList(new User(10,"admin10"),new User(9,"admin9"),new User(8,"admin8"));
		assertNotNull(expected);
		assertTrue(expected.getTotal()==10);
		assertTrue(expected.getOffset()==0);
		assertTrue(expected.getSize()==3);
		EntitiesHelper.assertUsers(expected.getDatas(), actuals);
	}
	
	@Test
	public void testfindSqlByArgsAndAlias() throws DatabaseUnitException, SQLException {
		IDataSet ds = createDateSet("t_user");
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, ds);
		SystemContext.removeOrder();
		SystemContext.removeSort();
		SystemContext.setPageSize(3);
		SystemContext.setPageOffset(0);
		Map<String, Object> alias = new HashMap<String, Object>();
		alias.put("ids", Arrays.asList(1,2,4,5,6,7,8,9));
		Pager<User> expected = userDao.findUserBySql("select * from  t_user where id >=? and id <=? and id in (:ids)", new Object[]{1,4}, alias,User.class, true);
		List<User> actual = Arrays.asList(new User(1,"admin1"),new User(2,"admin2"),new User(4,"admin4"));;
		
		assertNotNull(expected);
		assertTrue(expected.getTotal()==3);
		assertTrue(expected.getOffset()==0);
		assertTrue(expected.getSize()==3);
		EntitiesHelper.assertUsers(expected.getDatas(), actual);
	}
	
	
	@After
	public void tearDown() throws FileNotFoundException, DatabaseUnitException, SQLException {
		this.resumeTable();
		
		SessionHolder holder = (SessionHolder) TransactionSynchronizationManager.getResource(sessionFactory);
		Session s = holder.getSession(); 
		s.flush();
		TransactionSynchronizationManager.unbindResource(sessionFactory);
	
	}
	
	
	
}
