package com.bigcoin.configurer.job;

import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.bigcoin.common.util.CommonUtil;
import com.bigcoin.dao.RedisDAO;
import com.bigcoin.dto.BithumbData;
import com.bigcoin.dto.CurrencyData;
import com.bigcoin.handler.WebSocketHandler;
import com.bigcoin.service.CurrencyService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CurrencyDataScheduleTask {

	private RedisDAO redisDAO;
	private CurrencyService currencyService;
	
	@Autowired
	public CurrencyDataScheduleTask(RedisDAO redisDAO, CurrencyService currencyService) {
		this.redisDAO = redisDAO;
		this.currencyService = currencyService;
	}
	
	@Scheduled(fixedDelay = 2000, initialDelay = 2000) // 작업이 끝나고 2초뒤에 다시 시작, 프로그램 시작시 2초 딜레이
	public void refreshSockets() {
		
		try {
			String jsonResult = currencyService.getAllCurrency();
			BithumbData data = CommonUtil.convertJsonToBithumbData(jsonResult);
			
			for(CurrencyData cData : data.getData()) {
				CurrencyData rData = (CurrencyData) redisDAO.vopGet(cData.getTicker());
				if (rData != null && !cData.getClosingPrice().equals(rData.getClosingPrice())) {
					
					redisDAO.vopSave(cData.getTicker(), cData);
					websocketSendMessage(CommonUtil.makeJsonString(cData));
				}
			}
		} catch (IOException e) {
			log.error(new Date() + " : " + e.getMessage());
		} catch (Exception e) {
			log.error(new Date() + " : " + e.getMessage());
		}
		
	}
	
	public void websocketSendMessage(String message) throws IOException {
		try {
			for (WebSocketSession webSocketSession : WebSocketHandler.sessions) {
				webSocketSession.sendMessage(new TextMessage(message));
	        }
		}  catch (IOException e) {
			log.error(new Date() + " : " + e.getMessage());
		} catch (Exception e) {
			log.error(new Date() + " : " + e.getMessage());
		}
		
	}
}
