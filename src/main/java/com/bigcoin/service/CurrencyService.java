package com.bigcoin.service;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.bigcoin.common.util.RestApiUtil;

@Service
public class CurrencyService {

	public String getAllCurrency(String paramUrl) throws IOException {
		return RestApiUtil.callGet(paramUrl);
	}
}
