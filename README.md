# Rest Client Logging in Spring Boot

This repository demonstrates how to implement basic logging for Spring's RestClient, introduced in Spring Framework 6.1 and Spring Boot 3.2. It showcases two approaches to logging HTTP requests and responses: inline logging and using a separate interceptor class.

## Features

- Logging of HTTP request and response details
- Two implementation approaches: inline and separate interceptor
- Logging of request/response headers, body, and status codes
- Easy integration with Spring Boot applications

## Prerequisites

- Java 17 or higher
- Spring Boot 3.2 or higher

## Implementation Approaches

### 1. Inline Logging

This approach implements logging directly within the RestClient configuration.

```java
public class PostClient {
    private final RestClient restClient;

    public PostClient(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .requestInterceptor((request, body, execution) -> {
                    var response = execution.execute(request, body);
                    logRequest(request, body);
                    logResponse(request,response);
                    return response;
                })
                .build();
    }

    private void logRequest(HttpRequest request, byte[] body) {
        // Logging implementation
    }

    private void logResponse(HttpRequest request, ClientHttpResponse response) {
        // Logging implementation
    }

    // RestClient methods...
}
```

### 2. Separate Interceptor Class

This approach uses a dedicated interceptor class implementing `ClientHttpRequestInterceptor`.

```java
@Component
public class ClientLoggerRequestInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
       var response = execution.execute(request, body);
       logRequest(request, body);
       logResponse(request,response);
       return response;
    }

    private void logRequest(HttpRequest request, byte[] body) {
        // Logging implementation
    }

    private void logResponse(ClientHttpResponse response) {
        // Logging implementation
    }
}
```

Usage with RestClient:

```java
@Component
public class PostClient {
    private final RestClient restClient;

    public PostClient(RestClient.Builder builder, ClientLoggerRequestInterceptor interceptor) {
        this.restClient = builder
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .requestInterceptor(interceptor)
                .build();
    }

    // RestClient methods...
}
```

## Benefits of Each Approach

1. Inline Logging:
    - Simplicity: All logging logic is in one place
    - Direct access to RestClient configuration

2. Separate Interceptor:
    - Reusability: Can be used with multiple RestClient instances
    - Separation of concerns: Keeps logging logic separate from client logic
    - Easier to unit test

## Customization

You can customize the logging by modifying the `logRequest` and `logResponse` methods. Consider adding:

- Logging level adjustments (e.g., DEBUG for detailed logs)
- Sensitive data masking
- Response body size limits for large payloads

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.