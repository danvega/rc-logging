package dev.danvega.post;

import dev.danvega.ClientLoggerRequestInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class PostClient {

    private static final Logger log = LoggerFactory.getLogger(PostClient.class);
    private final RestClient restClient;

    public PostClient(RestClient.Builder builder, ClientLoggerRequestInterceptor clientLoggerRequestInterceptor) {
        this.restClient = builder
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .requestInterceptor(clientLoggerRequestInterceptor)
                .build();
    }

    public List<Post> findAll() {
        return restClient.get()
                .uri("/posts")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }

    public Post findById(Integer id) {
        return restClient.get()
                .uri("/posts/{id}",id)
                .retrieve()
                .toEntity(Post.class)
                .getBody();
    }

}
