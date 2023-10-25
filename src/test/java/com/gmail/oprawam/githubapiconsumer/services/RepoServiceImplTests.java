package com.gmail.oprawam.githubapiconsumer.services;

import com.gmail.oprawam.githubapiconsumer.dto.githubdto.GithubBranch;
import com.gmail.oprawam.githubapiconsumer.dto.githubdto.GithubRepo;
import com.gmail.oprawam.githubapiconsumer.exception.NotFoundException;
import com.gmail.oprawam.githubapiconsumer.mapper.RepoResponseMapper;
import com.gmail.oprawam.githubapiconsumer.service.GithubBranchServiceImpl;
import com.gmail.oprawam.githubapiconsumer.service.GithubRepoServiceImpl;
import com.gmail.oprawam.githubapiconsumer.service.RepoServiceImpl;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class RepoServiceImplTests {
    public static final String REPO_NOT_FOUND = "Repo not found";
    public static final String BRANCH_NOT_FOUND = "Branch not found";
    public static final int LIST_SIZE = 10;

    /*
     TODO: Cucumber-JVM, kafka, redis(cache), roadmap medium
     TODO: @see C:\Users\opraw\Desktop\java_roadmap_2023.webp
    */

    @Mock
    WebTestClient webTestClient;
    @Mock
    GithubRepoServiceImpl githubRepoService;
    @Mock
    GithubBranchServiceImpl githubBranchService;

    //    @InjectMocks
    RepoServiceImpl repoService;
    RepoResponseMapper repoResponseMapper;

    @BeforeEach
    void beforeEach() {
        repoResponseMapper = new RepoResponseMapper();

//        webTestClient = mock(WebTestClient.class);
//        githubRepoService = mock(GithubRepoServiceImpl.class);
//        githubBranchService = mock(GithubBranchServiceImpl.class);

        repoService = new RepoServiceImpl(githubRepoService, githubBranchService, repoResponseMapper);
    }

    @Test
    void Should_getReposWithoutFork_Ok_When_UsernameFound() {
        // given
        var githubRepoList = Instancio.ofList(GithubRepo.class).size(LIST_SIZE).create();
        var githubRepoListFlux = Flux.fromIterable(githubRepoList);
        var githubBranchList = Instancio.ofList(GithubBranch.class).size(LIST_SIZE).create();
        var monoGithubBranchList = Mono.just(githubBranchList);

        given(githubRepoService.fetchGithubRepos(anyString())).willReturn(githubRepoListFlux);
        given(githubBranchService.fetchGithubRepoBranches(any(GithubRepo.class))).willReturn(monoGithubBranchList);
        var expected = githubRepoList.stream().filter(repo -> !repo.fork()).map(githubRepo -> repoResponseMapper.githubRepoAndGithubBranchesToRepoResponse(githubRepo, githubBranchList)).toList();
        // when
        var result = repoService.getReposWithoutFork(anyString());
        // then
        StepVerifier.create(result).expectNextSequence(expected).expectComplete().verify();
    }

    @Test
    void Should_getReposWithoutFork_Send_Error_When_UsernameNotFound() {
        // given
        given((githubRepoService.fetchGithubRepos(anyString()))).willReturn(Flux.error(new NotFoundException(REPO_NOT_FOUND)));
        // when
        var result = repoService.getReposWithoutFork(anyString());
        // then
        StepVerifier.create(result).expectErrorMatches((throwable) -> throwable instanceof NotFoundException && throwable.getMessage().equals(REPO_NOT_FOUND)).verify();
    }

    @Test
    void Should_getReposWithoutFork_Send_Error_When_BranchesNotFound() {
        // given
        var githubRepoList = Instancio.ofList(GithubRepo.class).size(LIST_SIZE).create();
        var githubRepoListFlux = Flux.fromIterable(githubRepoList);

        given(githubRepoService.fetchGithubRepos(anyString())).willReturn(githubRepoListFlux);
        given(githubBranchService.fetchGithubRepoBranches(any(GithubRepo.class))).willReturn(Mono.error(new NotFoundException(BRANCH_NOT_FOUND)));

        // when
        var result = repoService.getReposWithoutFork(anyString());

        // then
        StepVerifier.create(result).expectErrorMatches((throwable) -> throwable instanceof NotFoundException && throwable.getMessage().equals(BRANCH_NOT_FOUND)).verify();

    }

}
