# mcp-client

Spring AI MCP Client 实践模块，通过 HTTPS Streamable HTTP 调用 `mcp` 模块暴露的天气查询工具。

## 技术栈

- Java 17
- Spring Boot 3.4.5
- Spring AI 1.1.0
- MCP Streamable HTTP

## 启动流程

先启动 `ai-framework/mcp` 服务，并确认本地 `8005` 端口没有旧进程。

```bash
cd /Users/momo/AILearn/LLMentor/LlMentor/service

# 1. 启动 MCP Server：HTTPS 8443
JAVA_HOME=/Users/momo/Library/Java/JavaVirtualMachines/ms-21.0.11/Contents/Home \
  ./mvnw -s .mvn-aliyun-settings.xml -pl ai-framework/mcp spring-boot:run

# 2. 启动 MCP Client：HTTP 8005
JAVA_HOME=/Users/momo/Library/Java/JavaVirtualMachines/ms-21.0.11/Contents/Home \
  ./mvnw -s .mvn-aliyun-settings.xml -pl ai-framework/mcp-client spring-boot:run
```

客户端服务地址：

```text
http://127.0.0.1:8005
```

当前客户端通过 [McpClientConfiguration](src/main/java/com/ai/mcpclient/config/McpClientConfiguration.java) 手动构建 `HttpClientStreamableHttpTransport`，本地测试环境信任所有证书。生产环境应改为加载受信任 CA 证书并启用主机名校验。

## 接口

当前客户端只读取 MCP Server 中名称以 `weather` 开头的工具。

### 查看当前 MCP 连接

```bash
curl http://127.0.0.1:8005/client/servers
```

返回示例：

```text
streamable-client -> streamable-mcp-server
```

### 查看已过滤的工具列表

```bash
curl http://127.0.0.1:8005/client/tools
```

返回示例：

```json
["weatherResponmse"]
```

### 调用 queryWeatherByCityAndDate

```bash
curl -G http://127.0.0.1:8005/client/weather \
  --data-urlencode 'city=北京' \
  --data-urlencode 'date=2026-11-21' \
  --data-urlencode 'district=朝阳区' \
  --data-urlencode 'street=望京'
```

返回示例：

```json
{"city":"北京","date":"2026-11-21","district":"朝阳区","street":"望京","condition":"晴朗，有微风","temperatureCelsius":18.4}
```

### 调用 getWeather

```bash
curl -G http://127.0.0.1:8005/client/getWeather \
  --data-urlencode 'city=北京'
```
