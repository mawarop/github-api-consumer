package com.gmail.oprawam.githubapiconsumer.dto;

import lombok.*;

import java.util.List;

//@Getter
//@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class RepoResponse {
    private String repositoryName;
    private String ownerLogin;
    private List<Branch> branches;
}
