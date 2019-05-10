package com.application.service.imageuploadservice.impl;

import java.io.InputStream;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.application.request.response.constants.GeneralConstants;
import com.application.service.ImageUploadService;

@Service("s3Upload")
public class AmazonS3UploadServiceImpl  implements ImageUploadService{
	private static final Logger log = LoggerFactory.getLogger(AmazonS3UploadServiceImpl.class);
	@Override
	public String upload(InputStream in,String fileName,String bucketname) {
		try {
			
			AWSCredentials credentials = new BasicAWSCredentials(GeneralConstants.ACCESS_KEY_ID ,GeneralConstants.ACCESS_KEY_SECRET);
			AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(GeneralConstants.ACCESS_REGION)
	                .withCredentials(new AWSStaticCredentialsProvider(credentials))
	                .build();

			s3Client.putObject(new PutObjectRequest(Optional.ofNullable(bucketname).orElse(GeneralConstants.IMAGE_UPLOAD_BUCKET_NAME),fileName, in, null ));	
			return "https://s3.ap-south-1.amazonaws.com/"+GeneralConstants.IMAGE_UPLOAD_BUCKET_NAME+"/"+fileName;
			}catch(Exception e) {
				log.error("Unable to upload file : {}  into the bucket {}. Error is: {} ",fileName,GeneralConstants.IMAGE_UPLOAD_BUCKET_NAME,e.getMessage());
		}
		return null;
}
}
