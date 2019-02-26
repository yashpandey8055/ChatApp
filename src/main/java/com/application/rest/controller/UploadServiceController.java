package com.application.rest.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.application.bean.User;

@RestController
@RequestMapping("/upload")
public class UploadServiceController {

	
	@PostMapping(value="/uploadImage",consumes = MediaType.MULTIPART_FORM_DATA_VALUE ,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> cropAndUploadImage(@AuthenticationPrincipal User user,@RequestParam("file") MultipartFile multi,
			@RequestParam("startX") Integer startX,@RequestParam("startY") Integer startY,
			@RequestParam("cropHeight") Integer cropHeight,@RequestParam("cropWidth") Integer cropWidth,@RequestParam("status") String status){
		
		return null;
	}
	
	@PostMapping(value="/changeprofilepicture",consumes = MediaType.MULTIPART_FORM_DATA_VALUE ,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> changeProfilePicture(@AuthenticationPrincipal User user,@RequestParam("file") MultipartFile multi,
			@RequestParam("startX") Integer startX,@RequestParam("startY") Integer startY,
			@RequestParam("cropHeight") Integer cropHeight,@RequestParam("cropWidth") Integer cropWidth){
	
		return null;
	}
	
	@PostMapping(value="/uploadVideo",consumes = MediaType.MULTIPART_FORM_DATA_VALUE ,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> uploadVideo(@AuthenticationPrincipal User user,@RequestParam("file") MultipartFile multi,
			@RequestParam("status") String status){
		
		return null;
	}

}
