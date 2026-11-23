package com.ai.mcpclient.service;

import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.spec.McpSchema;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class McpClientService {

	private final McpSyncClient mcpSyncClient;
	private final SyncMcpToolCallbackProvider toolCallbackProvider;

	public McpClientService(McpSyncClient mcpSyncClient, SyncMcpToolCallbackProvider toolCallbackProvider) {
		this.mcpSyncClient = mcpSyncClient;
		this.toolCallbackProvider = toolCallbackProvider;
	}

	public List<String> listWeatherTools() {
		return Arrays.stream(toolCallbackProvider.getToolCallbacks())
				.map(callback -> callback.getToolDefinition().name())
				.toList();
	}

	public McpSchema.CallToolResult callTool(String toolName, Map<String, Object> arguments) {
		McpSchema.CallToolRequest request = McpSchema.CallToolRequest.builder()
				.name(toolName)
				.arguments(arguments)
				.build();
		return mcpSyncClient.callTool(request);
	}

	public String serverDescription() {
		return mcpSyncClient.getClientInfo().name() + " -> " + mcpSyncClient.getServerInfo().name();
	}

}
