package com.application.request.validation;

import java.util.Calendar;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.application.request.response.bean.UserRegisterReqResBean;


@Aspect
public class RequestValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(RequestValidator.class);
	
	private static final int MINIMUM_USERNAME_LENGTH = 5;
	@Pointcut("execution(com.application.rest.controller.PublicUserController.register)")
	public void register() {
		/**
		 * Point cut for  register validator
		 */
	}
	
	@Before("register()")
	public void logRequestAdvice(JoinPoint joinPoint) {
		Object[] args = joinPoint.getArgs();
		boolean validated = false;
		Calendar cal = Calendar.getInstance();
		for(Object arg: args) {
			if(arg instanceof UserRegisterReqResBean) {
				validated = ((UserRegisterReqResBean) arg).getUserName().length()>MINIMUM_USERNAME_LENGTH;
				validated = ((UserRegisterReqResBean) arg).getPassword().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[\\\\d]).{9,}$");
				validated = String.valueOf(((UserRegisterReqResBean) arg).getPhoneNumber()).length()==10;
				validated = ((UserRegisterReqResBean) arg).getYearOfBirth()>1900
						&&((UserRegisterReqResBean) arg).getYearOfBirth()<=cal.get(Calendar.YEAR);
			}
		}
	}
}
