package com.apidoc.gateway.ws.model.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateApiModel {
	
	private String id;
	private String userId;
	@NotNull(message="The name is required, yet not provided")
	@Size(min=1, message="The name is required, yet not provided")
	private String name;
	@NotNull(message="The method is required, yet not provided")
	@Size(min=1, message="The method is required, yet not provided")
	private String method;
	@NotNull(message="The route is required, yet not provided")
	@Size(min=1, message="The route is required, yet not provided")
	private String route;
	private String description;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getRoute() {
		return route;
	}
	public void setRoute(String route) {
		this.route = route;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

}
