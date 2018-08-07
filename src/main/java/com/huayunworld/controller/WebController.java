package com.huayunworld.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huayunworld.pojo.User;

@Controller
@RequestMapping("web")
public class WebController {
	@RequestMapping("/test")
	public String pathTest(ModelMap model) {
		model.addAttribute("message", "你好");
		return "index";
	}

	@RequestMapping("test1")
	public String get(HttpServletRequest request, HttpServletResponse response) {
		return "upload";

	}
	
	@RequestMapping("/toServer.do")
	@ResponseBody
	public Map<String, String> sendString(User user) { // user是与页面参数对应的JavaBean
		// map集合用来存放返回值
		Map<String, String> map = new HashMap<String, String>();
		if (user != null) {
			map.put("result", "提交成功");
		} else {
			map.put("result", "提交失败");
		}
		return map;
	}
}
