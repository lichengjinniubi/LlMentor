package com.ai.mcpclient.config;

import io.modelcontextprotocol.client.McpClient;
import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.client.transport.HttpClientStreamableHttpTransport;
import io.modelcontextprotocol.spec.McpSchema;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.net.http.HttpClient;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.time.Duration;

@Configuration
public class McpClientConfiguration {

	@Bean
	public McpSyncClient streamableMcpClient() throws Exception {
		TrustManager[] trustAllCerts = new TrustManager[] {
				new X509TrustManager() {
					@Override
					public X509Certificate[] getAcceptedIssuers() {
						return new X509Certificate[0];
					}

					@Override
					public void checkClientTrusted(X509Certificate[] certs, String authType) {
					}

					@Override
					public void checkServerTrusted(X509Certificate[] certs, String authType) {
					}
				}
		};

		SSLContext sslContext = SSLContext.getInstance("TLS");
		sslContext.init(null, trustAllCerts, new SecureRandom());

		SSLParameters sslParameters = new SSLParameters();
		sslParameters.setEndpointIdentificationAlgorithm(null);

		HttpClient.Builder httpClient = HttpClient.newBuilder()
				.connectTimeout(Duration.ofSeconds(30))
				.sslContext(sslContext)
				.sslParameters(sslParameters);

		HttpClientStreamableHttpTransport transport = HttpClientStreamableHttpTransport.builder("https://127.0.0.1:8443")
				.endpoint("/api/mcp")
				.clientBuilder(httpClient)
				.build();

		McpSyncClient client = McpClient.sync(transport)
				.clientInfo(new McpSchema.Implementation("streamable-client", "1.0"))
				.requestTimeout(Duration.ofSeconds(10))
				.build();

		client.initialize();
		return client;
	}

}
