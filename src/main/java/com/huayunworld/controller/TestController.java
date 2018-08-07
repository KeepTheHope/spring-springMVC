package com.huayunworld.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("test")
public class TestController {

	@RequestMapping("a")
	@ResponseBody
	public String test(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String str = "aaaa";
		return str;

		// System.err.println("test");
		// response.getWriter().print("aaa");

	}

	@RequestMapping(value = "/b", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String test2(HttpServletRequest request, HttpServletResponse response, String name) throws Exception {
		String jsona = receivePost(request);
		// String str = request.getParameter("name");
		System.err.println(jsona);
		return jsona;
	}

	@RequestMapping(value = "/c", consumes = "application/xml", produces = "application/xml", method = RequestMethod.POST)
	public void test3(HttpServletRequest request, HttpServletResponse response) {
		String str = request.getParameter("id");
		System.err.println(str);
	}

	public String receivePost(HttpServletRequest request) throws Exception {
		// 读取请求内容
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
		String line = null;
		StringBuilder sb = new StringBuilder();
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		String reqBody = sb.toString();
		return reqBody;
	}

	@RequestMapping("demo")
	public void get(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String str = "{\"id\":\"2006211019\",\"name\":\"201807131531471034689006.wav\",\"type\":\"我没有时间，很忙\",\"updatetime\":\"2018-07-13 16:37:18\"}";
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().print(str);
	}

}
