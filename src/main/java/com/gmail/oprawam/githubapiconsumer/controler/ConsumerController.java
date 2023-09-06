package com.gmail.oprawam.githubapiconsumer.controler;

import com.gmail.oprawam.githubapiconsumer.dto.Branch;
import com.gmail.oprawam.githubapiconsumer.dto.RepoResponse;
import com.gmail.oprawam.githubapiconsumer.dto.githubdto.GithubBranch;
import com.gmail.oprawam.githubapiconsumer.dto.githubdto.GithubRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequiredArgsConstructor
public class ConsumerController {
    private final WebClient webClient = WebClient.create("https://api.github.com");

    @GetMapping("api/v1/github/users/{username}/repositories")
    public Flux<RepoResponse> getUserRepositories(@RequestHeader("Accept") String accept, @PathVariable String username) {

        return webClient.get().uri("/users/{username}/repos", username)
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToFlux(response -> {
                    if (response.statusCode().equals(HttpStatus.OK)) {
                        return response.bodyToFlux(new ParameterizedTypeReference<GithubRepo>() {
                        });
                    } else {
                        throw new RuntimeException("Error");
                    }
                })

                .filter(repo -> !repo.isFork())
                .flatMap(rwf -> {
                    String branchesUrl = rwf.getBranchesUrl();
                    return webClient.get().uri(branchesUrl.replaceFirst("\\{.*", ""))
                            .accept(MediaType.APPLICATION_JSON)
                            .exchangeToFlux(response -> {
                                if (response.statusCode().equals(HttpStatus.OK)) {
                                    return response.bodyToFlux(new ParameterizedTypeReference<GithubBranch>() {
                                    });
                                } else {
                                    throw new RuntimeException("Error");
                                }
                            })
                            .collectList().flatMap(githubBranchResultList -> {
                                RepoResponse repoResponse = RepoResponse.builder().
                                        repositoryName(rwf.getName()).
                                        ownerLogin(rwf.getOwner().getLogin())
                                        .branches(githubBranchResultList.stream().map(githubBranch ->
                                                new Branch(githubBranch.getName(), githubBranch.getCommit().getSha())).toList()).build();
                                return Mono.just(repoResponse);
                            });
                });
    }
}
