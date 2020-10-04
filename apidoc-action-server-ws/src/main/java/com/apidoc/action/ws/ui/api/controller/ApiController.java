package com.apidoc.action.ws.ui.api.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apidoc.action.ws.io.repositories.ApiRepository;
import com.apidoc.action.ws.model.request.ApiModel;
import com.apidoc.action.ws.model.response.ApiRest;
import com.apidoc.action.ws.model.response.CallRest;
import com.apidoc.action.ws.service.ApiService;
import com.apidoc.action.ws.shared.dto.ApiDto;


@RestController
@RequestMapping("api")
public class ApiController {

	@Autowired
	ApiService apiService;
	
	@Autowired
	ApiRepository apiRepository;
	
	@GetMapping(path="/{userId}")
	public ResponseEntity<CallRest> getUserApis (@PathVariable String userId) {
		
		CallRest returnValue = new CallRest();
		
		List<ApiRest> data = new ArrayList<>();
		
		List<ApiDto> allApis = apiService.getUserApis(userId);
		
		for (ApiDto apiDto : allApis) {
			ApiRest apiRest = new ApiRest();
			BeanUtils.copyProperties(apiDto, apiRest);
			data.add(apiRest);
		}
		
		returnValue.setStatus("success");
		returnValue.setData(data);
		
		return new ResponseEntity<CallRest>(returnValue, HttpStatus.OK);
	}
	
	@PostMapping(path="/{userId}")
	public ResponseEntity<CallRest> createApi(@PathVariable("userId") String userId, @RequestBody ApiModel api) throws IOException, InterruptedException {
				
		ApiDto apiDto = new ApiDto();
		
		CallRest returnValue = new CallRest();
		
		BeanUtils.copyProperties(api, apiDto);
		
		apiDto.setUserId(userId);
		
		ApiDto createdApi = apiService.createApi(apiDto);
		ApiRest apiRest = new ApiRest();
		BeanUtils.copyProperties(createdApi, apiRest);
		
		returnValue.setStatus("success");
		returnValue.setData(apiRest);
		
		return new ResponseEntity<CallRest>(returnValue, HttpStatus.OK);
	}
	
	@PutMapping(path="/{userId}/{apiId}")
	public ResponseEntity<CallRest> editApi(@PathVariable("userId") String userId, @PathVariable("apiId") Long apiId, @RequestBody ApiModel api) throws IOException, InterruptedException {
				
		ApiDto apiDto = new ApiDto();
		
		CallRest returnValue = new CallRest();
		
		BeanUtils.copyProperties(api, apiDto);
		
		ApiRest apiRest = new ApiRest();
		
		try {
			ApiDto updatedApi = apiService.updateApi(userId, apiId, apiDto);	
			
			BeanUtils.copyProperties(updatedApi, apiRest);
		} catch (Exception exp) {
			returnValue.setStatus("Error");
			returnValue.setData(exp.getMessage());
			
			return new ResponseEntity<CallRest>(returnValue, HttpStatus.BAD_REQUEST);
		}
		
		returnValue.setStatus("success");
		returnValue.setData(apiRest);
		
		return new ResponseEntity<CallRest>(returnValue, HttpStatus.OK);
	}
	
	@DeleteMapping(path="/{userId}/{apiId}")
	public ResponseEntity<CallRest> deleteApi(@PathVariable("userId") String userId, @PathVariable("apiId") Long apiId) throws IOException, InterruptedException {
		
		CallRest returnValue = new CallRest();
			
		try {
			apiService.deleteApi(userId, apiId);
		} catch (Exception exp) {
			returnValue.setStatus("Error");
			returnValue.setData(exp.getMessage());
			
			return new ResponseEntity<CallRest>(returnValue, HttpStatus.BAD_REQUEST);
		}
				
		
		returnValue.setStatus("success");
		returnValue.setData("Successfully deleted Api");
		
		return new ResponseEntity<CallRest>(returnValue, HttpStatus.OK);
	}
}

