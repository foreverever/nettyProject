package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.service.RedisService;
import com.example.demo.service.TrafficService;

@Controller
public class TrafficController {
	
	@Autowired
	private TrafficService traffic;
	@Autowired
	private RedisService redis;
	
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String enter() {
		return "main";
	}

	@RequestMapping(value = "/content", method = RequestMethod.GET)
	public @ResponseBody Object content(String startDay, String endDay, String startTime, String endTime) {
		
		return traffic.search(startDay, endDay, startTime, endTime);
	}

	@RequestMapping(value = "/graph", method = RequestMethod.GET)
	public @ResponseBody Object graph() {

		return redis.getCount();
	}
	
	@RequestMapping(value = "/rank", method = RequestMethod.GET)
	public @ResponseBody Object rank() {
		
		return traffic.rank();
	}
	
	@RequestMapping(value = "/time", method = RequestMethod.GET)
	public @ResponseBody Object time() {
		
		return traffic.time();
	}
}
