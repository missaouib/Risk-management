package com.smartosc.fintech.risk.rulemanagement.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "url")
@Getter
@Setter
public class UrlStorageConfig {
    private String ruleengine;
}
