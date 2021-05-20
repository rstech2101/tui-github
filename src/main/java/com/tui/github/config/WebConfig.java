package com.tui.github.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.tui.github.interceptor.RequestInterceptor;

/**
 * Spring Web configuration to register required interceptors
 * @author
 *
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Autowired
	private RequestInterceptor requestInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registery) {
		registery.addInterceptor(requestInterceptor);
	}
}
