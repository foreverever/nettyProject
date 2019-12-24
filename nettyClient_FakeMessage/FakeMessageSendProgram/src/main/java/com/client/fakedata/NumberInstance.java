package com.client.fakedata;

public class NumberInstance {
	
	private int number;
	private int count;
	
	public NumberInstance(int number, int count) {
		this.number = number;
		this.count = count;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	@Override
	public String toString() {
		return "[조작 번호=" + number + ", 조작 갯수=" + count + "]";
	}
	
	

}
