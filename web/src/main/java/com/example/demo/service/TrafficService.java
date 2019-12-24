package com.example.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.TrafficDAO;
import com.example.demo.vo.RankingVO;
import com.example.demo.vo.TimehVO;
import com.example.demo.vo.TrafficVO;

@Service
public class TrafficService {

	@Autowired
	private TrafficDAO traffic;

	public List<TrafficVO> search(String startDay, String endDay, String startTime, String endTime) {

		String start = startDay + " " + startTime;
		String end = endDay + " " + endTime + ":59";

		Map<String, String> content = new HashMap<String, String>();
		content.put("start", start);
		content.put("end", end);

		return traffic.search(content);
	}

	public List<RankingVO> rank() {

		return traffic.rank();
	}

	public int[] time() {
		int[] timeCnt = { 0, 0, 0, 0, 0, 0, 0, 0 };

		List<TimehVO> time = traffic.time();

		for (int i = 0; i < time.size(); i++) {
			switch (time.get(i).getClock()) {
			case 0:case 1:case 2:
				timeCnt[0] += time.get(i).getCnt();
				break;
			case 3:case 4:case 5:
				timeCnt[1] += time.get(i).getCnt();
				break;
			case 6:case 7:case 8:
				timeCnt[2] += time.get(i).getCnt();
				break;
			case 9:case 10:case 11:
				timeCnt[3] += time.get(i).getCnt();
				break;
			case 12:case 13:case 14:
				timeCnt[4] += time.get(i).getCnt();
				break;
			case 15:case 16:case 17:
				timeCnt[5] += time.get(i).getCnt();
				break;
			case 18:case 19:case 20:
				timeCnt[6] += time.get(i).getCnt();
				break;
			case 21:case 22:case 23:
				timeCnt[7] += time.get(i).getCnt();
				break;
			}
		}
		
		return timeCnt;
	}
}
