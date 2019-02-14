package com.application.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;
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

import com.application.bean.PostResponse;
import com.application.constants.Constants;
import com.application.service.UploadService;
import com.application.service.dao.UsersDao;
import com.application.service.dao.documents.PostDocument;
import com.application.service.dao.documents.UserDocument;

@RestController
@RequestMapping("/upload")
public class UploadServiceController {

	private static final Logger log = LoggerFactory.getLogger(UploadServiceController.class);

	@Autowired
	@Qualifier("localSystem")
	UploadService s3UploadService;
	
	@Autowired
	PostController postController;
	
	@Autowired
	UsersDao userDao;
	
	private static final Random RANDOM = new Random();
	
	@PostMapping(value="/uploadImage",consumes = MediaType.MULTIPART_FORM_DATA_VALUE ,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PostResponse> cropAndUploadImage(@AuthenticationPrincipal UserDocument user,@RequestParam("file") MultipartFile multi,
			@RequestParam("startX") Integer startX,@RequestParam("startY") Integer startY,
			@RequestParam("cropHeight") Integer cropHeight,@RequestParam("cropWidth") Integer cropWidth,@RequestParam("status") String status){
		try {
			ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
			BufferedImage originalImage = ImageIO.read(multi.getInputStream());
			BufferedImage dst =originalImage.getSubimage(startX, 
					startY, cropWidth, 
					cropHeight);	

			ImageIO.write(dst, "png", byteStream);
			PostDocument document = new PostDocument();
			document.setUserName(user.getUsername());
			document.setIsStatus(false);
			document.setType(Constants.IMAGE);
			document.setStatus(status);
			document.setLikes(0);
			document.setCommentCount(0);
			document.setPostImageUrl(s3UploadService.upload(new ByteArrayInputStream(byteStream.toByteArray()), randomStringGenerate(Constants.PNG), null));
			return postController.insertPost(user, document);
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		return null;
	}
	
	@PostMapping(value="/changeprofilepicture",consumes = MediaType.MULTIPART_FORM_DATA_VALUE ,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> changeProfilePicture(@AuthenticationPrincipal UserDocument user,@RequestParam("file") MultipartFile multi,
			@RequestParam("startX") Integer startX,@RequestParam("startY") Integer startY,
			@RequestParam("cropHeight") Integer cropHeight,@RequestParam("cropWidth") Integer cropWidth){
		try {
			ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
			BufferedImage originalImage = ImageIO.read(multi.getInputStream());
			BufferedImage dst =originalImage.getSubimage(startX, 
					startY, cropWidth, 
					cropHeight);	

			ImageIO.write(dst, "png", byteStream);
			String path = s3UploadService.upload(new ByteArrayInputStream(byteStream.toByteArray()), randomStringGenerate(Constants.PNG), null);
			user.setProfileUrl(path);
			userDao.save(user);
			return new ResponseEntity<>(path,HttpStatus.OK);
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		return null;
	}
	
	@PostMapping(value="/uploadVideo",consumes = MediaType.MULTIPART_FORM_DATA_VALUE ,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PostResponse> uploadVideo(@AuthenticationPrincipal UserDocument user,@RequestParam("file") MultipartFile multi,
			@RequestParam("status") String status){
		try {
			PostDocument document = new PostDocument();
			document.setUserName(user.getUsername());
			document.setIsStatus(false);
			document.setType(Constants.VIDEO);
			document.setStatus(status);
			document.setLikes(0);
			document.setCommentCount(0);
			document.setPostImageUrl(s3UploadService.upload(multi.getInputStream(),randomStringGenerate(Constants.MP4),null));
			return postController.insertPost(user, document);
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		return null;
	}
	private static String randomStringGenerate(String ext) {
		String characterArray= "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnaopqrst1234567890";
		StringBuilder stringBuilder = new StringBuilder();
		for(int i =0;i<10;i++) {
			stringBuilder.append(characterArray.charAt(RANDOM.nextInt(characterArray.length())));
		}
		stringBuilder.append("."+ext);
		return stringBuilder.toString();
	}
	
}
