package org.leejianhao.cms.controller;

import javax.inject.Inject;

import org.leejianhao.basic.util.EnumUtils;
import org.leejianhao.cms.model.Role;
import org.leejianhao.cms.model.RoleType;
import org.leejianhao.cms.service.IRoleService;
import org.leejianhao.cms.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.leejianhao.cms.auth.AuthClass;

@RequestMapping("/admin/role")
@Controller
@AuthClass
public class RoleController {
	
	@Inject
	private IRoleService roleService;
	@Inject
	private IUserService userService;
	
	@RequestMapping("/roles")
	public String list(Model model) {
		model.addAttribute("roles", roleService.listRole());
		return "role/list";
	}
	
	@RequestMapping("/{id}")
	public String show(@PathVariable int id, Model model) {
		model.addAttribute(roleService.load(id));
		model.addAttribute("us",userService.listRoleUsers(id));
		return "role/show";
	}
	
	@RequestMapping("/delete/{id}")
	public String delete(@PathVariable int id) {
		roleService.delete(id);
		return "redirect:/admin/role/roles";
	}
	
	@RequestMapping("/clearUsers/{id}")
	public String clearUsers(@PathVariable int id) {
		roleService.deleteRoleUsers(id);
		return "redirect:/admin/role/roles";
	}
	
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String add(Model model) {
		model.addAttribute(new Role());
		model.addAttribute("types",EnumUtils.enum2Name(RoleType.class));
		return "role/add";
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String add(Role role) {
		roleService.add(role);
		return "redirect:/admin/role/roles";
	}
	
	@RequestMapping(value="/update/{id}",method=RequestMethod.GET)
	public String update(@PathVariable int id, Model model) {
		model.addAttribute(roleService.load(id));
		return "role/update";
	}
	
	@RequestMapping(value="/update/{id}",method=RequestMethod.POST)
	public String update(@PathVariable int id,Role role) {
		
		Role or = roleService.load(id);
		or.setName(role.getName());
		or.setRoleType(role.getRoleType());
		roleService.update(or);
		return "redirect:/admin/role/roles";
	}
	
}
