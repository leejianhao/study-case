package org.leejianhao.cms.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import junit.framework.Assert;
import static junit.framework.Assert.*;
import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.internal.runners.statements.Fail;
import org.junit.runner.RunWith;
import org.leejianhao.basic.test.util.AbstractDbUnitTestCase;
import org.leejianhao.cms.model.Channel;
import org.springframework.orm.hibernate4.SessionHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.support.TransactionSynchronizationManager;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/beans.xml")
public class TestChannelDao extends AbstractDbUnitTestCase{
	
	@Inject
	private IChannelDao channelDao;
	
	@Inject
	private SessionFactory sessionFactory;
	
	@Before
	public void setUp() throws SQLException, IOException, DatabaseUnitException {
		Session s = sessionFactory.openSession();
		TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(s));
		this.backupAllTable();	
		IDataSet ds = createDateSet("topic");
		//使用ReplacementDataSet可以有效的替换的null值
		/*ReplacementDataSet rds = new ReplacementDataSet(ds);
		rds.addReplacementObject("null", null);
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon,rds);*/
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon,ds);
	}
	
	@Test
	public void testListByParent() {
		List<Channel> cs = channelDao.listByParent(1);
		Assert.assertNotNull(cs);
		Assert.assertEquals(cs.size(), 4);
		fail("未完成的测试");
	}
	
	@Test
	public void testGenerateTree() {
		System.out.println(channelDao.generateTree());
		fail("没有完成的测试");
	}
	
	@Test
	public void testGenerateTreeByParent() {
		System.out.println(channelDao.generateTreeByParent(null));
		fail("没有完成的测试");
	}
	
	@Test
	public void testGetMaxOrderByParent() {
		Integer max = channelDao.getMaxOrderByParent(1);
		Assert.assertEquals(max, new Integer(4));
		max = channelDao.getMaxOrderByParent(2);
		Assert.assertEquals(max, new Integer(0));
		
		
	}
	
	@After
	public void tearDown() throws DatabaseUnitException, SQLException, IOException {
	//	this.resumeTable();
		
		SessionHolder holder = (SessionHolder) TransactionSynchronizationManager.getResource(sessionFactory);
		Session s = holder.getSession(); 
		s.flush();
		TransactionSynchronizationManager.unbindResource(sessionFactory);
	
	}
}
