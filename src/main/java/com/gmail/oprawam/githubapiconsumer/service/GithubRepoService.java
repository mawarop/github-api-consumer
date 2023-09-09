package com.gmail.oprawam.githubapiconsumer.service;

import com.gmail.oprawam.githubapiconsumer.dto.githubdto.GithubRepo;
import reactor.core.publisher.Flux;

public interface GithubRepoService {
    Flux<GithubRepo> fetchGithubRepos(String username);
}
