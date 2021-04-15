package com.bigcoin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableBatchProcessing // 배치 기능 활성화
public class BigcoinApplication {

	public static void main(String[] args) {
		SpringApplication.run(BigcoinApplication.class, args);
	}

}
