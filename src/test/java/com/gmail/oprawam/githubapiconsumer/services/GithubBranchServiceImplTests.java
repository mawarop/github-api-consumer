package com.gmail.oprawam.githubapiconsumer.services;

import com.gmail.oprawam.githubapiconsumer.dto.githubdto.GithubOwner;
import com.gmail.oprawam.githubapiconsumer.dto.githubdto.GithubRepo;
import com.gmail.oprawam.githubapiconsumer.exception.NotFoundException;
import com.gmail.oprawam.githubapiconsumer.service.GithubBranchService;
import com.gmail.oprawam.githubapiconsumer.service.GithubBranchServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
//@ContextConfiguration(classes = {WebConfig.class}) todo czemu nie dziala zaciaganie z webconfig webclienta
// todo junit and mockito lifecycle
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class GithubBranchServiceImplTests {

    public static final String USER_NAME = "octokit";
    @Spy
    public WebClient.Builder webClientBuilder;
    GithubBranchService githubBranchService;

    @BeforeEach
    void beforeEach() {
        webClientBuilder = WebClient.builder().baseUrl("https://api.github.com");
        githubBranchService = new GithubBranchServiceImpl(webClientBuilder);

    }

    @Test
    void Should_fetchGithubRepoBranches_Success_When_Request_Ok() {
        final String VALID_BRANCH_URL = "https://api.github.com/repos/octokit/handbook/branches";
        // given
        GithubRepo githubRepo = new GithubRepo("octokit", false, VALID_BRANCH_URL, new GithubOwner(USER_NAME));
        var result = githubBranchService.fetchGithubRepoBranches(githubRepo);
        var branchesList = result.block();
        assert branchesList != null;
        assert !branchesList.isEmpty();
    }

    @Test
    void Should_fetchGithubRepoBranches_Return_Exception_When_NotFound() {
        String RANDOM_STRING = UUID.randomUUID().toString().replaceAll("[_/!@#$%^&*+=;':?<>]", "");
        final String INVALID_BRANCH_URL = "https://api.github.com/repos/octokit" + RANDOM_STRING + "/handbook" + "/branches";

        // given
        NotFoundException notFoundException = new NotFoundException("Branch not found");
        GithubRepo githubRepo = new GithubRepo("octokit", false, INVALID_BRANCH_URL, new GithubOwner(USER_NAME));
        var result = githubBranchService.fetchGithubRepoBranches(githubRepo);
        NotFoundException resultException = assertThrows(NotFoundException.class, result::block);

        assertThat(resultException.getMessage(), equalTo((notFoundException.getMessage())));
    }

}
