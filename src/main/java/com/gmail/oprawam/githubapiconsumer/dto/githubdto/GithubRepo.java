package com.gmail.oprawam.githubapiconsumer.dto.githubdto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class GithubRepo {
    private String name;
    private boolean fork;
    @JsonProperty("branches_url")
    private String branchesUrl;
    private GithubOwner owner;
}
