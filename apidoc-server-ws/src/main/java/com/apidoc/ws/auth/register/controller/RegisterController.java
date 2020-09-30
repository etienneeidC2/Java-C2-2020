package com.apidoc.ws.auth.register.controller;

import java.net.http.*;
import java.io.IOException;
import java.net.URI;
import java.time.Duration;
import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apidoc.ws.auth.model.request.GoogleTokenModel;
import com.apidoc.ws.auth.model.response.UserRest;
import com.apidoc.ws.service.UserService;
import com.apidoc.ws.shared.dto.UserDto;


@RestController
@RequestMapping("user")
public class RegisterController {

	@Autowired
	UserService userService;
	
	@GetMapping
	public String getUser() {
		return "Get user was called";
	}
	
	@PostMapping
	public UserRest createUser(@RequestBody GoogleTokenModel token) throws IOException, InterruptedException {
		
		String t = token.getToken();
		
		HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).connectTimeout(Duration.ofSeconds(10)).build();
		
		HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("https://oauth2.googleapis.com/tokeninfo?id_token=" + t)).build();
		
		HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
		
//		System.out.println(response.body());
		
		JSONObject resBody = new JSONObject(response.body());
//		System.out.println(resBody.getString("given_name"));
//		System.out.println(resBody.getString("family_name"));
//		System.out.println(resBody.getString("email"));

		UserRest returnValue = new UserRest();
		UserDto userDto = new UserDto();
		
		userDto.setFirstName(resBody.getString("given_name"));
		userDto.setLastName(resBody.getString("family_name"));
		userDto.setEmail(resBody.getString("email"));
		
		UserDto createdUser = userService.createUser(userDto);
		BeanUtils.copyProperties(createdUser, returnValue);
		
		return returnValue;
	}
	
	@PutMapping
	public String updateUser() {
		return "Update USer was called";
	}
}

