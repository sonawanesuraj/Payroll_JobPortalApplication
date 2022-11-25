package com.app.util;

public class ApiUrls {

	public static final String API = "/api";
	public static final String GET_ALL = "/all";
	public static final String USERS = "/users";
	public static final String PERMISSIONS = "/permissions";
	public static final String ROLE = "/role";
	public static final String JOB = "/job";
	public static final String USERJOB = "/userjob";
	public static final String USERROLE = "/userrole";
	public static final String ROLEPERMISSION = "/role-permission";
	public static final String EXCEL = "/excel";
	public static final String AUTH = "/auth";
	public static final String LOGIN = AUTH + "/login";
	public static final String REGISTER = AUTH + "/register";
	public static final String FORGOT_PASSWORD = AUTH + "/forgot-password";
	public static final String FORGOT_PASSWORD_CONFIRM = AUTH + "/forgot-password/confirm";
	public static final String LOGOUT = AUTH + "/logout";
	public static final String[] SWAGGER_URLS = { "/v3/api-docs", "/v2/api-docs", "/swagger-resources/**",
			"/swagger-ui/**", "/webjars/**", "/api/swagger-ui/index.html" };

	public static final String[] URLS_WITHOUT_HEADER = { ApiUrls.API + ApiUrls.LOGIN, ApiUrls.API + ApiUrls.REGISTER,
			ApiUrls.API + ApiUrls.FORGOT_PASSWORD, ApiUrls.API + ApiUrls.FORGOT_PASSWORD_CONFIRM, };

}
