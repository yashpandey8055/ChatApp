package com.application.service;

import java.io.InputStream;

@FunctionalInterface
public interface UploadService {

	public String upload(InputStream in,String fileName,String bucketname);
}
