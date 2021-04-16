package com.bigcoin.common.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.bigcoin.common.util.CommonUtil;
import com.bigcoin.dao.RedisDAO;
import com.bigcoin.dto.BithumbData;
import com.bigcoin.service.CurrencyService;

@Order(value = 1) // Runner의 순서 지정
@Component
public class CurrencyDataRunner implements ApplicationRunner {
	
	private CurrencyService currencyService;
	private RedisDAO redisDAO;
	
	@Autowired
	public CurrencyDataRunner(CurrencyService currencyService, RedisDAO redisDAO) {
		this.currencyService = currencyService;
		this.redisDAO = redisDAO;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		redisDAO.deleteAll();
		String jsonResult = currencyService.getAllCurrency();
		
		BithumbData data = CommonUtil.convertJsonToBithumbData(jsonResult);
		redisDAO.vopSave("date", data.getDate());
		
		data.getData().stream()
			.forEach(currencyData -> {
				redisDAO.vopSave(currencyData.getTicker(), currencyData);
			});
		
		// 테스트용!!!!
//		System.out.println(redisDAO.vopGet("ETH"));
//		System.out.println(redisDAO.vopGet("QTUM"));
//		System.out.println(redisDAO.vopGet("date"));
	}

}
