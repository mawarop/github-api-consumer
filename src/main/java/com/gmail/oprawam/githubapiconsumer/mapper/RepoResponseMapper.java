package com.gmail.oprawam.githubapiconsumer.mapper;

import com.gmail.oprawam.githubapiconsumer.dto.Branch;
import com.gmail.oprawam.githubapiconsumer.dto.RepoResponse;
import com.gmail.oprawam.githubapiconsumer.dto.githubdto.GithubBranch;
import com.gmail.oprawam.githubapiconsumer.dto.githubdto.GithubRepo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RepoResponseMapper {
    public RepoResponse githubRepoAndGithubBranchesToRepoResponse(GithubRepo githubRepo, List<GithubBranch> githubBranchList) {
        return RepoResponse.builder().
                repositoryName(githubRepo.getName())
                .ownerLogin(githubRepo.getOwner().getLogin())
                .branches(githubBranchList.stream().map(githubBranch ->
                        new Branch(githubBranch.getName(), githubBranch.getCommit().getSha())).toList()).build();
    }
}
