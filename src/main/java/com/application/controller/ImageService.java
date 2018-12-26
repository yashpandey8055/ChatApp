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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.application.service.AmazonS3UploadService;

@RestController
@RequestMapping("/public")
public class ImageService {
	private static final Logger log = LoggerFactory.getLogger(ImageService.class);
	
	@Autowired
	AmazonS3UploadService s3UploadService;
	
	private static final Random RANDOM = new Random();
	
	@PostMapping(value="/uploadProfilePic",consumes = MediaType.MULTIPART_FORM_DATA_VALUE ,produces=MediaType.APPLICATION_JSON_VALUE)
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
			s3UploadService.uploadToAmazonS3Bucket(new ByteArrayInputStream(byteStream.toByteArray()),randomStringGenerate(), AmazonS3UploadService.IMAGE_UPLOAD_BUCKET_NAME);
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		return null;
	}
	
	private static String randomStringGenerate() {
		String characterArray= "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnaopqrst1234567890";
		StringBuilder stringBuilder = new StringBuilder();
		for(int i =0;i<10;i++) {
			stringBuilder.append(characterArray.charAt(RANDOM.nextInt(characterArray.length())));
		}
		return stringBuilder.toString();
	}
	
}
