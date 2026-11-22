# mcp

Spring AI MCP Server 实践模块，使用 Streamable HTTP 对外暴露天气查询工具。

## 技术栈

- Java 17
- Spring Boot 3.4.5
- Spring AI 1.1.0
- MCP Streamable HTTP

## 启动流程

先确认旧的 `8004` / `8443` 进程已经停止，避免端口冲突。

```bash
cd /Users/momo/AILearn/LLMentor/LlMentor/service

JAVA_HOME=/Users/momo/Library/Java/JavaVirtualMachines/ms-21.0.11/Contents/Home \
  ./mvnw -s .mvn-aliyun-settings.xml -pl ai-framework/mcp spring-boot:run
```

启动成功后服务地址：

```text
https://127.0.0.1:8443/api/mcp
```

当前使用自签名测试证书 [keystore.p12](src/main/resources/keystore.p12)，密码为 `123456`。因此直接用 curl 调试时需要加 `-k`；生产环境应替换为合法证书并通过 Nginx 做 TLS 终结。

## 工具列表

- `queryWeatherByCityAndDate`
- `getWeather`

## Postman / curl 调试流程

### 1. 初始化并获取会话

请求头：

```text
Content-Type: application/json
Accept: application/json, text/event-stream
```

Body：

```json
{
  "jsonrpc": "2.0",
  "id": 1,
  "method": "initialize",
  "params": {
    "protocolVersion": "2025-06-18",
    "capabilities": {},
    "clientInfo": {
      "name": "postman",
      "version": "1.0.0"
    }
  }
}
```

curl：

```bash
curl -k -i -X POST https://127.0.0.1:8443/api/mcp \
  -H 'Content-Type: application/json' \
  -H 'Accept: application/json, text/event-stream' \
  -d '{
    "jsonrpc": "2.0",
    "id": 1,
    "method": "initialize",
    "params": {
      "protocolVersion": "2025-06-18",
      "capabilities": {},
      "clientInfo": {"name": "postman", "version": "1.0.0"}
    }
  }'
```

响应头中会返回：

```text
Mcp-Session-Id: <session-id>
```

复制该值，后续请求必须带上。

### 2. 通知服务端初始化完成

Header：

```text
Mcp-Session-Id: <session-id>
```

Body：

```json
{
  "jsonrpc": "2.0",
  "method": "notifications/initialized"
}
```

预期返回 `202`，响应体为空。

### 3. 查询工具列表

Body：

```json
{
  "jsonrpc": "2.0",
  "id": 2,
  "method": "tools/list"
}
```

响应可能是 SSE，重点读取 `data:` 后面的 JSON。

### 4. 调用 queryWeatherByCityAndDate

Body：

```json
{
  "jsonrpc": "2.0",
  "id": 3,
  "method": "tools/call",
  "params": {
    "name": "queryWeatherByCityAndDate",
    "arguments": {
      "request": {
        "city": "北京",
        "date": "2026-11-21",
        "district": "朝阳区",
        "street": "望京"
      }
    }
  }
}
```

### 5. 调用 getWeather

Body：

```json
{
  "jsonrpc": "2.0",
  "id": 4,
  "method": "tools/call",
  "params": {
    "name": "getWeather",
    "arguments": {
      "request": {
        "city": "北京",
        "date": "2026-11-21",
        "district": "朝阳区",
        "street": "望京"
      }
    }
  }
}
```
