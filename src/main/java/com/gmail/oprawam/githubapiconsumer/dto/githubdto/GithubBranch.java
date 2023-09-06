package com.gmail.oprawam.githubapiconsumer.dto.githubdto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class GithubBranch {
    private String name;
    private GithubCommit commit;
}
