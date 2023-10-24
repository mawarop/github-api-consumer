package com.gmail.oprawam.githubapiconsumer.controllers;

import com.gmail.oprawam.githubapiconsumer.config.WebConfig;
import com.gmail.oprawam.githubapiconsumer.controler.ConsumerController;
import com.gmail.oprawam.githubapiconsumer.dto.RepoResponse;
import com.gmail.oprawam.githubapiconsumer.exception.NotFoundException;
import com.gmail.oprawam.githubapiconsumer.service.RepoService;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.UUID;

import static org.instancio.Select.field;
import static org.mockito.BDDMockito.given;

@WebFluxTest(ConsumerController.class)
@Import(WebConfig.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class ConsumerControllerTests {

    WebTestClient webTestClient;
    ConsumerController consumerController;
    @MockBean
    RepoService repoService;

    @BeforeEach
    void beforeEach() {
        consumerController = new ConsumerController(repoService);
        webTestClient =
                WebTestClient.bindToController(consumerController).build();
    }

    @Test
    void Should_GetUserRepositoriesWithoutForks_Ok_When_Valid_Query_Parameters() {
        // given
        String USERNAME = "octokit";
        String URI = "/api/v1/github/users/" + USERNAME + "/repositories";
        int listSize = 10;
        List<RepoResponse> repoResponseList = Instancio.ofList(RepoResponse.class).size(listSize).set(field(RepoResponse::ownerLogin), USERNAME).create();
        Flux<RepoResponse> repoResponseFlux = Flux.fromIterable(repoResponseList);
        given(repoService.getReposWithoutFork(USERNAME)).willReturn(repoResponseFlux);

        // when && then
        webTestClient.get()
                .uri(URI)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(RepoResponse.class).hasSize(listSize).contains(repoResponseList.toArray(new RepoResponse[0]));

    }

    @Test
    void Should_GetUserRepositoriesWithoutForks_Fail_When_User_Not_Exist() {
        // given
        String USERNAME = UUID.randomUUID().toString().replaceAll("_", "");
        String URI = "/api/v1/github/users/" + USERNAME + "/repositories";
        int listSize = 10;
        List<RepoResponse> repoResponseList = Instancio.ofList(RepoResponse.class).size(listSize).set(field(RepoResponse::ownerLogin), USERNAME).create();
        Flux<RepoResponse> repoResponseFlux = Flux.fromIterable(repoResponseList);
        given(repoService.getReposWithoutFork(USERNAME)).willThrow(new NotFoundException("Repo not found"));

        // when && then
        webTestClient.get()
                .uri(URI)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound();
    }

}
