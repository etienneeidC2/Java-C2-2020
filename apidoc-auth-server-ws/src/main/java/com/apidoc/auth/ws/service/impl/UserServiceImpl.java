package com.apidoc.auth.ws.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apidoc.auth.ws.auth.model.response.UserRest;
import com.apidoc.auth.ws.io.entity.UserEntity;
import com.apidoc.auth.ws.io.repositories.UserRepository;
import com.apidoc.auth.ws.security.SecurityConstants;
import com.apidoc.auth.ws.service.UserService;
import com.apidoc.auth.ws.shared.dto.UserDto;
import com.apidoc.auth.ws.shared.dto.Utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	Utils utils;

	@Override
	public UserDto createUser(UserDto user) {
		
		UserDto returnValue = new UserDto();
		
		UserEntity oldUserDetails = userRepository.findByEmail(user.getEmail());
		
		if( oldUserDetails != null) {
			BeanUtils.copyProperties(oldUserDetails, returnValue);
			
		} else {
			UserEntity userEntity = new UserEntity();
			BeanUtils.copyProperties(user, userEntity);
			
			String publicUserId = utils.generateUserId(30);
			userEntity.setUserId(publicUserId);
			
			UserEntity storedUserDetails = userRepository.save(userEntity);
			
			BeanUtils.copyProperties(storedUserDetails, returnValue);
		}
		
		String token = Jwts.builder()
				.setSubject(returnValue.getUserId())
				.setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_DATE))
				.signWith(SignatureAlgorithm.HS512, SecurityConstants.TOKEN_SECRET)
				.compact();
		
		returnValue.setToken(token);
		
		return returnValue;
	}
	
	@Override
	public List<UserDto> getAllUsers() {
		
		List<UserDto> returnValue = new ArrayList<>();
				
		List<UserEntity> allUsers= userRepository.findAll();
				
		for (UserEntity userEntity : allUsers) {
			UserDto userDto = new UserDto();
			BeanUtils.copyProperties(userEntity, userDto);
			returnValue.add(userDto);
		}
		
		return returnValue;
	}

}
