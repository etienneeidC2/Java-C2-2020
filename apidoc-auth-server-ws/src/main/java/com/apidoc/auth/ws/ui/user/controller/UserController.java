package com.apidoc.auth.ws.ui.user.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apidoc.auth.ws.auth.model.response.CallRest;
import com.apidoc.auth.ws.auth.model.response.UserRest;
import com.apidoc.auth.ws.service.UserService;
import com.apidoc.auth.ws.shared.dto.UserDto;

@RestController
@RequestMapping("users")
public class UserController {

	@Autowired
	UserService userService;
	
	@GetMapping
	public ResponseEntity<CallRest> getAllUsers() throws IOException, InterruptedException {
		
		CallRest returnValue = new CallRest();
		
		List<UserRest> data = new ArrayList<>();
		
		List<UserDto> allUsers = userService.getAllUsers();
		
		for (UserDto userDto : allUsers) {
			UserRest userRest = new UserRest();			
			BeanUtils.copyProperties(userDto, userRest);
			data.add(userRest);
		}
		
		returnValue.setStatus("success");
		returnValue.setData(data);
		
		return new ResponseEntity<CallRest>(returnValue, HttpStatus.OK);
	}
	
}
