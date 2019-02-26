package com.application.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.application.bean.User;


@RestController
@RequestMapping("/public")
public class PublicUserController {

	@PostMapping("/users/register")
	public ResponseEntity<Object> register(@RequestBody User document){
		return null;
	}
	
	@GetMapping("/users/login")
	public ResponseEntity<Object> login(@RequestParam String userName,@RequestParam String password){

		return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
	}

	@GetMapping("/users/getUser")
	public ResponseEntity<Object> getUser(@RequestParam String token){
		return new ResponseEntity<>(null,HttpStatus.OK);
	}

	
	  @GetMapping("/exist")
	  public boolean fieldExist(@RequestParam("key") String key, @RequestParam("value") String value) {

		return false;
	  }
}
