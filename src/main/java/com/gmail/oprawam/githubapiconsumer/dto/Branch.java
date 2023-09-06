package com.gmail.oprawam.githubapiconsumer.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Branch {
    private String name;
    private String lastCommitSha;
}
