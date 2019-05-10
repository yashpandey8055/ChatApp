package com.application.request.response.constants;

public class GeneralConstants {

	private GeneralConstants(){
		/**
		 * No need of Constructor for Constant file
		 */
	}
	
	public static final String NOW_INSTANT = "Just now";
	
	public static final int COMMENT_LIMIT_ON_VIEW_POST = 2;
	
	public static final int PERIOD_OF_DAYS_FOR_POST = -3;
	
	public static final String STATUS_POST = "Status";
	
	public static final String IMAGE_POST = "Image";
	
	public static final String LIKED_MSG = "Liked";
	
	
	/**
	 * Amazon S3 upload constants 
	 */
	public static final String ACCESS_KEY_ID = "AKIAIB5NEGKRHZBMXLPA";
	
	public static final String ACCESS_KEY_SECRET = "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";
	
	public static final String ACCESS_REGION = "ap-south-1";
	
	public static final String IMAGE_UPLOAD_BUCKET_NAME = "ketu-user-profile-pictures";

	/**
	 * Extensions
	 */
	public static final String PNG_EXT = "png";
	
	/**
	 * Like Unlike Constants 
	 */
	
	public static final String COMMENT_TYPE = "commentType";
	
	public static final String POST_TYPE = "postType";
}
