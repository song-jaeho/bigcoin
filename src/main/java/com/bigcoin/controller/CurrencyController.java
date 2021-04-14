package com.bigcoin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bigcoin.common.util.CommonUtil;
import com.bigcoin.dto.CurrencyData;
import com.bigcoin.service.CurrencyService;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping(value = {"/currency"})
public class CurrencyController {

	@Autowired
	private CurrencyService currencyService;

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	
	@RequestMapping(value = {"/all"})
	public ResponseEntity<String> getAllCurrency() {
		ResponseEntity<String> response = null; 
		
		try {
			String jsonResult = currencyService.getAllCurrency();
			response = new ResponseEntity<String>(jsonResult, HttpStatus.OK);
		} catch (Exception e) {
			response = new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	
	
	@RequestMapping(value = {"/test"})
	public ResponseEntity<String> test() throws JsonProcessingException {
		ResponseEntity<String> response = null; 
		
		ValueOperations<String, Object> vop = redisTemplate.opsForValue();
		CurrencyData currencyData = (CurrencyData) vop.get("ETH");
		
		response = new ResponseEntity<String>(CommonUtil.makeJsonString(currencyData), HttpStatus.OK);
		
		return response;
	}
}
