package com.gmail.oprawam.githubapiconsumer.config;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor

public class WebRequestFilter implements WebFilter {
    private final DataBufferFactory dataBufferFactory;

    @NonNull
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, @NonNull WebFilterChain chain) {
        if (exchange.getRequest().getHeaders().getAccept().contains(MediaType.APPLICATION_XML)) {
            exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);
            return exchange.getResponse().writeWith(Mono.just(dataBufferFactory.wrap("Wrong Accept header. Should be application/json !!!".getBytes())));
//            chain.filter(exchange).subscribe();
//            return exchange.getResponse().setComplete();
        }
        return chain.filter(exchange);
    }
}
