package com.application.service.impl;

import java.util.Date;

import com.application.data.dao.documents.PostDocument;
import com.application.request.response.bean.GenericResponseBean;
import com.application.request.response.bean.PostActivityReqResBean;
import com.application.service.PostService;

public class StatusPostServiceImpl implements PostService{
	
	private static final String STATUS = "Status";

	@Override
	public GenericResponseBean post(PostActivityReqResBean postActivityReqResBean) {
		PostDocument document = new PostDocument();
		document.setCommentCount(0);
		document.setCreationDate(new Date());
		document.setUpdationDate(new Date());
		document.setIsStatus(false);
		document.setLikes(0);
		document.setPostImageUrl(null);
		document.setStatus(postActivityReqResBean.getStatus());
		document.setType(STATUS);
		document.setUserName(postActivityReqResBean.getUserName());
		return null;
	}

	@Override
	public GenericResponseBean edit(PostActivityReqResBean postActivityReqResBean) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GenericResponseBean delete(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GenericResponseBean view(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
