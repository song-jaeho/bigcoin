package com.bigcoin.common.runner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import com.bigcoin.common.util.CommonUtil;
import com.bigcoin.dto.SampleData;
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
		JSONParser parser = new JSONParser();
		JSONObject jsonobj = (JSONObject) parser.parse( jsonResult );
		
		JSONObject jsonobj2 =  (JSONObject)jsonobj.get("data");
				
				
		
		// HashMap 형태로 받기로 수정
		SampleData test = (SampleData) CommonUtil.convertJsonStrToClass(jsonResult, SampleData.class);

	
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
