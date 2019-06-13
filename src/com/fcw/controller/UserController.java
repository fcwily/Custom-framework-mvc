package com.fcw.controller;

import com.fcw.mvc.annotation.Controller;
import com.fcw.mvc.annotation.RequestMapping;

@Controller
public class UserController {
	@RequestMapping("select")
	public String select() {
		System.out.println("执行查询");
		return "redirect:seletct.html";
	}

	@RequestMapping("insert")
	public String insert() {
		System.out.println("执行新增");
		return "insert.html";
	}
}
