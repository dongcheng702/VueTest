package com.vuetest.springboot.cms.mapper.usermapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.vuetest.springboot.cms.entity.userentity.UserBean;

@Mapper
public interface UserMapper {
	
	
	@Select({
		"<script>",
		"select * from cms_employee",
		"<where>",
		"loginId = #{user.loginId}",
		"</where>",
	    "</script>"
	})
	public UserBean selectUser(@Param("user") UserBean user);
}
