package com.gmail.oprawam.githubapiconsumer.service;

import com.gmail.oprawam.githubapiconsumer.dto.RepoResponse;
import reactor.core.publisher.Flux;

public interface RepoService {
    Flux<RepoResponse> getReposWithoutFork(String username);

}
