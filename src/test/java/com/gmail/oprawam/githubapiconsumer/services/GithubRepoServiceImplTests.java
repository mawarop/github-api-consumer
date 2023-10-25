package com.gmail.oprawam.githubapiconsumer.services;

import com.gmail.oprawam.githubapiconsumer.dto.githubdto.GithubRepo;
import com.gmail.oprawam.githubapiconsumer.exception.NotFoundException;
import com.gmail.oprawam.githubapiconsumer.service.GithubRepoService;
import com.gmail.oprawam.githubapiconsumer.service.GithubRepoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
//@ContextConfiguration(classes = {WebConfig.class})
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class GithubRepoServiceImplTests {
    public static final String USER_NAME = "octokit";
    @Spy
    public WebClient.Builder webClientBuilderSpy;
    //    @Mock
    GithubRepoService githubRepoService;

    @BeforeEach
    void beforeEach() {
        webClientBuilderSpy = spy(WebClient.builder().baseUrl("https://api.github.com"));
//        webClientBuilder = WebClient.builder().baseUrl("https://api.github.com");
        githubRepoService = new GithubRepoServiceImpl(webClientBuilderSpy);

//        webTestClient =
//                WebTestClient.bindToController(consumerController).build();
    }

    @Test
    void Should_fetchGithubRepos_When_Request_Ok() {
        ArgumentCaptor<String> urlCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> urlArgCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<MediaType> mediaTypeArgumentCaptor = ArgumentCaptor.forClass(MediaType.class);

        WebClient webClient = Mockito.spy(webClientBuilderSpy.build());
        WebClient.RequestHeadersUriSpec getRequestHeaderUriSpec = Mockito.spy(webClient.get());

        given(webClientBuilderSpy.build()).willReturn(webClient);
        given(webClient.get()).willReturn(getRequestHeaderUriSpec);

//
//       given(webClient.build().get()).willReturn((WebClient.RequestHeadersUriSpec)spy(spy(webClient.build()).get()));
//
//        WebClient webClient = webClientBuilderSpy.build();
//        WebClient.RequestHeadersUriSpec<?> getSpecSpy = spy(spy(webClientBuilderSpy.build()).get());
//        given(webClient.get()).willReturn((WebClient.RequestHeadersUriSpec)getSpecSpy);
//        given(webClient.build().get()).willReturn(we);
//          var realWebClientBuilder = webClient;
//          var webClientBuilderSpy = spy(webClient);
//        doReturn(webClient.build().get()).when(webClientBuilderMock.build()).get();

//        doReturn(getSpec).when(webClient.build().get());
//        given(webClient.build().get()).willReturn(getRequestHeaderUriSpec);

//        given(webClient.build()).willReturn(spy(webClient.build()));
        Flux<GithubRepo> result = githubRepoService.fetchGithubRepos(USER_NAME);
        verify(getRequestHeaderUriSpec).uri(urlCaptor.capture(), urlArgCaptor.capture());
//        verify(getRequestHeaderUriSpec, atLeast(1)).uri(urlCaptor.capture(), urlArgCaptor.capture());
        verify(getRequestHeaderUriSpec.uri(eq("/users/{username}/repos"), eq(USER_NAME))).accept(mediaTypeArgumentCaptor.capture());
//        verify(webClient.build().get()).uri(urlCaptor.capture(), urlArgCaptor.capture());
        assertThat(urlCaptor.getValue(), equalTo("/users/{username}/repos"));
        assertThat(urlArgCaptor.getValue(), equalTo(USER_NAME));
        assertThat(mediaTypeArgumentCaptor.getValue(), equalTo(MediaType.APPLICATION_JSON));

        List<GithubRepo> githubRepoList = result.collectList().block();
        assertThat(githubRepoList, not(empty()));
//        assertThat(githubRepoList.);
        assertTrue(githubRepoList.stream().allMatch(item -> USER_NAME.equals(item.owner().login())));
//        System.out.println(urlCaptor.getValue() + " " + urlArgCaptor.getValue() + " " + mediaTypeArgumentCaptor.getValue().toString());
//        System.out.println(mediaTypeArgumentCaptor.capture());

    }

    @Test
    void Should_fetchGithubRepos_Return_NotFound_Error_When_Request_Repo_NotFound() {
        NotFoundException notFoundException = new NotFoundException("Repo not found");
        final String randomUserName = UUID.randomUUID().toString().replaceAll("_", "") + USER_NAME;

        var result = githubRepoService.fetchGithubRepos(randomUserName);
        NotFoundException exception = assertThrows(NotFoundException.class, () -> result.collectList().block());
        assertThat(exception.getMessage(), equalTo(notFoundException.getMessage()));

    }
}
