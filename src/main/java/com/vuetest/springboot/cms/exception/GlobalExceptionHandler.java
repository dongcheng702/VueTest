package com.vuetest.springboot.cms.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vuetest.springboot.cms.common.Result;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	/**
	 * 如果抛出的是ServiceException,则调用此方法
	 * @param e 业务异常
	 * @return Result
	 */
	@ExceptionHandler(ServiceException.class)
	@ResponseBody
	public Result handle(ServiceException e) {
		return Result.error(e.getCode(), e.getMessage());	
	}
}
