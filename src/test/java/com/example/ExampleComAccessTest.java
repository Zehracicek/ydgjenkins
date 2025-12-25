package com.example;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SuppressWarnings("resource")
public class ExampleComAccessTest {

    @Test
    public void exampleDotComIsReachable() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://example.com"))
                .timeout(Duration.ofSeconds(10))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // BOZULDU: 404 bekliyor ama 200 gelecek
        assertEquals(404, response.statusCode(), "Expected HTTP 404 from https://example.com");
        assertTrue(response.body().contains("Example Domain"), "Response body should contain 'Example Domain'");
    }
}