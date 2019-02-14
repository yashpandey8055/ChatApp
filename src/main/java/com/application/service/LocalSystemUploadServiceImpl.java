package com.application.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("localSystem")
public class LocalSystemUploadServiceImpl implements UploadService{
	private static final Logger log = LoggerFactory.getLogger(LocalSystemUploadServiceImpl.class);
	private static final String FOLDER_NAME = "/src/main/resources/static/contents";
	
	@Override
	public String upload(InputStream in, String fileName, String bucketname) {
		URL url = this.getClass().getClassLoader().getResource("static");
		File file = new File(url.toString()+"/contents");
		file.mkdir();
		return upload(in,fileName,url);
	}
	
	public String upload(InputStream in, String fileName,URL folderName) {
		File file = new File(folderName.getPath(),fileName);
		try {
			file.createNewFile();
			OutputStream out = new FileOutputStream(file);
			in.transferTo(out);
			out.flush();
			out.close();
		} catch (IOException e) {
			log.error("Unable to save in localsystem. File "+fileName,e.getMessage());
		}
		return fileName;
	}

}
