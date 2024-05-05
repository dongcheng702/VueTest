package com.vuetest.springboot.cms.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.vuetest.springboot.cms.common.AuthAccess;
import com.vuetest.springboot.cms.common.Constants;
import com.vuetest.springboot.cms.entity.userentity.UserBean;
import com.vuetest.springboot.cms.exception.ServiceException;
import com.vuetest.springboot.cms.mapper.usermapper.UserMapper;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtInterceptor implements HandlerInterceptor{

	@Autowired
	UserMapper mapper;
	
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// 获取Header里的token
		String token = request.getHeader("token");
        // 如果不是映射到方法直接通过
		 if (handler instanceof HandlerMethod) {
	            AuthAccess annotation = ((HandlerMethod) handler).getMethodAnnotation(AuthAccess.class);
	            if (annotation != null) {
	                return true;
	            }
	        }
		// 执行认证
        if (StringUtils.isBlank(token)) {
            throw new ServiceException(Constants.CODE_600,"无token,请先登录");
        }
        // 验证 token
        isTokenValid(token);

        return true;
	}
	
	// 验证 token 的方法
    public boolean isTokenValid(String token) {
    	 // 获取 token 中的 user id
        String userId;
        try {
            userId = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException j) {
            throw new ServiceException(Constants.CODE_600,"token验证失败");
        }
        // 根据token中的userid查询数据库
        UserBean loginId = new UserBean();
        loginId.setLoginId(userId);
        UserBean user = mapper.selectUser(loginId);
        if (user == null) {
            throw new ServiceException(Constants.CODE_600,"用户不存在,请重新登录");
        }
        // 用户密码加签生成验证器
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
        try {
            jwtVerifier.verify(token); // 验证token
        } catch (JWTVerificationException e) {
            throw new ServiceException(Constants.CODE_600,"token过期,请重新登录");
        }
        return true;
    }
}
