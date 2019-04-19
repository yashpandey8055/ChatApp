package com.application.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtils {
	private static final Logger LOG = LoggerFactory.getLogger(DateUtils.class);
	
	private DateUtils(){
		/**
		 * No constructor for Utility Class
		 */
	}

	public static Date convertToDate(Integer date,String month,Integer year) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");
		try {
			return sdf.parse(date+"/"+month+"/"+year);
		} catch (ParseException e) {
			LOG.error("Wrong date format/Invalid date. Date should be in dd/MMM/yyyy format.");
		}
		
		return null;
	}
}
