package com.gmail.oprawam.githubapiconsumer.controler;

import com.gmail.oprawam.githubapiconsumer.dto.Branch;
import com.gmail.oprawam.githubapiconsumer.dto.RepoResponse;
import com.gmail.oprawam.githubapiconsumer.dto.githubdto.GithubBranch;
import com.gmail.oprawam.githubapiconsumer.dto.githubdto.GithubRepo;
import com.gmail.oprawam.githubapiconsumer.exception.GeneralResponseException;
import com.gmail.oprawam.githubapiconsumer.exception.NotAcceptableException;
import com.gmail.oprawam.githubapiconsumer.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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

        if(accept.equals("application/xml")){
            throw new NotAcceptableException("Not acceptable Accept header");
        }

        return webClient.get().uri("/users/{username}/repos", username)
//                .accept(MediaType.APPLICATION_XML)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, e ->{
                if (e.statusCode().equals(HttpStatus.NOT_FOUND)) {
                    throw new NotFoundException("Repo not found");
                }
                else {
                    throw new GeneralResponseException("Error occured. Status " + e.statusCode());
                }
                })
                .bodyToFlux(new ParameterizedTypeReference<GithubRepo>() {
                })
                .filter(repo -> !repo.isFork())
                .flatMap(rwf -> {
                    String branchesUrl = rwf.getBranchesUrl();
                    return webClient.get().uri(branchesUrl.replaceFirst("\\{.*", ""))
                            .accept(MediaType.APPLICATION_JSON)
                            .retrieve()
                            .onStatus(HttpStatusCode::is4xxClientError, e ->{
                                if (e.statusCode().equals(HttpStatus.NOT_FOUND)) {
                                    throw new NotFoundException("Repo not found");
                                }
                                else {
                                    throw new GeneralResponseException("Error occured. Status " + e.statusCode());
                                }
                            })
                            .bodyToFlux(new ParameterizedTypeReference<GithubBranch>() {
                            })
                            .collectList().flatMap(githubBranchResultList -> {
                                RepoResponse repoResponse = RepoResponse.builder().
                                        repositoryName(rwf.getName())
                                        .ownerLogin(rwf.getOwner().getLogin())
                                        .branches(githubBranchResultList.stream().map(githubBranch ->
                                                new Branch(githubBranch.getName(), githubBranch.getCommit().getSha())).toList()).build();
                                return Mono.just(repoResponse);
                            });
                });
    }
}
