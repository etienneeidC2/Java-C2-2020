package com.apidoc.auth.ws.service;

import com.apidoc.auth.ws.shared.dto.UserDto;

public interface UserService {
	
	UserDto createUser(UserDto user);
	
}
