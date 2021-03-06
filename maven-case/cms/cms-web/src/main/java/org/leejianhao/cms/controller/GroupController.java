package org.leejianhao.cms.controller;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.leejianhao.cms.model.ChannelTree;
import org.leejianhao.cms.model.Group;
import org.leejianhao.cms.service.IGroupService;
import org.leejianhao.cms.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.leejianhao.cms.auth.AuthClass;

@RequestMapping("/admin/group")
@Controller
@AuthClass
public class GroupController {
	
	@Inject
	private IGroupService groupService;
	@Inject
	private IUserService userService;
	
	@RequestMapping("/groups")
	public String list(Model model) {
		model.addAttribute("datas",groupService.findGroup());
		return "group/list";
	}
	
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String add(Model model) { 
		model.addAttribute(new Group());
		return "group/add";
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String add(@Validated Group group,BindingResult br) {
		if(br.hasErrors()) {
			return "group/add";
		}
		group.setCreateDate(new Date());
		groupService.add(group);
		return "redirect:/admin/group/groups";
	}
	
	@RequestMapping(value="/update/{id}",method=RequestMethod.GET)
	public String update(@PathVariable int id, Model model) { 
		model.addAttribute(groupService.load(id));
		return "group/update";
	}
	
	@RequestMapping(value="/update/{id}",method=RequestMethod.POST)
	public String update(@PathVariable int id,@Validated Group group,BindingResult br) {
		if(br.hasErrors()) {
			return "group/update";
		}
		Group og = groupService.load(id);
		og.setDescr(group.getDescr());
		og.setName(group.getName());
		groupService.update(og);
		return "redirect:/admin/group/groups";
	}
	
	@RequestMapping("/delete/{id}")
	public String delete(@PathVariable int id) {
		groupService.delete(id);
		return "redirect:/admin/group/groups";
	}
	
	@RequestMapping("/{id}")
	public String show(@PathVariable int id,Model model) {
		model.addAttribute(groupService.load(id));
		model.addAttribute("us", userService.listGroupUsers(id));
		return "group/show";
	}
	
	@RequestMapping("/clearUsers/{id}")
	public String clearGroupUsers(@PathVariable int id) {
		groupService.deleteGroupUsers(id);
		return "redirect:/admin/group/groups";
	}
	
	@RequestMapping("/listChannels/{gid}")
	public String listChannels(@PathVariable int gid, Model model) {
		model.addAttribute(groupService.load(gid));
		return "/group/listChannel";
	}
	
	@RequestMapping("/groupTree/{gid}")
	public @ResponseBody List<ChannelTree> groupTree(@PathVariable Integer gid) {
		return groupService.generateGroupChannelTree(gid);
	}
	
	@RequestMapping("/setChannels/{gid}")
	public String setChannels(@PathVariable int gid, Model model) {
		model.addAttribute(groupService.load(gid));
		model.addAttribute("cids", groupService.listGroupChannelIds(gid));
		return "/group/setChannel";
	}
}
