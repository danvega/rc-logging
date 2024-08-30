package dev.danvega.todo;

import dev.danvega.ClientLoggerRequestInterceptor;
import dev.danvega.post.PostClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class TodoClient {

    private static final Logger log = LoggerFactory.getLogger(PostClient.class);
    private final RestClient restClient;

    public TodoClient(RestClient.Builder builder, ClientLoggerRequestInterceptor clientLoggerRequestInterceptor) {
        this.restClient = builder
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .requestInterceptor(clientLoggerRequestInterceptor)
                .build();
    }

    public List<Todo> findAll() {
        return restClient.get()
                .uri("/todos")
                .retrieve()
                .body(new ParameterizedTypeReference<List<Todo>>() {});
    }

}
