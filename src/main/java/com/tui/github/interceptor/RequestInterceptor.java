package com.tui.github.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.servlet.HandlerInterceptor;

import com.tui.github.exception.custom.ExceptionMessageConstant;
import com.tui.github.util.ApplicationConstant;

/**
 * This is Request Interceptor Class, All request first intercept here.    
 * @author
 *
 */
@Component
public class RequestInterceptor implements HandlerInterceptor{
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse responwse, Object object)
			throws HttpMediaTypeNotAcceptableException {
		String accept=request.getHeader(ApplicationConstant.ACCEPT);
		if(request.getRequestURI().contains(ApplicationConstant.SWAGGER)) {
			return true;
		}
		if(!MediaType.APPLICATION_JSON_VALUE.equals(accept)) {
			throw new HttpMediaTypeNotAcceptableException(ExceptionMessageConstant.APPLICATION_JSON_NOT_FOUND);
		}
		return true;
	}

}
