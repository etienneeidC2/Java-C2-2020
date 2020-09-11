package com.cnamtv.ws.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cnamtv.ws.UserRepository;
import com.cnamtv.ws.io.entity.UserEntity;
import com.cnamtv.ws.service.UserService;
import com.cnamtv.ws.shared.dto.UserDto;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository userRepository;

	@Override
	public UserDto createUser(UserDto user) {
		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(user, userEntity);
		
		userEntity.setUserId("testUserId");
		
		UserEntity storedUSerDetails = userRepository.save(userEntity);
		
		UserDto returnValue = new UserDto();
		BeanUtils.copyProperties(storedUSerDetails, returnValue);
		
		return returnValue;
	}

}
