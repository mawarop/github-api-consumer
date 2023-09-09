package com.gmail.oprawam.githubapiconsumer.service;

import com.gmail.oprawam.githubapiconsumer.dto.githubdto.GithubBranch;
import com.gmail.oprawam.githubapiconsumer.dto.githubdto.GithubRepo;
import reactor.core.publisher.Flux;

public interface GithubBranchService {
    Flux<GithubBranch> fetchGithubRepoBranches(GithubRepo githubRepo);

}
