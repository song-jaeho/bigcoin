package com.bigcoin.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class RedisDAO {

	private RedisTemplate<String, Object> redisTemplate;
	
	@Autowired
	public RedisDAO(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public void deleteAll() {
		// 기존것 다 지움
		redisTemplate.keys("*").stream()
			.forEach(key -> {
				if(redisTemplate.delete(key)) {
					log.info("RedisDAO deleteAll() success key : {}", key);
				} else {
					log.error("RedisDAO deleteAll() fail key : {}", key);
				}
			});
	}
	
	public void vopSave(String key, Object value) {
		redisTemplate.opsForValue().set(key, value);;
	}
	
	public Object vopGet(String key) {
		return redisTemplate.opsForValue().get(key);
	}
	
	public List<Object> vopGetAll() {
		
		ValueOperations<String, Object> vop = redisTemplate.opsForValue();
		List<Object> resultList = new ArrayList<Object>();
		
		Iterator<String> it = redisTemplate.keys("*").iterator();
		while(it.hasNext()) {
			resultList.add(vop.get(it.next()));
		}
		
		return resultList;
	}

}
