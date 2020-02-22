package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Service
public class RestClient {

    @Autowired
    RestTemplate restTemplate;

    @Value("${service.uri}")
    String serviceUri;

    private static final Logger log = LoggerFactory.getLogger(RestClient.class);

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        builder.setReadTimeout(Duration.ofSeconds(3));
        builder.setConnectTimeout(Duration.ofSeconds(3));
        return builder.build();
    }

    public String checkService() {
        String s = restTemplate.getForObject(serviceUri, String.class);
        log.info("Data received - {}", s);
        return s;
    }

    //	@Scheduled(fixedDelay = 1000, initialDelay = 5000)
    public void test() {
        try {
            String s = restTemplate.getForObject(serviceUri, String.class);
            log.info("Data received - {}", s);
        } catch (Exception e) {
            log.error("Rest call failed with msg {}", e.getMessage());
        }
    }
}
