package com.apidoc.action.ws.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apidoc.action.ws.io.entity.ApiEntity;
import com.apidoc.action.ws.io.repositories.ApiRepository;
import com.apidoc.action.ws.service.ApiService;
import com.apidoc.action.ws.shared.dto.ApiDto;

@Service
public class ApiServiceImpl implements ApiService {
	
	@Autowired
	ApiRepository apiRepository;
	
	@Override
	public List<ApiDto> getUserApis(String userId) {
		
		List<ApiDto> returnValue = new ArrayList<>();
				
		List<ApiEntity> allUserApis= apiRepository.findAllByUserId(userId);
		
		for (ApiEntity apiEntity : allUserApis) {
			ApiDto apiDto = new ApiDto();
			BeanUtils.copyProperties(apiEntity, apiDto);
			returnValue.add(apiDto);
		}
		
		return returnValue;
	}

	@Override
	public ApiDto createApi(ApiDto api) {
		
		ApiDto returnValue = new ApiDto();

		ApiEntity apiEntity = new ApiEntity();
		
		BeanUtils.copyProperties(api, apiEntity);
		
		ApiEntity storedApiDetails = apiRepository.save(apiEntity);
		
		BeanUtils.copyProperties(storedApiDetails, returnValue);
		
		return returnValue;
	}

	@Override
	public ApiDto updateApi(String userId, Long apiId, ApiDto api) throws Exception {
		
		ApiEntity apiEntity = apiRepository.findByIdAndUserId(apiId, userId);
		
		if (apiEntity == null) {
			throw new Exception("Invalid Api ID");
		}
		
		api.setId(apiId);
		api.setUserId(userId);
		
		if (api.getMethod() == null) {
			api.setMethod(apiEntity.getMethod());
		}
		
		if (api.getRoute() == null) {
			api.setRoute(apiEntity.getRoute());
		}
		
		if (api.getName() == null) {
			api.setName(apiEntity.getName());
		}
		
		if (api.getDescription() == null) {
			api.setDescription(apiEntity.getDescription());
		}
		
		BeanUtils.copyProperties(api, apiEntity);
		
		ApiEntity storedApiDetails = apiRepository.save(apiEntity);
		
		ApiDto returnValue = new ApiDto();
		
		BeanUtils.copyProperties(storedApiDetails, returnValue);
		
		return returnValue;
	}
	
	@Override
	public void deleteApi(String userId, Long apiId) throws Exception {
		
		ApiEntity apiEntity = apiRepository.findByIdAndUserId(apiId, userId);
		
		if (apiEntity == null) {
			throw new Exception("Invalid Api ID");
		}
		
		apiRepository.deleteById(apiId);
	}
	
}
