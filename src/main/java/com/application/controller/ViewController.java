package com.application.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ViewController {

	@GetMapping({"/login","/"})
	public String  loginPage(Map<String, Object> model){
		return "/views/login.html";
	}

	@GetMapping("/chat")
	public String  chatPage(Map<String, Object> model){
		return "/views/chat.html";
	}

	@GetMapping("/dashboard")
	public String  dashboardPage(Map<String, Object> model){
		return "/views/dashboard.html";
	}
	
	@GetMapping("/user")
	public String  userPage(Map<String, Object> model){
		return "/views/user.html";
	}
	
	@GetMapping("/post")
	public String  postViewPage(Map<String, Object> model){
		return "/views/post.html";
	}
}
