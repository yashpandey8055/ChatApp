package com.application.controller;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.application.config.UUIDAuthenticationService;
import com.application.service.dao.UsersDao;
import com.application.service.dao.documents.UserDocument;
import com.application.utils.Utils;

@RestController
@RequestMapping("/public")
public class PublicUserController {

	@Autowired 
	UUIDAuthenticationService userService;
	
	@Autowired
	UsersDao userDao;
	
	@PostMapping("/users/register")
	public ResponseEntity<Object> register(@RequestBody UserDocument document){
		document.setAge(Calendar.getInstance().get(Calendar.YEAR)-document.getYearOfBirth());
		document.setProfileUrl("https://s3.ap-south-1.amazonaws.com/ketu-user-profile-pictures/lonely-"+document.getGender().toLowerCase()+".jpg");
		String pass = document.getPassword();
		document.setPassword(BCrypt.hashpw(document.getPassword(), BCrypt.gensalt()));
		userDao.save(document);
		return login(document.getUsername(),pass);
	}
	
	@GetMapping("/users/login")
	public ResponseEntity<Object> login(@RequestParam String userName,@RequestParam String password){
		UserDocument userDocument = userDao.find(userName);
		if(userDocument != null&&BCrypt.checkpw(password, userDocument.getPassword())) {
			return new ResponseEntity<>(userService.login(userName),HttpStatus.OK);
		}
		return new ResponseEntity<>("Not found",HttpStatus.NOT_FOUND);
	}

	@GetMapping("/users/getUser")
	public ResponseEntity<UserDocument> getUser(@RequestParam String token){
		UserDocument document = userService.findByToken(token);
		return new ResponseEntity<>(document,HttpStatus.OK);
	}
	
	@PostMapping("/users/pushUsers")
	public ResponseEntity<String> pushUsers(@RequestBody List<UserDocument> documents){
		userDao.pushList(documents);
		return new ResponseEntity<>("Hello",HttpStatus.ACCEPTED);
	}
	
	  @GetMapping("/exist")
	  public boolean fieldExist(@RequestParam("key") String key, @RequestParam("value") String value) {
		  List<UserDocument> userDocument;
		  if(Utils.isNumeric(value)) {
			  userDocument =  userDao.findByQuery(Query.query(Criteria.where(key).is(Long.parseLong(value))));
		  }else {
			 userDocument =  userDao.findByQuery(Query.query(Criteria.where(key).is(value))); 
		  }
		 
		  if(userDocument!=null&&!userDocument.isEmpty()) {
			  return true;
		  }
		return false;
	  }
}
