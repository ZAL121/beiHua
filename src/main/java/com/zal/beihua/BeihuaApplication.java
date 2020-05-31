package com.zal.beihua;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@MapperScan("com.zal.**.mapper")
public class BeihuaApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeihuaApplication.class, args);
	}

}
