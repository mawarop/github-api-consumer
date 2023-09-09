package com.gmail.oprawam.githubapiconsumer.service;

import com.gmail.oprawam.githubapiconsumer.dto.githubdto.GithubRepo;
import com.gmail.oprawam.githubapiconsumer.exception.GeneralResponseException;
import com.gmail.oprawam.githubapiconsumer.exception.NotFoundException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
public class GithubRepoServiceImpl implements GithubRepoService {
    private final WebClient webClient = WebClient.create("https://api.github.com");

    public Flux<GithubRepo> fetchGithubRepos(String username) {
        return webClient.get().uri("/users/{username}/repos", username)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, e -> {
                    if (e.statusCode().equals(HttpStatus.NOT_FOUND)) {
                        throw new NotFoundException("Repo not found");
                    } else {
                        throw new GeneralResponseException("Error occurred. Status " + e.statusCode());
                    }
                })
                .bodyToFlux(new ParameterizedTypeReference<GithubRepo>() {
                });
    }
}
