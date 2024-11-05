package org.moonzhou.springbootcommandline;

import org.moonzhou.springbootcommandline.service.CommandLineService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBootCommandlineApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootCommandlineApplication.class, args);
	}

	// 使用CommandLineRunner来执行一些启动后的逻辑
	@Bean
	public CommandLineRunner commandLineRunner(CommandLineService commandLineService) {
		return args -> {
			// 打印服务类的欢迎信息
			System.out.println(commandLineService.welcome());
		};
	}
}
