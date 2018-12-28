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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.application.service.AmazonS3UploadServiceImpl;
import com.application.service.UploadService;

@RestController
@RequestMapping("/public/upload")
public class ImageService {
	private static final Logger log = LoggerFactory.getLogger(ImageService.class);
	private static final String ERROR_MESSAGE = "Error while uploading. Please try again later";
	private static final String MP4 = "mp4";
	private static final String PNG = "png";
	@Autowired
	@Qualifier("localSystem")
	UploadService s3UploadService;
	
	private static final Random RANDOM = new Random();
	
	@PostMapping(value="/uploadImage",consumes = MediaType.MULTIPART_FORM_DATA_VALUE ,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> cropAndUploadImage(@RequestParam("file") MultipartFile multi,
			@RequestParam("startX") Integer startX,@RequestParam("startY") Integer startY,
			@RequestParam("cropHeight") Integer cropHeight,@RequestParam("cropWidth") Integer cropWidth){
		try {
			ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
			BufferedImage originalImage = ImageIO.read(multi.getInputStream());
			BufferedImage dst =originalImage.getSubimage(startX, 
					startY, cropWidth, 
					cropHeight);	
			ImageIO.write(dst, "png", byteStream);
			return new ResponseEntity<>(s3UploadService.upload(new ByteArrayInputStream(byteStream.toByteArray()),
					randomStringGenerate(PNG), AmazonS3UploadServiceImpl.IMAGE_UPLOAD_BUCKET_NAME),HttpStatus.OK) ;
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		return new ResponseEntity<>(ERROR_MESSAGE,HttpStatus.EXPECTATION_FAILED) ;
	}
	
	@PostMapping(value="/uploadVideo",consumes = MediaType.MULTIPART_FORM_DATA_VALUE ,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> uploadVideo(@RequestParam("file") MultipartFile multi){
		try {
			return new ResponseEntity<>(s3UploadService.upload(multi.getInputStream(),randomStringGenerate(MP4),null),HttpStatus.OK) ;
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		return new ResponseEntity<>(ERROR_MESSAGE,HttpStatus.EXPECTATION_FAILED) ;
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
