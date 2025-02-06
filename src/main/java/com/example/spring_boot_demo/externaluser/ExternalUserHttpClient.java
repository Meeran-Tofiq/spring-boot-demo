package com.example.spring_boot_demo.externaluser;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

import java.util.List;

public interface ExternalUserHttpClient {
	@GetExchange("/users")
	List<ExternalUser> findAll();

	@GetExchange("/users/{id}")
	ExternalUser findById(@PathVariable Integer id);
}
