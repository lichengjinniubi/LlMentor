package com.ai.mcpclient.controller;

import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.spec.McpSchema;
import io.modelcontextprotocol.spec.McpSchema.TextContent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/client")
public class McpClientController {

	private final McpSyncClient mcpSyncClient;

	public McpClientController(McpSyncClient mcpSyncClient) {
		this.mcpSyncClient = mcpSyncClient;
	}

	@GetMapping("/servers")
	public String servers() {
		return mcpSyncClient.getClientInfo().name() + " -> " + mcpSyncClient.getServerInfo().name();
	}

	@GetMapping("/weather")
	public String weather(
			@RequestParam(defaultValue = "北京") String city,
			@RequestParam(defaultValue = "2026-11-21") String date,
			@RequestParam(defaultValue = "朝阳区") String district,
			@RequestParam(defaultValue = "望京") String street) {
		Map<String, Object> request = Map.of(
				"city", city,
				"date", date,
				"district", district,
				"street", street);
		Map<String, Object> arguments = Map.of("request", request);

		McpSchema.CallToolRequest callToolRequest = McpSchema.CallToolRequest.builder()
				.name("getWeather")
				.arguments(arguments)
				.build();

		McpSchema.CallToolResult result = mcpSyncClient.callTool(callToolRequest);

		return result.content().stream()
				.filter(TextContent.class::isInstance)
				.map(TextContent.class::cast)
				.map(TextContent::text)
				.findFirst()
				.orElse(result.toString());
	}


	@GetMapping("/getWeather")
	public String getWeather(
			@RequestParam(defaultValue = "北京") String city) {
		Map<String, Object> request = Map.of(
				"city", city,
				"date", "2026-11-21",
				"district", "朝阳区",
				"street", "望京");
		Map<String, Object> arguments = Map.of("request", request);

		McpSchema.CallToolRequest callToolRequest = McpSchema.CallToolRequest.builder()
				.name("queryWeatherByCityAndDate")
				.arguments(arguments)
				.build();

		McpSchema.CallToolResult result = mcpSyncClient.callTool(callToolRequest);

		return result.content().stream()
				.filter(TextContent.class::isInstance)
				.map(TextContent.class::cast)
				.map(TextContent::text)
				.findFirst()
				.orElse(result.toString());
	}

}
