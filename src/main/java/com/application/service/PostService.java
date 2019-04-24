package com.application.service;

import com.application.request.response.bean.GenericResponseBean;
import com.application.request.response.bean.PostActivityReqResBean;

public interface PostService {
	
	public GenericResponseBean post(PostActivityReqResBean postActivityReqResBean);
	
	public GenericResponseBean edit(PostActivityReqResBean postActivityReqResBean);
	
	public GenericResponseBean delete(String id);
	
	public GenericResponseBean view(String id);
}
