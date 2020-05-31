package com.zal.beihua;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zal.beihua.*.mapper")
public class BeihuaApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeihuaApplication.class, args);
	}

}
