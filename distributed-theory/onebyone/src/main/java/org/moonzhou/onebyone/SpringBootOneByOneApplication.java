package org.moonzhou.onebyone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author moonzhou
 */
@EnableAspectJAutoProxy
@SpringBootApplication
public class SpringBootOneByOneApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootOneByOneApplication.class, args);
	}

}
