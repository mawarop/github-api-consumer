package com.gmail.oprawam.githubapiconsumer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Branch {
    private String name;
    private String lastCommitSha;
}
