package com.app.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.app.configuration.jwtTokenUtil;
import com.app.entities.UserEntity;
import com.app.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class GlobalFunction implements HandlerInterceptor {

	@Autowired
	private jwtTokenUtil jwtTokenUtil;

	@Autowired
	UserRepository userRepository;

	public final static String CUSTUM_ATTRIBUTE_USER_ID = "X-user-id";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String authHeader = request.getHeader("Authorization");
		String tokenString = (null != authHeader) ? authHeader.split(" ")[1] : null;

		if (null != tokenString) {
			final String emailString = jwtTokenUtil.getUsernameFromToken(tokenString);

			UserEntity userEntity = userRepository.findByEmail(emailString);

			request.setAttribute("X-user-id", userEntity.getId());
		}

		return HandlerInterceptor.super.preHandle(request, response, handler);
	}

}