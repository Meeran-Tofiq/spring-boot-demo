package com.example.spring_boot_demo;

import com.example.spring_boot_demo.externaluser.ExternalUser;
import com.example.spring_boot_demo.externaluser.ExternalUserHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.util.List;

@SpringBootApplication
public class SpringBootDemoApplication {

	private static final Logger log = LoggerFactory.getLogger(SpringBootDemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringBootDemoApplication.class, args);
	}

	@Bean
	ExternalUserHttpClient externalUserHttpClient() {
		RestClient restClient = RestClient.create("https://jsonplaceholder.typicode.com/");
		HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient)).build();
		return factory.createClient(ExternalUserHttpClient.class);
	}

	@Bean
	CommandLineRunner commandLineRunner(ExternalUserHttpClient externalUserHttpClient) {
		return args -> {
			List<ExternalUser> users = externalUserHttpClient.findAll();
			System.out.println(users.getFirst());
		};
	}
}
