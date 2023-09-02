package com.gmail.oprawam.githubapiconsumer.consumer.controler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.oprawam.githubapiconsumer.consumer.githubdto.GithubRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ConsumerController {
    private final ObjectMapper mapper;
    @GetMapping("api/v1/github/users/{username}/repositories")
    public void getUserRepositories(@RequestHeader("Accept") String accept , @PathVariable String username){

        WebClient webClient = WebClient.create("https://api.github.com");
        Mono<ResponseEntity<String>> result = webClient.get().uri("/users/{username}/repos", username)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve().toEntity(String.class);
        result.subscribe(r -> {
            try {
                List<GithubRepo> repoList = mapper.readValue(r.getBody(), mapper.getTypeFactory().constructCollectionType(List.class, GithubRepo.class));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

        });
    }

}
