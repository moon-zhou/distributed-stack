package org.moonzhou.springbootnative;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot Native 示例应用主类
 * 
 * 本应用演示了如何使用Spring Boot 3和GraalVM构建原生镜像应用。
 * 原生镜像相比传统的JVM运行方式具有启动快、内存占用少等优势。
 * 
 * @author moonzhou
 */
@SpringBootApplication
public class SpringBootNativeApplication {

	/**
	 * 应用程序入口点
	 * 
	 * @param args 命令行参数
	 */
	public static void main(String[] args) {
		SpringApplication.run(SpringBootNativeApplication.class, args);
	}

}