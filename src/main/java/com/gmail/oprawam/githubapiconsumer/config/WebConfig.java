package com.gmail.oprawam.githubapiconsumer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.web.reactive.config.DelegatingWebFluxConfiguration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableWebFlux
public class WebConfig extends DelegatingWebFluxConfiguration {
    //    @Bean
//    WebExceptionHandler webExceptionHandler(){
//        WebExceptionHandler webExceptionHandler = new ResponseStatusExceptionHandler()
//    }
    @Bean
    DataBufferFactory dataBufferFactory() {
        return new DefaultDataBufferFactory();
    }

    @Bean
    WebClient.Builder webClient() {
        return WebClient.builder().baseUrl("https://api.github.com");
    }
}
