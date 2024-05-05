package com.vuetest.springboot.cms.service.userservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vuetest.springboot.cms.entity.userentity.UserBean;
import com.vuetest.springboot.cms.form.userform.UserForm;
import com.vuetest.springboot.cms.mapper.usermapper.UserMapper;
import com.vuetest.springboot.cms.utils.TokenUtils;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserMapper mapper;

	@Override
	public UserForm Login(UserForm user) {
		UserBean ret = selectUser(user);
		if (ret != null) {
			UserForm retUser = new UserForm();
			retUser.setName(ret.getName());
			retUser.setUserId(ret.getLoginId());
			retUser.setPassword(ret.getPassword());
			retUser.setToken(TokenUtils.genToken(ret.getLoginId(), ret.getPassword()));
			return retUser;
		}
		return null;
	}
	
	private UserBean selectUser(UserForm user) {
		UserBean bean = new UserBean();
		bean.setLoginId(user.getUserId());
		return mapper.selectUser(bean);
	}
}
