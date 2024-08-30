package dev.danvega;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	/*
	 * Create a single RestClient bean that can be used throughout the application
	 */
	@Bean
	RestClient restClient(RestClient.Builder builder, ClientLoggerRequestInterceptor clientLoggerRequestInterceptor) {
		return builder
				.baseUrl("https://jsonplaceholder.typicode.com/")
				.requestInterceptor(clientLoggerRequestInterceptor)
				.build();
	}

}
