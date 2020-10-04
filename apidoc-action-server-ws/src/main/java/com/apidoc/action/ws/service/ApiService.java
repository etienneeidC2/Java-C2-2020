package com.apidoc.action.ws.service;

import java.util.List;

import com.apidoc.action.ws.shared.dto.ApiDto;

public interface ApiService {
	
	List<ApiDto> getUserApis(String userId);
	
	ApiDto createApi(ApiDto api);
		
	ApiDto updateApi(String userId, Long apiId, ApiDto api) throws Exception;
	
	void deleteApi(String userId, Long apiId) throws Exception;
	
}
