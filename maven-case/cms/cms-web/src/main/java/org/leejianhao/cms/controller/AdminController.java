package org.leejianhao.cms.controller;

import javax.servlet.http.HttpSession;

import org.leejianhao.cms.web.CmsSessionContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.leejianhao.cms.auth.AuthClass;
import com.leejianhao.cms.auth.AuthMethod;

@Controller
@AuthClass("login")
public class AdminController {
	
	@RequestMapping("/admin")
	@AuthMethod
	public String index() {
		return "admin/index";
	}
	
	@AuthMethod
	@RequestMapping("/admin/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		CmsSessionContext.removeSession(session);
		System.out.println("删除了Session:"+session.getId());
		return "redirect:/login";
	}
}
