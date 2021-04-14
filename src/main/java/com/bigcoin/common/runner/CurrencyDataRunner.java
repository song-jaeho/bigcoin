package com.bigcoin.common.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import com.bigcoin.service.CurrencyService;

@Order(value = 1) // Runner의 순서 지정
@Component
public class CurrencyDataRunner implements ApplicationRunner {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@Autowired
	private CurrencyService currencyService;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		ValueOperations<String, Object> vop = redisTemplate.opsForValue();
		
		String jsonResult = currencyService.getAllCurrency("https://api.bithumb.com/public/ticker/BTC_KRW");
		
		/*
		SampleData data = new SampleData();
		data.setCoinName("BTC");
		data.setBody(jsonResult);
		*/
		
		vop.set("BTC", jsonResult);
		
		System.out.println("Runner Start ======================");
		System.out.println(vop.get("BTC"));
	}
}
