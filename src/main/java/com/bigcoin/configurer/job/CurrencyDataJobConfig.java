package com.bigcoin.configurer.job;

//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//@Slf4j
//@RequiredArgsConstructor // 생성자 DI
//@Configuration
public class CurrencyDataJobConfig {

//	private final JobBuilderFactory jobBuilderFactory; // 생성자 DI
//	private final StepBuilderFactory stepBuilderFactory; // 생성자 DI
//	
//	// tasklet 하나와 Reader & Processor & Writer 한 묶음이 같은 레벨
//	// 하나의 Job안에 여러 개의 Step이 존재
//	
//	@Bean
//	public Job CurrencyDataJob() {
//		return jobBuilderFactory.get("currencyDataJob") // Batch Job을 생성함
//				.start(readCurrency())
//				.build();
//	}
//	
//	@Bean
//	public Step readCurrency() {
//		return stepBuilderFactory.get("readCurrency") // Batch Step을 생성함
//				.tasklet((contribution, chunkContext) -> { // Step 안에서 수행될 기능을 명시
//					log.info(">>>>> readCurrency start");
//					return RepeatStatus.FINISHED;
//				})
//				.build();
//	}
}
