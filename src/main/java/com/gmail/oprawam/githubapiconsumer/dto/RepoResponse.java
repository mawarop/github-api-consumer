package com.gmail.oprawam.githubapiconsumer.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record RepoResponse(String repositoryName, String ownerLogin, List<Branch> branches) {
}
