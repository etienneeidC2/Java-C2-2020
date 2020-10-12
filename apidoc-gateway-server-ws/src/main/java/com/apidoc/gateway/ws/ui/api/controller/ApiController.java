package com.apidoc.gateway.ws.ui.api.controller;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.apidoc.gateway.ws.model.request.ApiModel;
import com.apidoc.gateway.ws.model.response.CallRest;
import com.apidoc.gateway.ws.security.SecurityConstants;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import io.jsonwebtoken.Jwts;

//import com.apidoc.action.ws.io.repositories.ApiRepository;
//import com.apidoc.action.ws.model.request.ApiModel;
//import com.apidoc.action.ws.model.response.ApiRest;
//import com.apidoc.action.ws.model.response.CallRest;
//import com.apidoc.action.ws.service.ApiService;
//import com.apidoc.action.ws.shared.dto.ApiDto;


@RestController
@RequestMapping("api")
public class ApiController {

	@CrossOrigin(
		    allowCredentials = "true",
		    origins = "*", 
		    allowedHeaders = "*", 
		    methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT}
		)
	@GetMapping(path="/{userId}")
	public ResponseEntity<CallRest> getUserApis(@PathVariable String userId) throws IOException, InterruptedException {
	
		HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).connectTimeout(Duration.ofSeconds(10)).build();
        
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:8082/api/" + userId)).header("Content-Type", "application/json").build();
        		
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
		Object data = gson.fromJson(resBody.getJSONArray("data").toString(), Object.class);
		returnValue.setData(data);
		
		return new ResponseEntity<CallRest>(returnValue, HttpStatus.OK);
	}

	@PostMapping()
	public ResponseEntity<CallRest> createApi(HttpServletRequest req, @RequestBody ApiModel api) throws IOException, InterruptedException {
		
		CallRest returnValue = new CallRest();
		
		String token = req.getHeader(SecurityConstants.HEADER_STRING);
		
		if(token == null || !token.startsWith(SecurityConstants.TOKEN_PREFIX)) {
			returnValue.setStatus("unauthorized");
			returnValue.setData("You are unauthorized to perform this action");
			
			return new ResponseEntity<CallRest>(returnValue, HttpStatus.UNAUTHORIZED);
		}
		
		token = token.replace(SecurityConstants.TOKEN_PREFIX, "");
		
		String userId = Jwts.parser().setSigningKey(SecurityConstants.TOKEN_SECRET).parseClaimsJws(token).getBody().getSubject();
		
		Map<Object, Object> postBody = new HashMap<>();
		postBody.put("name", api.getName());
		postBody.put("method", api.getMethod());
		postBody.put("route", api.getRoute());
		postBody.put("description", api.getDescription());
		
		ObjectMapper objectMapper = new ObjectMapper();
	    String requestBody = objectMapper
	          .writerWithDefaultPrettyPrinter()
	          .writeValueAsString(postBody);
		
		HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).connectTimeout(Duration.ofSeconds(10)).build();
        
		HttpRequest request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(requestBody)).uri(URI.create("http://localhost:8082/api/" + userId)).header("Content-Type", "application/json").build();
		
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

	@PutMapping(path="/{apiId}")
	public ResponseEntity<CallRest> editApi(HttpServletRequest req,  @PathVariable("apiId") Long apiId, @RequestBody ApiModel api) throws IOException, InterruptedException {
		
		CallRest returnValue = new CallRest();
		
		String token = req.getHeader(SecurityConstants.HEADER_STRING);
		
		if(token == null || !token.startsWith(SecurityConstants.TOKEN_PREFIX)) {
			returnValue.setStatus("unauthorized");
			returnValue.setData("You are unauthorized to perform this action");
			
			return new ResponseEntity<CallRest>(returnValue, HttpStatus.UNAUTHORIZED);
		}
		
		token = token.replace(SecurityConstants.TOKEN_PREFIX, "");
		
		String userId = Jwts.parser().setSigningKey(SecurityConstants.TOKEN_SECRET).parseClaimsJws(token).getBody().getSubject();
		
		Map<Object, Object> postBody = new HashMap<>();
		postBody.put("name", api.getName());
		postBody.put("method", api.getMethod());
		postBody.put("route", api.getRoute());
		postBody.put("description", api.getDescription());
		
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(Include.NON_NULL);
		
	    String requestBody = objectMapper
	          .writerWithDefaultPrettyPrinter()
	          .writeValueAsString(postBody);
		
		HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).connectTimeout(Duration.ofSeconds(10)).build();
        
		HttpRequest request = HttpRequest.newBuilder().PUT(HttpRequest.BodyPublishers.ofString(requestBody)).uri(URI.create("http://localhost:8082/api/" + userId + "/" + apiId)).header("Content-Type", "application/json").build();
		
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
//	
	@DeleteMapping(path="/{apiId}")
	public ResponseEntity<CallRest> deleteApi(HttpServletRequest req,  @PathVariable("apiId") Long apiId) throws IOException, InterruptedException {
 
		CallRest returnValue = new CallRest();
		
		String token = req.getHeader(SecurityConstants.HEADER_STRING);
		
		if(token == null || !token.startsWith(SecurityConstants.TOKEN_PREFIX)) {
			returnValue.setStatus("unauthorized");
			returnValue.setData("You are unauthorized to perform this action");
			
			return new ResponseEntity<CallRest>(returnValue, HttpStatus.UNAUTHORIZED);
		}
		
		token = token.replace(SecurityConstants.TOKEN_PREFIX, "");
		
		String userId = Jwts.parser().setSigningKey(SecurityConstants.TOKEN_SECRET).parseClaimsJws(token).getBody().getSubject();
		
		HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).connectTimeout(Duration.ofSeconds(10)).build();
        
		HttpRequest request = HttpRequest.newBuilder().DELETE().uri(URI.create("http://localhost:8082/api/" + userId + "/" + apiId)).header("Content-Type", "application/json").build();
		
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
		returnValue.setData(resBody.getString("data"));
		
		return new ResponseEntity<CallRest>(returnValue, HttpStatus.OK);
		
	}
}

