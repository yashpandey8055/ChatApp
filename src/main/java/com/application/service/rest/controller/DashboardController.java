package com.application.service.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.application.bean.User;
import com.application.bean.ViewPostBean;
import com.application.request.response.bean.GenericResponseBean;
import com.application.service.DisplayPostService;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {
	
	@Autowired
	@Qualifier("Dashboard")
	DisplayPostService dashboardService;
	
	@GetMapping("/getPosts")
	public ResponseEntity<GenericResponseBean> getPost(@AuthenticationPrincipal User currentUser){
		ViewPostBean viewPostBean = new ViewPostBean();
		viewPostBean.setActiveUser(currentUser);
		viewPostBean.setUsernameForPost(currentUser.getUsername());
		return new ResponseEntity<>(dashboardService.viewPost(viewPostBean),HttpStatus.OK);
		
		
	}
}
