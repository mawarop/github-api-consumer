package com.gmail.oprawam.githubapiconsumer.consumer.githubdto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class GithubOwner {
    private String login;
}
