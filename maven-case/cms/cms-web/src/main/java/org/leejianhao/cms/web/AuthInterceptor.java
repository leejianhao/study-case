package org.leejianhao.cms.web;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.leejianhao.cms.model.CmsException;
import org.leejianhao.cms.model.User;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AuthInterceptor extends HandlerInterceptorAdapter {
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession();
		
		/*
		 * 如果使用uplodify使用文件上传，由于flash的bug问题，会产生一个新的session,
		 * 所以必须再获取一个原有 的session,然后重置session
		 */
		String sid = request.getParameter("sid");
		if(sid!=null && !"".equals(sid.trim())) {
			 //当sid有值，则便是有uplodify
			session = CmsSessionContext.getSession(session);
		}
		
		HandlerMethod hm = (HandlerMethod) handler;
		User user = (User) session.getAttribute("loginUser");
		
		CmsSessionContext.getSession(session);
		if(user==null) {
			response.sendRedirect(request.getContextPath()+"/login");
		}else {
			boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
			if(!isAdmin) {
				//不是超级管理人员，就需要判断是否有权限访问某些功能
				Set<String> actions = (Set<String>) session.getAttribute("allActions");
				String aname = hm.getBean().getClass().getName()+"."+hm.getMethod().getName();
				if(!actions.contains(aname)) throw new CmsException("没有权限访问");
			}
		}
		
		return super.preHandle(request, response, handler);
	}
	
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		super.afterCompletion(request, response, handler, ex);
	}
}
