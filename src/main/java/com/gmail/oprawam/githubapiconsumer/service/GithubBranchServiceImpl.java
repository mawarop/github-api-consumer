package com.gmail.oprawam.githubapiconsumer.service;

import com.gmail.oprawam.githubapiconsumer.dto.githubdto.GithubBranch;
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
public class GithubBranchServiceImpl implements GithubBranchService{
    private final WebClient webClient = WebClient.create("https://api.github.com");

    public Flux<GithubBranch> fetchGithubRepoBranches(GithubRepo githubRepo){
        String branchesUrl = githubRepo.getBranchesUrl();
        return webClient.get().uri(branchesUrl.replaceFirst("\\{.*", ""))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, e ->{
                    if (e.statusCode().equals(HttpStatus.NOT_FOUND)) {
                        throw new NotFoundException("Branch not found");
                    }
                    else {
                        throw new GeneralResponseException("Error occurred. Status " + e.statusCode());
                    }
                })
                .bodyToFlux(new ParameterizedTypeReference<GithubBranch>() {
                });
    }

}
