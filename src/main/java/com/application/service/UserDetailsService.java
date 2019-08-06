package com.application.service;

import com.application.request.response.bean.GenericResponseBean;

public interface UserDetailsService {

	public GenericResponseBean getUserDetails(String param);
}
