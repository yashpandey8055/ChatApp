package com.application.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

	public static Date convertToDate(Integer date,Integer month,Integer year) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try {
			return sdf.parse(date+"/"+month+"/"+year);
		} catch (ParseException e) {
			LOG.error("Wrong date format/Invalid date. Date should be in dd/MMM/yyyy format.");
		}
		
		return null;
	}
	
	public static String calculateTimeDifference(Date date) {
    	Calendar cal = Calendar.getInstance();
    	
    	long[] timeCount = {60,60,24,30,365};
    	String[] time = {"s","m","h","d","mon","y"};
    	
    	float seconds = (cal.getTimeInMillis() - date.getTime())/1000;
    	int count=0;
    	while(true) {
    		if((seconds=seconds/timeCount[count])<=1) {
    			break;
    		}else {
    			count++;
    		}
    	}
    	if(count>=2&&(seconds/24)>=1) {
    		seconds = seconds/24;
    		return (int)seconds+" "+time[count]+" ago";
    	}
    	return ((int)(seconds*timeCount[count]))+""+time[count]+" ago";
}
}
