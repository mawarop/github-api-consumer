package com.gmail.oprawam.githubapiconsumer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class RepoResponse {
    private String repositoryName;
    private String ownerLogin;
    private List<Branch> branches;
}
