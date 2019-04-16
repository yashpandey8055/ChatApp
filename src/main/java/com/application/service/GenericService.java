package com.application.service;

import com.application.request.response.bean.GenericResponseBean;

@FunctionalInterface
public interface GenericService {

	public GenericResponseBean service();
}
