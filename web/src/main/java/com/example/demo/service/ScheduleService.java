package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.demo.dao.TrafficDAO;

@Service
public class ScheduleService {

	@Autowired
	private TrafficDAO traffic;
	
	@Scheduled(fixedRate = 10000)
	public void updateRank() {
		traffic.rankUpdate();
	}
	
	@Scheduled(cron = "0 0 0 * * *")
	public void resetTime() {
		traffic.timeReset();
	}
	
	@Scheduled(cron = "1 0/10 * * * *")
	public void updateTime() {
		traffic.timeUpdate();
	}
}
