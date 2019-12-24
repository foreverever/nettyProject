package com.example.demo.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.vo.RankingVO;
import com.example.demo.vo.TimehVO;
import com.example.demo.vo.TrafficVO;

@Repository
public class TrafficDAO {
	
	@Autowired
	private SqlSessionTemplate session;
	
	public List<TrafficVO> search(Map<String, String> content) {
		return session.selectList("traffic.search", content);
	}
	
	public List<RankingVO> rank() {
		return session.selectList("traffic.rank");		
	}
	
	public void rankUpdate() {
		session.update("traffic.updateRank");
	}
	
	public List<TimehVO> time() {
		return session.selectList("traffic.time");
	}
	
	public void timeReset() {
		session.update("traffic.resetTime");
	}
	
	public void timeUpdate() {
		session.update("traffic.updateTime");
	}
	
}
