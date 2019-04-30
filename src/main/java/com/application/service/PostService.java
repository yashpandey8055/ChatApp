package com.application.service;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;

import com.application.bean.PostResponse;
import com.application.data.dao.documents.PostDocument;
import com.application.data.dao.documents.UserDocument;
import com.application.request.response.bean.GenericResponseBean;
import com.application.request.response.bean.PostActivityReqResBean;
import com.application.request.response.constants.DataAccessObjectConstants;
import com.application.request.response.constants.GeneralConstants;
import com.application.request.response.constants.RequestResponseConstant;

public interface PostService {
	
	public GenericResponseBean post(PostActivityReqResBean postActivityReqResBean);
	
	public GenericResponseBean edit(PostActivityReqResBean postActivityReqResBean);
	
	public GenericResponseBean delete(String id);
	
	public GenericResponseBean view(String id,String user);
	
	public static GenericResponseBean createPostResponse(PostDocument postDocument,UserDocument userDocument) {
		GenericResponseBean responseBean = new GenericResponseBean();
		PostResponse postResponse = new PostResponse();
		postResponse.setUser(userDocument);
		postResponse.setPost(postDocument);
		postResponse.setComments(new ArrayList<>(1));
		postResponse.setLikesCount(0);
		postResponse.setLikedByUser(false);
		postResponse.setDaysAgo(GeneralConstants.NOW_INSTANT);
		responseBean.setCode(HttpStatus.OK);
		responseBean.setType(RequestResponseConstant.SUCCESS_RESPONSE);
		responseBean.setData(postResponse);
		
		return responseBean;
	}
}
