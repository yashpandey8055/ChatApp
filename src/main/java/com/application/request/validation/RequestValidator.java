package com.application.request.validation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;


@Aspect
public class RequestValidator {
	
	@Pointcut("execution(com.application.rest.controller.PublicUserController.register)")
	public void register() {
		/**
		 * Point cut for  register validator
		 */
	}
	
	@Before("register()")
	public void logRequestAdvice(JoinPoint joinPoint) {
		/**
		 * Empty for now 
		 */
	}
}
