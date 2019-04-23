package com.application.view.mapper;


import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewMapper {
	
	@GetMapping({"/secure/login","/"})
	public String  loginPage(Map<String, Object> model){
		return "/ui/login.html";
	}

	@GetMapping("/chat")
	public String  chatPage(Map<String, Object> model){
		return "/ui/chat.html";
	}

	@GetMapping("/dashboard")
	public String  dashboardPage(Map<String, Object> model){
		return "/ui/dashboard.html";
	}
	
	@GetMapping("/user")
	public String  userPage(Map<String, Object> model){
		return "/ui/user.html";
	}
	
	@GetMapping("/post")
	public String  postViewPage(Map<String, Object> model){
		return "/ui/post.html";
	}
	
	@GetMapping("/secure/register")
	public String  registerPage(Map<String, Object> model){
		return "/ui/register.html";
	}
	
}
