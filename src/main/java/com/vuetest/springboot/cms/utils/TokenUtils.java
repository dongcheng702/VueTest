package com.vuetest.springboot.cms.utils;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import cn.hutool.core.date.DateUtil;

@Component
public class TokenUtils {
	
	public static String genToken(String userId,String sign) {
		return JWT.create().withAudience(userId)
					.withExpiresAt(DateUtil.offsetHour(new Date(), 2))
					.sign(Algorithm.HMAC256(sign));	
	}
}
