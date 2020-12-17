package com.guvenx.springdemo.aspect;

import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CRMLoggingAspect {

	// setup logger
	private Logger logger = Logger.getLogger(getClass().getName());

	// setup pointcut declarations
	@Pointcut("execution(* com.guvenx.springdemo.controller.*.*(..))")
	private void forControllerPackage() {
	}

	@Pointcut("execution(* com.guvenx.springdemo.service.*.*(..))")
	private void forServicePackage() {
	}

	@Pointcut("execution(* com.guvenx.springdemo.dao.*.*(..))")
	private void forDaoPackage() {
	}

	@Pointcut("forControllerPackage() || forServicePackage() || forDaoPackage()")
	private void forAppFlow() {
	}

	// add @Before advice
	@Before("forAppFlow()")
	public void before(JoinPoint joinPoint) {
		// display mothod we are calling
		String method = joinPoint.getSignature().toShortString();
		logger.info(">>>>> in @Before: calling method: " + method);

		// display the arguments to the method
		// get the arguments
		Object[] args = joinPoint.getArgs();

		// display agrs
		for (Object object : args)
			logger.info(">>>>> argument: " + object);

	}

	// add @AfterRurning advice

	@AfterReturning(pointcut = "forAppFlow()", returning = "result")
	public void afterReturning(JoinPoint joinPoint, Object result) {

		// display method we are returning from
		logger.info(">>>>> in @AfterReturning: from method: " + joinPoint.getSignature().toShortString());

		// display data returned
		logger.info(">>>>> result: " + result);
	}

}
