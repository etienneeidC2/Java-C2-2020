package com.apidoc.ws.security;

public class SecurityConstraints {
	public static final long EXPIRATION_DATE = 864000000; // 10 days
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	public static final String SIGN_UP_URL = "/user";
	public static final String TOKEN_SECRET = "jf9i4jgu83nfl0";
}
