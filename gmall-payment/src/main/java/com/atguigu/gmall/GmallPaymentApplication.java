package com.atguigu.gmall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

import java.io.BufferedInputStream;
import java.io.InputStream;

@SpringBootApplication
@MapperScan(basePackages = "com.atguigu.gmall.payment.mapper")
public class GmallPaymentApplication {

	public static void main(String[] args) {
		SpringApplication.run(GmallPaymentApplication.class, args);
	}
}
