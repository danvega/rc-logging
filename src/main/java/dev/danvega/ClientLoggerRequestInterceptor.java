package dev.danvega;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class ClientLoggerRequestInterceptor implements ClientHttpRequestInterceptor {
    
    private static final Logger log = LoggerFactory.getLogger(ClientLoggerRequestInterceptor.class);

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        var response = execution.execute(request, body);
        logRequest(request, body);
        logResponse(request,response);
        return response;
    }

    private void logRequest(HttpRequest request, byte[] body) {
        log.info("Request: {} {}", request.getMethod(), request.getURI());
        logHeaders(request.getHeaders());
        if (body.length > 0) {
            log.info("Request body: {}", new String(body, StandardCharsets.UTF_8));
        }
    }

    private void logResponse(HttpRequest request, ClientHttpResponse response) throws IOException {
        log.info("Response status: {}", response.getStatusCode());
        logHeaders(request.getHeaders());
        var responseBody = response.getBody().readAllBytes();
        if( responseBody.length > 0 ) {
            log.info("Response body: {}", new String(responseBody, StandardCharsets.UTF_8));
        }
    }

    private void logHeaders(HttpHeaders headers) {
        headers.forEach((name,values) -> values.forEach(value -> log.info("{}={}",name, value)));
    }

}
