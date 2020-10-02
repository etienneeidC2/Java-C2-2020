package com.apidoc.auth.ws.ui.login.controller;

import java.net.http.*;
import java.io.IOException;
import java.net.URI;
import java.time.Duration;
import org.json.JSONObject;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apidoc.auth.ws.auth.model.request.GoogleTokenModel;
import com.apidoc.auth.ws.auth.model.response.ApiRest;
import com.apidoc.auth.ws.auth.model.response.UserRest;
import com.apidoc.auth.ws.service.UserService;
import com.apidoc.auth.ws.shared.dto.UserDto;


@RestController
@RequestMapping("login")
public class LoginController {

	@Autowired
	UserService userService;
	
	@PostMapping
	public ResponseEntity<ApiRest> createUser(@RequestBody GoogleTokenModel token) throws IOException, InterruptedException {
		
		String t = token.getToken();
		
		HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).connectTimeout(Duration.ofSeconds(10)).build();
		
		HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("https://oauth2.googleapis.com/tokeninfo?id_token=" + t)).build();
		
		ApiRest returnValue = new ApiRest();
		
		JSONObject resBody = new JSONObject();
		
		try {
			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			
			resBody = new JSONObject(response.body());
			
			if(response.statusCode() != 200) {				
				throw new Exception(resBody.getString("error"));
			}
			
		} catch (Exception exp) {
			returnValue.setStatus("error");
			returnValue.setData("Invalid Google token");
			
			return new ResponseEntity<ApiRest>(returnValue, HttpStatus.BAD_REQUEST);
		}
		
//		System.out.println(resBody.getString("given_name"));
//		System.out.println(resBody.getString("family_name"));
//		System.out.println(resBody.getString("email"));

		
		UserDto userDto = new UserDto();
		
		userDto.setFirstName(resBody.getString("given_name"));
		userDto.setLastName(resBody.getString("family_name"));
		userDto.setEmail(resBody.getString("email"));
		
		UserDto createdUser = userService.createUser(userDto);
		UserRest userRest = new UserRest();
		BeanUtils.copyProperties(createdUser, userRest);
		
		returnValue.setStatus("success");
		returnValue.setData(userRest);
		
		return new ResponseEntity<ApiRest>(returnValue, HttpStatus.OK);
	}
}

