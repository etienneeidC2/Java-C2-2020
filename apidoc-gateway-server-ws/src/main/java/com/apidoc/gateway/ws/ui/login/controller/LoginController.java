package com.apidoc.gateway.ws.ui.login.controller;

import java.net.http.*;
import java.io.IOException;
import java.net.URI;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.apidoc.gateway.ws.model.request.GoogleTokenModel;
import com.apidoc.gateway.ws.model.response.CallRest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

@RestController
@RequestMapping("login")
public class LoginController {
	
	@CrossOrigin(
		    allowCredentials = "true",
		    origins = "*", 
		    allowedHeaders = "*", 
		    methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT}
		)
	@PostMapping
	public ResponseEntity<CallRest> createUser(@RequestBody GoogleTokenModel token) throws IOException, InterruptedException {
	
		HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).connectTimeout(Duration.ofSeconds(10)).build();
		
		Map<Object, Object> postBody = new HashMap<>();
		postBody.put("token", token.getToken());
		
		ObjectMapper objectMapper = new ObjectMapper();
	    String requestBody = objectMapper
	          .writerWithDefaultPrettyPrinter()
	          .writeValueAsString(postBody);
        
        HttpRequest request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(requestBody)).uri(URI.create("http://localhost:8081/login")).header("Content-Type", "application/json").build();
        		
		CallRest returnValue = new CallRest();
		
		JSONObject resBody = new JSONObject();
		
		try {
			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			
			resBody = new JSONObject(response.body());
						
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
		Object data = gson.fromJson(resBody.getJSONObject("data").toString(), Object.class);
		returnValue.setData(data);
		
		return new ResponseEntity<CallRest>(returnValue, HttpStatus.OK);
	}
}

