package com.apidoc.gateway.ws.ui.user.controller;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apidoc.gateway.ws.model.response.CallRest;
import com.google.gson.Gson;

//import com.apidoc.auth.ws.auth.model.response.CallRest;
//import com.apidoc.auth.ws.auth.model.response.UserRest;
//import com.apidoc.auth.ws.service.UserService;
//import com.apidoc.auth.ws.shared.dto.UserDto;

@RestController
@RequestMapping("users")
public class UserController {

	@GetMapping
	public ResponseEntity<CallRest> getAllUsers() throws IOException, InterruptedException {
	
		HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).connectTimeout(Duration.ofSeconds(10)).build();
        
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:8081/users")).header("Content-Type", "application/json").build();
        		
		CallRest returnValue = new CallRest();
		
		JSONObject resBody = new JSONObject();
		
		try {
			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			
			resBody = new JSONObject(response.body());
			
			System.out.println(response.statusCode());
			
			if(response.statusCode() != 200) {				
				throw new Exception(resBody.getString("data"));
			}
			
		} catch (Exception exp) {
			returnValue.setStatus("error");
			returnValue.setData(exp.getMessage());
			
			return new ResponseEntity<CallRest>(returnValue, HttpStatus.BAD_REQUEST);
		}
				
		returnValue.setStatus(resBody.getString("status"));
		
		Gson gson = new Gson();
		Object data = gson.fromJson(resBody.getJSONArray("data").toString(), Object.class);
		returnValue.setData(data);
		
		return new ResponseEntity<CallRest>(returnValue, HttpStatus.OK);
	}
	
}
