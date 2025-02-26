package movie.collection.service.external;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import movie.collection.dto.external.MovieExternalDto;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
@PropertySource("classpath:application.properties")
public class MovieFetcher {
    @Value("${movie.api.key}")
    private String apiKey;
    private static final String API_URL="https://imdb236.p.rapidapi.com/imdb/top-box-office";
    private static final String API_HOST = "imdb236.p.rapidapi.com";

    private final ObjectMapper objectMapper;

    public MovieFetcher() {
        ObjectMapper objectMapper = new ObjectMapper();
         this.objectMapper = objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    }



    public CompletableFuture<List<MovieExternalDto>> fetchMovies() {
        AsyncHttpClient client = new DefaultAsyncHttpClient();

        return client.prepareGet(API_URL)
                .setHeader("x-rapidapi-key", apiKey)
                .setHeader("x-rapidapi-host", API_HOST)
                .execute()
                .toCompletableFuture()
                .thenApply(response -> {
                    try {
                        System.out.println(response.getResponseBody());
                        List<MovieExternalDto> movies = objectMapper.readValue(response.getResponseBody(),new TypeReference<List<MovieExternalDto>>(){} );
                        return movies;
                    } catch (IOException e) {
                        throw new RuntimeException("Error parsing API response", e);
                    }
                })
                .whenComplete((result, ex) -> {
                    try {
                        client.close();
                    } catch (IOException e) {
                        throw new RuntimeException("Error closing client", e);
                    }
                });
    }
}
