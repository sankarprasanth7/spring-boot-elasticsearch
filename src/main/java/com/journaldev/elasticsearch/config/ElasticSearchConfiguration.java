package com.journaldev.elasticsearch.config;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder.HttpClientConfigCallback;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticSearchConfiguration extends AbstractFactoryBean<RestHighLevelClient> {

	private static final Logger logger = LoggerFactory.getLogger(ElasticSearchConfiguration.class);
	@Value("${spring.data.elasticsearch.cluster-nodes}")
	private String clusterNodes;
	@Value("${spring.data.elasticsearch.cluster-name}")
	private String clusterName;
	private RestHighLevelClient restHighLevelClient;

	@Override
	public void destroy() {
		try {
			if (restHighLevelClient != null) {
				restHighLevelClient.close();
			}
		} catch (final Exception e) {
			logger.error("Error closing ElasticSearch client: ", e);
		}
	}

	@Override
	public Class<RestHighLevelClient> getObjectType() {
		return RestHighLevelClient.class;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

	@Override
	public RestHighLevelClient createInstance() {
		return buildClient();
	}

	private RestHighLevelClient buildClient() {
		try {
			final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
			credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("logos", "Em1u5b9J1*lksqi4hv38MSTH79PkpOv"));
			restHighLevelClient = new RestHighLevelClient(
					RestClient.builder(new HttpHost("es.data.dev.mojoreads.com", 8881, "http"))
							.setHttpClientConfigCallback(new HttpClientConfigCallback() {
								public HttpAsyncClientBuilder customizeHttpClient(
										HttpAsyncClientBuilder httpClientBuilder) {
									return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
								}
							}));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return restHighLevelClient;
	}

}
