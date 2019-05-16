package com.application.service.rest.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.application.bean.MessageBean;
import com.application.bean.User;
import com.application.data.dao.IMongoCollection;
import com.application.data.dao.documents.MessageDocument;
import com.application.data.dao.documents.MongoDocument;
import com.application.factory.MongoCollectionFactory;
import com.application.request.response.constants.DataAccessObjectConstants;
import com.application.service.MessagingService;
import com.google.common.collect.Lists;

@RestController
@RequestMapping("/message")
public class SimpleMessagingController {

	@Autowired
	MongoTemplate template;
	
	@GetMapping("/pastConversations")
	public ResponseEntity<Object> getPost(@AuthenticationPrincipal User currentUser){
		
		return null;
	}
}
