package com.application.service;

import java.util.List;

import com.application.bean.MessageBean;

public interface MessagingService {

	public void save(MessageBean messageBean);
	
	public List<MessageBean> view(MessageBean messageBean);
	
	public boolean update(MessageBean messageBean);
	
	public boolean delete(MessageBean messageBean);
}
