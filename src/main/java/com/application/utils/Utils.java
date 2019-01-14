package com.application.utils;

import java.util.Calendar;
import java.util.Date;

public class Utils {

	private Utils() {
		
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
