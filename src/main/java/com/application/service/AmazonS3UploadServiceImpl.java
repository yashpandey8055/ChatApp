package com.application.service;

import java.io.InputStream;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Component("s3Upload")
public class AmazonS3UploadServiceImpl implements UploadService{
	private static final Logger log = LoggerFactory.getLogger(AmazonS3UploadServiceImpl.class);
	private static final String ACCESS_KEY_ID = "AKIAIB5NEGKRHZBMXLPA";
	private static final String ACCESS_KEY_SECRET = "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";
	private static final String ACCESS_REGION = "ap-south-1";
	public static final String IMAGE_UPLOAD_BUCKET_NAME = "ketu-user-profile-pictures";
	
	@Override
	public String upload(InputStream in,String fileName,String bucketname) {
		try {
			
			AWSCredentials credentials = new BasicAWSCredentials(ACCESS_KEY_ID ,ACCESS_KEY_SECRET);
			AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(ACCESS_REGION)
	                .withCredentials(new AWSStaticCredentialsProvider(credentials))
	                .build();

			s3Client.putObject(new PutObjectRequest(Optional.ofNullable(bucketname).orElse(IMAGE_UPLOAD_BUCKET_NAME),fileName, in, null ));	
			return "https://s3.ap-south-1.amazonaws.com/"+bucketname+"/"+fileName;
			}catch(Exception e) {
				log.error("Unable to upload file "+fileName+" into the bucket "+bucketname,e.getMessage());
		}
		return null;
	}
}
