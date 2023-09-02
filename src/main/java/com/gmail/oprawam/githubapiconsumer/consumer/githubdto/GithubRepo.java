package com.gmail.oprawam.githubapiconsumer.consumer.githubdto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class GithubRepo {
    private String name;
    @JsonProperty("commits_url")
    private String commitsUrl;
    private GithubOwner owner;


}
