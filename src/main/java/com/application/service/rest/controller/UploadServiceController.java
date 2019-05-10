package com.application.service.rest.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.application.bean.User;
import com.application.request.response.bean.GenericResponseBean;
import com.application.request.response.bean.PostActivityReqResBean;
import com.application.request.response.constants.GeneralConstants;
import com.application.request.response.constants.RequestResponseConstant;
import com.application.service.ImageUploadService;
import com.application.service.PostService;
import com.application.utils.Utils;

@RestController
@RequestMapping("/upload")
public class UploadServiceController {
	
	private static final Logger LOG = LoggerFactory.getLogger(UploadServiceController.class);

	@Autowired
	@Qualifier("s3Upload")
	ImageUploadService imageUploadService;
	
	@Autowired
	@Qualifier("ImagePost")
	PostService postService;
	
	@PostMapping(value="/uploadImage",consumes = MediaType.MULTIPART_FORM_DATA_VALUE ,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GenericResponseBean> cropAndUploadImage(@AuthenticationPrincipal User user,@RequestParam("file") MultipartFile multi,
			@RequestParam("startX") Integer startX,@RequestParam("startY") Integer startY,
			@RequestParam("cropHeight") Integer cropHeight,@RequestParam("cropWidth") Integer cropWidth,@RequestParam("status") String status){
		try {
			ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
			BufferedImage originalImage = ImageIO.read(multi.getInputStream());
			BufferedImage dst =originalImage.getSubimage(startX, 
					startY, cropWidth, 
					cropHeight);	

			ImageIO.write(dst, "png", byteStream);
			PostActivityReqResBean postActivity = new PostActivityReqResBean();
			postActivity.setUserName(user.getUsername());
			postActivity.setStatus(status);
			postActivity.setPostImageUrl(imageUploadService.upload(new ByteArrayInputStream(byteStream.toByteArray()), Utils.randomStringGenerate(GeneralConstants.PNG_EXT), null));
			return new ResponseEntity<>(postService.post(postActivity), HttpStatus.OK);
		} catch (IOException e) {
			LOG.error(e.getMessage());
		}
		GenericResponseBean response = new GenericResponseBean();
		response.setCode(HttpStatus.INTERNAL_SERVER_ERROR);
		response.setType(RequestResponseConstant.FAILURE_RESPONSE);
		response.setData(RequestResponseConstant.FAILED_UPLOAD_IMAGE);
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
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
