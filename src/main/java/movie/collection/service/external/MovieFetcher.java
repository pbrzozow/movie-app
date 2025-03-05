package movie.collection.service.external;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import lombok.extern.slf4j.Slf4j;
import movie.collection.dto.external.MovieExternalDto;
import movie.collection.mapper.MovieMapper;
import movie.collection.service.MovieService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
@Component
@PropertySource("classpath:application.properties")
public class MovieFetcher {
    @Value("${movie.api.key}")
    private String apiKey;
    private static final String API_URL="https://imdb236.p.rapidapi.com/imdb/top250-movies";
    private static final String API_HOST = "imdb236.p.rapidapi.com";

    private final ObjectMapper objectMapper;
    private final MovieMapper movieMapper;
    private final MovieService movieService;

    public MovieFetcher(MovieMapper movieMapper, MovieService movieService) {
        this.movieMapper = movieMapper;
        this.movieService = movieService;
        ObjectMapper objectMapper = new ObjectMapper();
         this.objectMapper = objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    }

    @Async
    public CompletableFuture<Void> fetchMovies() {
        return CompletableFuture.runAsync(() -> {
            HttpResponse<String> response = Unirest.get(API_URL)
                    .header("x-rapidapi-key", apiKey)
                    .header("x-rapidapi-host", API_HOST)
                    .asString();

            String body = response.getBody();
            try {
                List<MovieExternalDto> movies = objectMapper.readValue(body, new TypeReference<List<MovieExternalDto>>() {});
                movies.stream().map(movieMapper::externalDtoToEntity).forEach(movieService::saveOrUpdateMovie);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

}
