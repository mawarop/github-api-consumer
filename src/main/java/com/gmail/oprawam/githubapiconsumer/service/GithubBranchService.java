package com.gmail.oprawam.githubapiconsumer.service;

import com.gmail.oprawam.githubapiconsumer.dto.githubdto.GithubBranch;
import com.gmail.oprawam.githubapiconsumer.dto.githubdto.GithubRepo;
import reactor.core.publisher.Mono;

import java.util.List;

public interface GithubBranchService {
    Mono<List<GithubBranch>> fetchGithubRepoBranches(GithubRepo githubRepo);

}
