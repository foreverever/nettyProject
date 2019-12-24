package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

	@Autowired
	StringRedisTemplate redis;
	
	public int getCount() {
		ValueOperations<String, String>vop = redis.opsForValue();
		if(vop.get("newCount") == null) return 0;
		int count = Integer.valueOf(vop.get("newCount"));
		vop.set("newCount", "0");
		
		return count;		
	}
}
