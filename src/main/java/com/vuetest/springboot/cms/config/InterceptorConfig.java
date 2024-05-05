package com.vuetest.springboot.cms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.vuetest.springboot.cms.interceptor.JwtInterceptor;

/**
 * 拦截器的配置类
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer{
	/**
     * 重写addInterceptors()实现拦截器
     * 配置：要拦截的路径以及不拦截的路径
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor())  //配置拦截规则
                .addPathPatterns("/**")  // 拦截所有请求，通过判断token是否合法来决定是否需要登录
                .excludePathPatterns(
                "/login",  //登录
                 "/register"//注册
                );//放行的请求
    }
 
    @Bean
    public JwtInterceptor jwtInterceptor() {
        return new JwtInterceptor();
    }
}
