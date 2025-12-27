package org.example.sijalsystem.Config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "n8n.webhook")
@Data
public class N8nConfig {
    private String url;
    private int timeout = 30000;
}

