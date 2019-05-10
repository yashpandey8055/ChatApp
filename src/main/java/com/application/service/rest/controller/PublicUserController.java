package com.application.service.rest.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.application.request.response.bean.GenericResponseBean;
import com.application.request.response.bean.UserRegisterReqResBean;
import com.application.service.loginregisterservice.impl.LoginServiceImpl;
import com.application.service.loginregisterservice.impl.RegistrationServiceImpl;
import com.application.utils.Utils;



@RestController
@RequestMapping("/secure")
public class PublicUserController {
	
	@Autowired
	RegistrationServiceImpl registerService;
	
	@Autowired
	LoginServiceImpl loginServiceImpl;
	
	@Autowired 
	MongoTemplate template;

	@PostMapping("/users/register")
	public ResponseEntity<GenericResponseBean> register(@RequestBody UserRegisterReqResBean request){
		GenericResponseBean response = registerService.service(request);
		return new ResponseEntity<>(response,response.getCode());

	}
	
	@GetMapping("/users/login")
	public ResponseEntity<Object> login(@RequestParam String userName,@RequestParam String password){
		GenericResponseBean response = loginServiceImpl.doLogin(userName,password);
		return new ResponseEntity<>(response,response.getCode());
	}

	@GetMapping("/users/getUser")
	public ResponseEntity<Object> getUser(@RequestParam String token){
		return new ResponseEntity<>(null,HttpStatus.OK);
	}

	
	  @GetMapping("/exist")
	  public boolean fieldExist(@RequestParam("key") String key, @RequestParam("value") String value) {
		 return template.exists(Query.query(Criteria.where(key).is(Utils.getRealTypeValue(value))),"users");
	  }
}
