package com.gmail.oprawam.githubapiconsumer.service;

import com.gmail.oprawam.githubapiconsumer.dto.RepoResponse;
import com.gmail.oprawam.githubapiconsumer.mapper.RepoResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class RepoServiceImpl implements RepoService {

    private final GithubRepoService githubRepoServiceImpl;
    private final GithubBranchService githubBranchServiceImpl;
    private final RepoResponseMapper repoResponseMapper;

    public Flux<RepoResponse> getReposWithoutFork(String username) {
        var githubRepoFluxWithoutFork = githubRepoServiceImpl.fetchGithubRepos(username)
                .filter(repo -> !repo.fork());
        return githubRepoFluxWithoutFork.flatMap(githubRepo -> githubBranchServiceImpl
                .fetchGithubRepoBranches(githubRepo)
                .map(githubBranchList -> repoResponseMapper.githubRepoAndGithubBranchesToRepoResponse(githubRepo, githubBranchList)));
//                (githubBranchList -> Mono.just(repoResponseMapper.githubRepoAndGithubBranchesToRepoResponse(githubRepo, githubBranchList))));

//                .map(githubBranchList -> repoResponseMapper.githubRepoAndGithubBranchesToRepoResponse(githubRepo, githubBranchList)));
//                .collectList()
//                .flatMap(githubBranchList -> Mono.just(repoResponseMapper.githubRepoAndGithubBranchesToRepoResponse(githubRepo, githubBranchList))));
    }
}
