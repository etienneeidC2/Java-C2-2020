package com.apidoc.action.ws.io.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.apidoc.action.ws.io.entity.ApiEntity;

@Repository
public interface ApiRepository extends CrudRepository<ApiEntity, Long> {

	ApiEntity findByIdAndUserId(Long apiId, String userId);
	
	ApiEntity findByUserId(String userId);
	
	List<ApiEntity> findAllByUserId(String userId);
}
