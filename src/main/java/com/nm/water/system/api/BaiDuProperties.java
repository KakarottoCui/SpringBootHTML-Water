package com.nm.water.system.api;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 百度智能AI
 * @author
 */
@Data
@ConfigurationProperties(prefix = "bai-du")
public class BaiDuProperties {

    private String appId;
    private String apiKey;
    private String accessKeySecret;

}