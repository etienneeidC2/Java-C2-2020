package com.apidoc.ws.service.impl;

import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apidoc.ws.UserRepository;
import com.apidoc.ws.io.entity.UserEntity;
import com.apidoc.ws.security.SecurityConstraints;
import com.apidoc.ws.service.UserService;
import com.apidoc.ws.shared.dto.UserDto;
import com.apidoc.ws.shared.dto.Utils;

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
			
			UserEntity storedUSerDetails = userRepository.save(userEntity);
			
			BeanUtils.copyProperties(storedUSerDetails, returnValue);
		}
		
		String token = Jwts.builder()
				.setSubject(returnValue.getUserId())
				.setExpiration(new Date(System.currentTimeMillis() + SecurityConstraints.EXPIRATION_DATE))
				.signWith(SignatureAlgorithm.HS512, SecurityConstraints.TOKEN_SECRET)
				.compact();
		
		returnValue.setToken(token);
		
		return returnValue;
	}

}
