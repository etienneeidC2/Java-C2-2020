package com.apidoc.gateway.ws.model.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class GoogleTokenModel {

	@NotNull(message="The token is required, yet not provided")
	@Size(min=1, message="The token is required, yet not provided")
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
}
