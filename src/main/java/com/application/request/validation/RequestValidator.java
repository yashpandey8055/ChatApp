package com.application.request.validation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.application.custom.exception.IllegalFieldException;
import com.application.request.response.bean.UserRegisterReqResBean;



@Aspect
@Component
public class RequestValidator {
	private static final Logger LOG = LoggerFactory.getLogger(RequestValidator.class);
	
	@Pointcut("execution()")
	public void register() {
		/**
		 * Point cut for  register validator
		 */
	}
	
	@Around("execution(* com.application.rest.controller.PublicUserController.register(..)) && args(request)")
	public  Object logRequestAdvice(ProceedingJoinPoint pjp,UserRegisterReqResBean request) throws Throwable {
		try {
			Validator.getNumericInstance(ValidatorType.AGE).validateField(request.getAge());
			Validator.getStringInstance(ValidatorType.PASSWORD).validateField(request.getPassword());
			Validator.getStringInstance(ValidatorType.USERNAME).validateField(request.getUserName());
			Validator.getStringInstance(ValidatorType.EMAIL).validateField(request.getEmail());
			
			Validator.stringFieldNotPresent(request.getGender(), "Gender");
			Validator.stringFieldNotPresent(request.getGender(), "Date of Birth");
			return pjp.proceed();
		}catch(IllegalFieldException ex) {
			LOG.error(ex.getMessage());
			return new ResponseEntity<>(ex.getMessage(),HttpStatus.OK);
		}
		
	}
	
}
