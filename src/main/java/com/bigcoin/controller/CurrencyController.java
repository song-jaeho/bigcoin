package com.bigcoin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bigcoin.constants.RestApiConstants;
import com.bigcoin.service.CurrencyService;

@RestController
@RequestMapping(value = {"/currency"})
public class CurrencyController {

	private CurrencyService currencyService;
	
	@Autowired
	public CurrencyController(CurrencyService currencyService) {
		this.currencyService = currencyService;
	}
	
	@RequestMapping(value = {"/all"})
	public ResponseEntity<String> getAllCurrency() {
		ResponseEntity<String> response = null; 
		
		try {
			String jsonResult = currencyService.getAllCurrency(RestApiConstants.BITHUMB_ALL_KRW);
			response = new ResponseEntity<String>(jsonResult, HttpStatus.OK);
		} catch (Exception e) {
			response = new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
}
