package com.vuetest.springboot.cms.entity.userentity;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class UserBean {
	
	private String employeeId;

	private String name;
	
	private String sex;

	private String birthday;

	private String address;

	private String phone;

	private String joiningDate;

	private String mail;

	private String jobType;

	private String jobLevel;
	
	private String employeeKbn;
	
	private String employeeKbnForList;
	
	private BigDecimal salary;
	
	private String employeeType;
	
	private String hasTax;
	
	private int topWorkHour;
	
	private int downWorkHour;
	
	private String loginId;
	
	private String password;
	
	private String registDay;
	
	private String updateDay;
}
