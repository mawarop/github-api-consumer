package com.gmail.oprawam.githubapiconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.blockhound.BlockHound;

@SpringBootApplication
public class GithubApiConsumerApplication {

    public static void main(String[] args) {
        BlockHound.install();

        SpringApplication.run(GithubApiConsumerApplication.class, args);
    }

}
