package com.ai.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.LinkedList;

@SpringBootApplication
public class ServiceApplication {


	private static final String API_KEY = "sk-051cf0bbeb034a33b78b63f2de342ccf";
	private static final String API_URL = "https://dashscope.aliyuncs.com/compatible-mode/v1/chat/completions";

	public static void main(String[] args) throws IOException, InterruptedException {
		LinkedList<Object> objects = new LinkedList<>();
		SpringApplication.run(ServiceApplication.class, args);

//		String requestBody = """
//                {
//                    "model": "qwen-plus",
//                    "messages": [
//                        {
//                            "role": "system",
//                            "content": "You are a helpful assistant."
//                        },
//                        {
//                            "role": "user",
//                            "content": "你好，介绍下JAVA？"
//                        }
//                    ],
//                    "stream": true
//                }
//                """;
//
//		HttpClient client = HttpClient.newHttpClient();
//
//		HttpRequest request = HttpRequest.newBuilder()
//				.uri(URI.create(API_URL))
//				.header("Content-Type", "application/json")
//				.header("Authorization", "Bearer " + API_KEY)
//				.header("X-DashScope-SSE", "enable")
//				.POST(HttpRequest.BodyPublishers.ofString(requestBody))
//				.build();
//
//		HttpResponse<java.util.stream.Stream<String>> response = client.send(
//				request, HttpResponse.BodyHandlers.ofLines());
//
//		response.body().forEach(line -> {
//			if (line.startsWith("data:")) {
//				String data = line.substring(5).trim();
//				if ("[DONE]".equals(data)) {
//					System.out.println("\n[完成]");
//					return;
//				}
//				try {
//					int contentStart = data.indexOf("\"content\":\"");
//					if (contentStart != -1) {
//						contentStart += 11;
//						int contentEnd = data.indexOf("\"", contentStart);
//						if (contentEnd != -1) {
//							String content = data.substring(contentStart, contentEnd);
//							System.out.print(content);
//						}
//					}
//				} catch (Exception e) {
//					// 非标准数据行，跳过
//				}
//			}
//		});
	}

}
