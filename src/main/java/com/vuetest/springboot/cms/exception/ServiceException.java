package com.vuetest.springboot.cms.exception;

import lombok.Getter;

/**
 * 
 * @author dongc
 * 自定义异常
 */
@Getter
public class ServiceException extends RuntimeException{

	private  String code;
 
    public ServiceException(String code,String msg) {
       super(msg);
       this.code =code;
    }
}
