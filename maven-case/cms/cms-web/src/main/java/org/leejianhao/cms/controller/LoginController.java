package org.leejianhao.cms.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.leejianhao.basic.util.Captcha;
import org.leejianhao.basic.util.JsonUtil;
import org.leejianhao.cms.dto.AjaxObj;
import org.leejianhao.cms.model.Role;
import org.leejianhao.cms.model.RoleType;
import org.leejianhao.cms.model.User;
import org.leejianhao.cms.service.IUserService;
import org.leejianhao.cms.web.CmsSessionContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {
	
	@Inject
	private IUserService userService;
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login() {
		return "admin/login";
	}
	
	private boolean isPublish(List<Role> rs) {
		for(Role r:rs) {
			if(r.getRoleType() == RoleType.ROLE_PUBLISH ) return true;
		}
		return false;
	}
	
	private boolean isAudit(List<Role> rs) {
		for(Role r:rs) {
			if(r.getRoleType() == RoleType.ROLE_AUDIT ) return true;
		}
		return false;
	}
	
	private boolean isAdmin(List<Role> rs) {
		for(Role r:rs) {
			if(r.getRoleType() == RoleType.ROLE_ADMIN ) return true;
		}
		return false;
	}
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(String username, String password, String checkcode, Model model, HttpSession session) {
		String cc = (String) session.getAttribute("cc");
		if(!cc.equals(checkcode)) {
			model.addAttribute("error", "验证码出错，请重新输入");
			return "admin/login";
		}
		User loginUser = userService.login(username, password);
		session.setAttribute("loginUser", loginUser);
		
		List<Role> rs = userService.listUserRoles(loginUser.getId());
		boolean isAdmin  = isAdmin(rs);
		session.setAttribute("isAdmin",isAdmin );
		if(!isAdmin) {
			session.setAttribute("isAudit", isAudit(rs));
			session.setAttribute("allActions", getAllActions(rs, session));
			session.setAttribute("isPublish", isPublish(rs));
		}
	
		session.removeAttribute("cc");
		CmsSessionContext.addSession(session);
		System.out.println("存储了Session:"+session.getId()); 
		
		return "redirect:/admin";
	}
	private Set<String> getAllActions(List<Role> rs, HttpSession session) {
		Set<String> actions = new HashSet<String>();
		Map<String,Set<String>> allAuths = (Map<String, Set<String>>) session.getServletContext().getAttribute("allAuths");
		actions.addAll(allAuths.get("base"));
		for(Role r:rs) {
			if(r.getRoleType() == RoleType.ROLE_ADMIN) continue;
			actions.addAll(allAuths.get(r.getRoleType().name()));
		}
		return actions;
	}
	@RequestMapping("/drawCheckCode")
	public void drawCheckCode(HttpServletResponse resp, HttpSession session) throws IOException {
		resp.setContentType("image/jpg");
		int width = 200;
		int height = 30; 	
		Captcha c = Captcha.getInstance();
		c.set(width, height);
		String checkcode = c.generateCheckcode();
		session.setAttribute("cc", checkcode);
		OutputStream os = resp.getOutputStream();
		ImageIO.write(c.generateCheckImg(checkcode), "jpg", os);
		
	}
}
