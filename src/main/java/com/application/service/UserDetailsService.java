package com.application.service;

import com.application.request.response.bean.GenericResponseBean;

@FunctionalInterface
public interface UserDetailsService {

	public GenericResponseBean getUserDetails(String param);
}
