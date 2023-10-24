package com.gmail.oprawam.githubapiconsumer.service;

import com.gmail.oprawam.githubapiconsumer.dto.githubdto.GithubRepo;
import com.gmail.oprawam.githubapiconsumer.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GithubRepoServiceImpl implements GithubRepoService {
    //    private final WebClient webClient = WebClient.create("https://api.github.com");
    private final WebClient.Builder webClientBuilder;


    public Flux<GithubRepo> fetchGithubRepos(String username) {
        return webClientBuilder.build().get().uri("/users/{username}/repos", username)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus.NOT_FOUND::equals, e ->
                        Mono.error(new NotFoundException("Repo not found")))


//                .onStatus(HttpStatusCode::is4xxClientError, e -> {
//                    if (e.statusCode().equals(HttpStatus.NOT_FOUND)) {
//                        // todo rzucac mono.error i sprawdzic dlaczego przy mono.error inaczej obsluguje niz przy rzucaniu
////                        throw new NotFoundException("Repo not found");
//                        return Mono.error(new NotFoundException("Repo not found"));
//
//                    } else {
////                        throw new GeneralResponseException("Error occurred. Status " + e.statusCode());
//                        return Mono.error(new GeneralResponseException("Error occurred. Status " + e.statusCode()));
//
//                    }
//                })
                .bodyToFlux(new ParameterizedTypeReference<GithubRepo>() {
                });
    }
}
