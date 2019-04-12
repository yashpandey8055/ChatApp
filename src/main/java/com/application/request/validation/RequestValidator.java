package com.application.request.validation;

import java.util.Calendar;

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
import com.application.request.response.bean.GenericResponseBean;
import com.application.request.response.bean.UserRegisterReqResBean;
import com.application.request.response.constants.RequestResponseConstant;



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
			FieldValidationFactory.getIntegerInstance(ValidatorType.AGE).validateField(request.getBirthYear());
			request.setAge(Calendar.getInstance().get(Calendar.YEAR) - request.getBirthYear());
			
			FieldValidationFactory.getLongInstance(ValidatorType.PHONENUMBER).validateField(request.getPhoneNumber());
			Validator.checkValidDate(request.getBirthDate(), request.getBirthMonth(),  request.getBirthYear());
			FieldValidationFactory.getStringInstance(ValidatorType.PASSWORD).validateField(request.getPassword());
			FieldValidationFactory.getStringInstance(ValidatorType.USERNAME).validateField(request.getUserName());
			FieldValidationFactory.getStringInstance(ValidatorType.EMAIL).validateField(request.getEmail());
			Validator.stringFieldNotPresent(request.getGender(), "Gender");
			Validator.stringFieldNotPresent(request.getFirstName(), "First Name");
			Validator.stringFieldNotPresent(request.getLastName(), "Last Name");
			return pjp.proceed();
		}catch(IllegalFieldException ex) {
			LOG.error("Invalid Field in the request while registering for user:: {}. Error:: {}",request.getUserName(),ex.getMessage());
			return new ResponseEntity<>(new GenericResponseBean(
					HttpStatus.BAD_REQUEST.value(),RequestResponseConstant.FAILURE_RESPONSE,ex.getMessage()
					),HttpStatus.BAD_REQUEST);
		}
		
	}
	
}
