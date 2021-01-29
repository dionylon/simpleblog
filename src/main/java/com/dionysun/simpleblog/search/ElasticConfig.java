package com.dionysun.simpleblog.search;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

@Configuration
public class ElasticConfig extends AbstractElasticsearchConfiguration {
    public static final String INDEX_NAME = "article";
    @Override
    public RestHighLevelClient elasticsearchClient() {
        return RestClients.create(ClientConfiguration.localhost()).rest();
    }
    // 不需要手动声明@Bean
}
