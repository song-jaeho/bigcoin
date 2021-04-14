package com.bigcoin.common.runner;

import java.util.Iterator;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import com.bigcoin.dto.CurrencyData;
import com.bigcoin.service.CurrencyService;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


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
		
		//String jsonResult = currencyService.getCurrency("https://api.bithumb.com/public/ticker/BTC_KRW");
		String jsonResult = currencyService.getAllCurrency();
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		JsonNode node = objectMapper.readTree(jsonResult);
		
		// status가 "0000"이 아니면 실패임 
		if (node.get("status").asText().equals("0000")) {
			
			// 노드를 data부로 옮긴다.
			node = node.get("data");

			Iterator<Entry<String, JsonNode>> it = node.fields();
			while(it.hasNext()) {
				Entry<String, JsonNode> entry = it.next();
				
				if (entry.getKey().equals("date")) {
					vop.set("date", entry.getValue().asText());
					continue; // 혹은 break; (맨마지막이니까)
				}
				
				CurrencyData currencyData = objectMapper.readValue(entry.getValue().toString(), CurrencyData.class);
				currencyData.setTicker(entry.getKey());
				
				vop.set(currencyData.getTicker(), currencyData);
			}
		} else {
			// TODO 실패하면 어쩌지..?
		}
		
		
		// 테스트용!!!!
		System.out.println(vop.get("ETH"));
		System.out.println(vop.get("QTUM"));
		System.out.println(vop.get("date"));
	}

}
