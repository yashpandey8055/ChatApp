package com.application.request.validation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Aspect
public class RequestValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(RequestValidator.class);
	@Pointcut("execution(com.application.rest.controller.PublicUserController.register)")
	public void register() {
		/**
		 * Point cut for  register validator
		 */
	}
	
	@Before("register()")
	public void logRequestAdvice(JoinPoint joinPoint) {
		LOGGER.info("Invoking  method : {} with request {}",joinPoint.getArgs());
	}
}
