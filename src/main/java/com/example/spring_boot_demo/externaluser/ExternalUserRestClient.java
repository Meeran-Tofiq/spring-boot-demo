package com.example.spring_boot_demo.externaluser;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class ExternalUserRestClient {

	private final RestClient restClient;

	public ExternalUserRestClient(RestClient.Builder builder) {
		this.restClient = builder.baseUrl("https://jsonplaceholder.typicode.com/").build();
	}

	public List<ExternalUser> findAll() {
		return restClient.get().uri("/users").retrieve().body(new ParameterizedTypeReference<>() {
		});
	}

	public ExternalUser findById(Integer id) {
		return restClient.get().uri("/users/{id}", id).retrieve().body(ExternalUser.class);
	}
}
