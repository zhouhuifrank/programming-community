package com.frankzhou.community;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableTransactionManagement
@EnableSwagger2
@EnableWebMvc
@EnableAspectJAutoProxy(exposeProxy = true)
@MapperScan("com.frankzhou.community.mapper")
@SpringBootApplication
public class CommunityApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommunityApplication.class, args);
		System.out.println("================================================");
		System.out.println("======Programming Community Start Succeed=======");
		System.out.println("=================================================");
	}

}
