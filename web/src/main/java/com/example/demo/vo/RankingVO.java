package com.example.demo.vo;

import org.apache.ibatis.type.Alias;

@Alias("Ranking")
public class RankingVO {
	private long num;
	private int content;
	private int cnt;
	
	public long getNum() {
		return num;
	}
	public void setNum(long num) {
		this.num = num;
	}
	public int getContent() {
		return content;
	}
	public void setContent(int content) {
		this.content = content;
	}
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
}
