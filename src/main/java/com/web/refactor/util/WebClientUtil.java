package com.web.refactor.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

public class WebClientUtil {

	public static WebClient getBaseUrl(final String url) {
		return WebClient.builder()
				.baseUrl(url)
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.clientConnector(
						new ReactorClientHttpConnector(HttpClient.create().followRedirect(true))
				)
				.build()
				.mutate()
				.build();
	}
}
