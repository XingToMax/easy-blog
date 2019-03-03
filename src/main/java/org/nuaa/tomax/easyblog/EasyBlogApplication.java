package org.nuaa.tomax.easyblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author tomax
 */
@SpringBootApplication
@EnableCaching
public class EasyBlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(EasyBlogApplication.class, args);
	}
}
