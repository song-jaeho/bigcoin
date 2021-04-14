package com.bigcoin.service;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.bigcoin.common.util.RestApiUtil;
import com.bigcoin.constants.RestApiConstants;

@Service
public class CurrencyService {

	public String getAllCurrency() throws IOException {
		return RestApiUtil.callGet(RestApiConstants.BITHUMB_ALL_KRW);
	}
	
	public String getCurrency(String paramUrl) throws IOException {
		return RestApiUtil.callGet(paramUrl);
	}
}
