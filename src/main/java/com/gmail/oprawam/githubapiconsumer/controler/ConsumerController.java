package com.gmail.oprawam.githubapiconsumer.controler;

import com.gmail.oprawam.githubapiconsumer.dto.RepoResponse;
import com.gmail.oprawam.githubapiconsumer.service.RepoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;


@RestController
@RequiredArgsConstructor
public class ConsumerController {
    private final RepoService repoService;

    @GetMapping(value = "api/v1/github/users/{username}/repositories")
    public Flux<RepoResponse> getUserRepositoriesWithoutForks(@PathVariable String username) {

//        if (accept.equals("application/xml")) {
//            throw new NotAcceptableException("Wrong header. Required Accept=application/json");
//        }
        return repoService.getReposWithoutFork(username);
    }
}
