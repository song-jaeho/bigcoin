package com.bigcoin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
//@EnableBatchProcessing // 배치 기능 활성화
@EnableScheduling // 스케쥴링 활성화
public class BigcoinApplication {

	public static void main(String[] args) {
		SpringApplication.run(BigcoinApplication.class, args);
	}

}
