package com.gmail.oprawam.githubapiconsumer.service;

import com.gmail.oprawam.githubapiconsumer.dto.githubdto.GithubBranch;
import com.gmail.oprawam.githubapiconsumer.dto.githubdto.GithubRepo;
import com.gmail.oprawam.githubapiconsumer.exception.GeneralResponseException;
import com.gmail.oprawam.githubapiconsumer.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GithubBranchServiceImpl implements GithubBranchService {
    private final WebClient.Builder webClientBuilder;
//    private final WebClient webClient = WebClient.create("https://api.github.com");

    public Mono<List<GithubBranch>> fetchGithubRepoBranches(GithubRepo githubRepo) {
        var branchesUrl = githubRepo.getBranchesUrl();
        return webClientBuilder.build().get().uri(branchesUrl.replaceFirst("\\{.*", ""))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus.NOT_FOUND::equals, e ->
                    Mono.error(new NotFoundException("Branch not found")))
                .bodyToMono(new ParameterizedTypeReference<>() {
                });
    }

}
