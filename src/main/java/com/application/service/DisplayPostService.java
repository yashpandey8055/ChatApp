package com.application.service;

import com.application.bean.ViewPostBean;
import com.application.request.response.bean.GenericResponseBean;

@FunctionalInterface
public interface DisplayPostService  {

	public GenericResponseBean viewPost(ViewPostBean viewPostBean);
}
