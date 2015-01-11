package org.leejianhao.cms.web;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class CmsSessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent event) {
		
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		//关闭浏览器的时候需要
		CmsSessionContext.removeSession(event.getSession());
		System.out.println("删除了Session:"+event.getSession().getId());
	}

}
