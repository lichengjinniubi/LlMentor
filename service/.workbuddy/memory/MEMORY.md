# LlMentor/service 项目记忆

## 工程结构
- Maven 多模块（用户偏好扁平分组式）：`service`（根父 pom，packaging=pom）→ modules 用嵌套路径 `ai-framework/springai`
- `ai-framework/` 只是普通文件夹不放 pom（对齐用户 bigchun 项目的目录风格），以后按框架分子文件夹：springai、langchain4j、agentscope、function-call 等，每个子文件夹是一个独立模块，parent 直指根 pom（relativePath ../../pom.xml）
- springai 模块：原 ai-service 代码（包名 com.ai.service，端口 8080），Spring Boot 3.4.5 / Java 17 / Spring AI Alibaba 1.1.0.0（dashscope）/ Spring AI 1.1.0 chat-memory-jdbc / MySQL

## 构建注意事项（重要）
- shell 默认 JAVA_HOME 是 JDK 8，命令行构建前需：`export JAVA_HOME=/Users/momo/Library/Java/JavaVirtualMachines/ms-21.0.11/Contents/Home`
- `~/.m2/settings.xml` 镜像全指向陌陌内网 Nexus（maven.wemomo.com），需 VPN；无 VPN 时用 `./mvnw -s .mvn-aliyun-settings.xml`（阿里云镜像，文件在 service 根目录）
- spring-ai-alibaba-bom 1.1.0.0 不管理 `spring-ai-alibaba-starter-dashscope` 坐标，版本已显式声明在父 pom
