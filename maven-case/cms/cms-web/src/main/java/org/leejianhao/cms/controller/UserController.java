package org.leejianhao.cms.controller;

import javax.inject.Inject;
import javax.validation.Valid;

import org.leejianhao.cms.dto.UserDto;
import org.leejianhao.cms.model.User;
import org.leejianhao.cms.service.IGroupService;
import org.leejianhao.cms.service.IRoleService;
import org.leejianhao.cms.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin/user")
public class UserController {
	@Inject
	private IUserService userService;
	@Inject
	private IGroupService groupService;
	@Inject
	private IRoleService roleService;
	
	@RequestMapping("/users")
	public String list(Model model) {
		model.addAttribute("datas", userService.findUser());
		return "user/list";
	}
	
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String add(Model model) {
		model.addAttribute("userDto", new UserDto());//user, user
		model.addAttribute("roles", roleService.listRole());
		model.addAttribute("groups", groupService.listGroup());
		return "user/add";
	}
	
	public void initAddUser(Model model) {
		model.addAttribute("roles", roleService.listRole());
		model.addAttribute("groups", groupService.listGroup());
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String add(@Valid UserDto userDto,BindingResult br, Model model) {
		if(br.hasErrors()) {
			initAddUser(model);
			return "user/add";
		}
	
		userService.add(userDto.getUser(),userDto.getRoleIds(),userDto.getGroupIds());
		
		return "redirect:/admin/user/users";
		
	}
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.GET)
	public String delete(@PathVariable int id) {
		userService.delete(id);
		return "redirect:/admin/user/users";
	}
	
	@RequestMapping(value="/updateStatus/{id}", method=RequestMethod.GET)
	public String updateStatus(@PathVariable int id) {
		userService.updateStatus(id);
		return "redirect:/admin/user/users";
	}
	
	@RequestMapping(value="/update/{id}", method=RequestMethod.GET)
	public String update(@PathVariable int id,Model model) {
		User u = userService.load(id);
		model.addAttribute("userDto",new UserDto(u,userService.listUserRoleIds(id),userService.listUserGroupIds(id)));
		initAddUser(model);
		return "user/update";
	}
	
	@RequestMapping(value="/update/{id}",method=RequestMethod.POST)
	public String update(@PathVariable int id,@Valid UserDto userDto,BindingResult br, Model model) {
		if(br.hasErrors()) {
			initAddUser(model);
			return "user/update";
		}
		User ou = userService.load(id);
		ou.setNickname(userDto.getUsername());
		ou.setPhone(userDto.getPhone());
		ou.setEmail(userDto.getEmail());
		ou.setStatus(userDto.getStatus());
		
		userService.update(ou,userDto.getRoleIds(),userDto.getGroupIds());
		
		return "redirect:/admin/user/users";
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public String show(@PathVariable int id, Model model) {
		model.addAttribute(userService.load(id));
		model.addAttribute("gs", userService.listUserGroups(id));
		model.addAttribute("rs", userService.listUserRoles(id));
		return "user/show";
	}
	
	/*@ExceptionHandler(CmsException.class)
	private void handleException(HttpServletRequest req,CmsException ce) {
		
		
	}*/
	
	
}
