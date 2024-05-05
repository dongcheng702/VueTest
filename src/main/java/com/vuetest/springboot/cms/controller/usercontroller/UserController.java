package com.vuetest.springboot.cms.controller.usercontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vuetest.springboot.cms.common.Constants;
import com.vuetest.springboot.cms.common.Result;
import com.vuetest.springboot.cms.form.userform.UserForm;
import com.vuetest.springboot.cms.service.userservice.UserService;

@RestController
public class UserController {
	
	@Autowired
	UserService service;
	
	@PostMapping("/login")
	public Result login(@RequestBody UserForm user) {
		UserForm ret = service.Login(user);
		if (ret == null) {
			return Result.error(Constants.CODE_600, "用户名或者密码错误");
		}
		return Result.success(ret);
	}
}
