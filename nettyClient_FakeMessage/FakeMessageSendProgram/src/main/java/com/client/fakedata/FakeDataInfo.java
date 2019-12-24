package com.client.fakedata;

import java.util.ArrayList;

public class FakeDataInfo {
	
	private ArrayList<NumberInstance> array;
	
	private int totalCount;

	public static FakeDataInfo fakeData;
		
	public static FakeDataInfo getInstance() {
		
		if(fakeData == null) {
			fakeData = new FakeDataInfo(new ArrayList<NumberInstance>(), 0);
		}
		
		return fakeData;
	}
	
	private FakeDataInfo(ArrayList<NumberInstance> array, int totalCount) {
		this.array = array;
		this.totalCount = totalCount;
	}
	
	public ArrayList<NumberInstance> getArray() {
		return array;
	}
	
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	
	public int getTotalCount() {
		return totalCount;
	}
	
	public int getArrayLength() {
		return array.size();
	}
	
	public int getTotalArrayCount() {
		int sum = 0;
		int size = array.size();
		for(int idx = 0; idx < size; idx++) {
			sum += array.get(idx).getCount();
		}
		
		return sum;
	}
	
	public void subtractCount(int idx) {
		int num = array.get(idx).getCount();
		array.get(idx).setCount(num-1);
	}
	
	public boolean isZeroCount(int idx) {
		int num = array.get(idx).getCount();
		if(num == 0) {
			return true;
		}
		return false;
	}
	
	public void deleteNumberInstance(int idx) {
		array.remove(idx);
	}
}
