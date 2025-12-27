package org.example.sijalsystem.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;


@Configuration
public class RestTemplateConfig {


    @Bean
    public RestTemplate restTemplate(N8nConfig n8nConfig) {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(n8nConfig.getTimeout());
        factory.setReadTimeout(n8nConfig.getTimeout());
        return new RestTemplate(factory);
        }
    }

