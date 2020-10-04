package com.apidoc.auth.ws.service;

import java.util.List;

import com.apidoc.auth.ws.shared.dto.UserDto;

public interface UserService {
	
	UserDto createUser(UserDto user);
	
	List<UserDto> getAllUsers();
}
