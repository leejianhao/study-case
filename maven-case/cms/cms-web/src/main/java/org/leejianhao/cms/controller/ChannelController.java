package org.leejianhao.cms.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.jboss.logging.annotations.Param;
import org.leejianhao.cms.dto.AjaxObj;
import org.leejianhao.cms.dto.TreeDto;
import org.leejianhao.cms.model.Channel;
import org.leejianhao.cms.model.ChannelTree;
import org.leejianhao.cms.model.ChannelType;
import org.leejianhao.cms.service.IChannelService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.leejianhao.cms.auth.AuthClass;

@RequestMapping("/admin/channel")
@Controller
@AuthClass
public class ChannelController {
	
	@Inject
	private IChannelService channelService;
	
	@RequestMapping("/channels")
	public String list(Model model) {
	//	model.addAttribute("treeDatas", JsonUtil.getInstance().obj2json(channelService.generateTree()));
		return "channel/list";
	}
	
	@RequestMapping("/channels/{pid}")
	public String listChild(@PathVariable Integer pid, @Param Integer refresh, Model model) {
		Channel pc = null;
		if(refresh == null) {
			model.addAttribute("refresh", 0);
		}else {
			model.addAttribute("refresh", 1);
		}
		if(pid==null ||pid<=0) {
			pc = new Channel();
			pc.setName(Channel.ROOT_NAME);
			pc.setId(Channel.ROOT_ID);
		} else {
			pc = channelService.load(pid);
		}
		model.addAttribute("pc", pc);
		model.addAttribute("channels",channelService.listByParent(pid));
		return "channel/list_child";
	}
	
	@RequestMapping("/treeAll")
	public @ResponseBody List<ChannelTree> treeAll() {
		return channelService.generateTree();
	}
	
	@RequestMapping("/treeAs")
	public @ResponseBody List<TreeDto> tree(@Param Integer pid) {
		List<TreeDto> tds = new ArrayList<TreeDto>();
		if(pid ==null) {
			tds.add(new TreeDto(0,"网站根栏目",1));
			return tds;
		}
		List<ChannelTree> cts = channelService.generateTreeByParent(pid);
		for(ChannelTree ct:cts) {
			tds.add(new TreeDto(ct.getId(),ct.getName(),1));
		}
		return tds;
	}
	
	
	@RequestMapping(value="/add/{pid}", method=RequestMethod.GET)
	public String add(@PathVariable Integer pid, Model model) {
		initAdd(model,pid);
		model.addAttribute(new Channel());
		return "channel/add";
	}
	
	public void initAdd(Model model, Integer pid) {
		if(pid==null) pid=0;
		Channel pc = null;
		if(pid==0) {
			pc = new Channel();
			pc.setId(Channel.ROOT_ID);
			pc.setName(Channel.ROOT_NAME);
		}else {
			pc = channelService.load(pid);
		}
		model.addAttribute("pc", pc);
		model.addAttribute("types", org.leejianhao.basic.util.EnumUtils.enumProp2NameMap(ChannelType.class, "name"));
	}
	
	@RequestMapping(value="/add/{pid}", method=RequestMethod.POST)
	public String add(@PathVariable Integer pid, Channel channel, BindingResult br, Model model) {
		if(br.hasErrors()) {
			initAdd(model,pid);
			return "channel/add";
		}
		channelService.add(channel, pid);
		
		return "redirect:/admin/channel/channels/"+pid+"?refresh=1";
	}
	
	@RequestMapping("/delete/{pid}/{id}")
	public String delete(@PathVariable Integer pid, @PathVariable Integer id) {
		channelService.delete(id);
		return "redirect:/admin/channel/channels/"+pid+"?refresh=1";
	}
	 
	@RequestMapping(value="/update/{id}", method=RequestMethod.GET)
	public String update(@PathVariable Integer id, Model model) {
		Channel c = channelService.load(id);
		model.addAttribute("channel", c);
		Channel pc = null;
		if(c.getParent()==null) {
			pc = new Channel();
			pc.setId(Channel.ROOT_ID);
			pc.setName(Channel.ROOT_NAME);
		}else {
			pc = c.getParent();
		}
		model.addAttribute("pc", pc);
		model.addAttribute("types", org.leejianhao.basic.util.EnumUtils.enumProp2NameMap(ChannelType.class, "name"));
		return "channel/update";
	}
	
	@RequestMapping(value="/update/{id}", method=RequestMethod.POST)
	public String update(@PathVariable Integer id, Channel channel, BindingResult br, Model model) {
		if(br.hasErrors()) {
			model.addAttribute("types", org.leejianhao.basic.util.EnumUtils.enumProp2NameMap(ChannelType.class, "name"));
			return "channel/update";
		}
		
		Channel tc = channelService.load(id);
		int pid =0;
		if(tc.getParent()!=null) pid = tc.getParent().getId();
		tc.setCustomLink(channel.getCustomLink());
		tc.setCustomLinkUrl(channel.getCustomLinkUrl());
		tc.setIsIndex(channel.getIsIndex());
		tc.setIsTopNav(channel.getIsTopNav());
		tc.setName(channel.getName());
		tc.setRecommend(channel.getRecommend());
		tc.setStatus(channel.getStatus());
		tc.setType(channel.getType());
		channelService.update(tc);
		return "redirect:/admin/channel/channels/"+pid+"?refresh=1";
	}
	
	@RequestMapping("/channels/updateSort")
	public @ResponseBody AjaxObj updateSort(@Param Integer[] ids) {
		try {
			channelService.updateSort(ids);
		} catch (Exception e) {
			return new AjaxObj(0, e.getMessage());
		}
		return new AjaxObj(1);
	}
	
}
