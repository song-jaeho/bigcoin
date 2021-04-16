package com.bigcoin.handler;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.bigcoin.common.util.CommonUtil;
import com.bigcoin.dao.RedisDAO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketHandler extends TextWebSocketHandler {

	public static Set<WebSocketSession> sessions = ConcurrentHashMap.newKeySet();
	
	private RedisDAO redisDAO;
	
	@Autowired
	public WebSocketHandler(RedisDAO redisDAO) {
		super();
		this.redisDAO = redisDAO;
	}
	
	@Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        
        sessions.add(session);
        log.info("client{} connect", session.getRemoteAddress());
        
        String allCurrencyData = CommonUtil.makeJsonString(redisDAO.vopGetAll());
        session.sendMessage(new TextMessage(allCurrencyData));
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    	log.info("client{} handle message:{}", session.getRemoteAddress(), message.getPayload());
        
        for (WebSocketSession webSocketSession : sessions) {
            //if (session == webSocketSession) continue;
            webSocketSession.sendMessage(message);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        sessions.remove(session);
        log.info("client{} connect", session.getRemoteAddress());
    }
    
}