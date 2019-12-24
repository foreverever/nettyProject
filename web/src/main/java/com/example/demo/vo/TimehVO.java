package com.example.demo.vo;

import org.apache.ibatis.type.Alias;

@Alias("Time")
public class TimehVO {
	private long num;
	private int clock;
	private int cnt;
	
	public long getNum() {
		return num;
	}
	public void setNum(long num) {
		this.num = num;
	}
	public int getClock() {
		return clock;
	}
	public void setClock(int clock) {
		this.clock = clock;
	}
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
}
