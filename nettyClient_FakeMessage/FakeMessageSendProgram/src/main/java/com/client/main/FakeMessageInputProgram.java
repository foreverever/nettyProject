package com.client.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.client.exception.DataOutOfRangeException;
import com.client.exception.RatioOutOfRangeException;
import com.client.exception.RatioSumOutOfRangeException;
import com.client.fakedata.FakeDataInfo;
import com.client.fakedata.NumberInstance;

public class FakeMessageInputProgram {
	
	private final Logger logger = LoggerFactory.getLogger(FakeMessageInputProgram.class);
	
	public void run() {
		//이 부분은 CLI 프로그램이라 사용자에게 나타내고 싶은 부분은 System.out.print처리
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		boolean isInputEnd = false;
		int totalCount = 0;
		int totalNum = 0;
		
		FakeDataInfo fakeDataInfo = FakeDataInfo.getInstance();
		
		while(!isInputEnd) {
			try {
				while(totalNum <= 0) {
					System.out.print("원하는 조작 번호의 갯수를 정해주세요 (1 이상을 입력하세요!) : ");
					totalNum = Integer.parseInt(br.readLine());
				}
				
				System.out.print("총 조작 투표수를 적어주세요 : ");
				while(totalCount <= 0) {
					totalCount = Integer.parseInt(br.readLine());
					fakeDataInfo.setTotalCount(totalCount);
				}

				System.out.println("조작 번호와 조작 비율을 적어주세요!! (Ex. '2 33' => 2번 33%)");
				System.out.println("조작 번호는 중복되지 않게 입력하여 주세요!!");
				System.out.println("총 비율의 합은 100이 되어야 합니다!!");
				
				HashSet<Integer> overlapNum = new HashSet<Integer>();
				
				int addRatioSum = 0;
				for(int idx = 0; idx < totalNum; idx++) {
					System.out.print((idx + 1) + " : ");
					String[] numAndRatio = br.readLine().split(" ");
					int num = Integer.parseInt(numAndRatio[0]);
					
					if(num < 1 || num > 9) {
						throw new DataOutOfRangeException("Data is out of range");
					}
					if(overlapNum.contains(num)) {
						break;
					}
					
					overlapNum.add(num);
					
					int ratio = Integer.parseInt(numAndRatio[1]);
					
					if(ratio < 0 || ratio > 100) {
						throw new RatioOutOfRangeException("Data Ratio is out of range.");
					}
					
					int count = (int) (totalCount * (ratio / 100.0));
					fakeDataInfo.getArray().add(new NumberInstance(num,count));
					addRatioSum += ratio;
				}
				
				if(addRatioSum != 100) {
					fakeDataInfo.getArray().clear();
					throw new RatioSumOutOfRangeException("RatioSum is not 100%");
				}

				
				int totalArrayCount = fakeDataInfo.getTotalArrayCount();
				while(totalArrayCount < totalCount) {
					int difference = totalCount - totalArrayCount;
					int divideNum = difference / totalNum + 1;
					
					for(int idx = 0; idx < totalNum; idx++) {
						int count = fakeDataInfo.getArray().get(idx).getCount();
						if(difference - divideNum > 0) {
							fakeDataInfo.getArray().get(idx).setCount(count + divideNum);
							difference -= divideNum;
						}
						else {
							fakeDataInfo.getArray().get(idx).setCount(count + difference);
							difference = 0;
						}
					}
					
					totalArrayCount = fakeDataInfo.getTotalArrayCount();
				}
				
				isInputEnd = true;
				
				System.out.println("데이터 입력을 완료하였습니다.");
				
			
				// 입력된 데이터 모두 보여주기
				System.out.println("조작 번호 및 조작 갯수");
				for(int idx = 0; idx < fakeDataInfo.getArrayLength(); idx++) {
					System.out.println(fakeDataInfo.getArray().get(idx).toString());
				}
				
				boolean systemOperate = false;
				
				while(!systemOperate) {
					System.out.print("조작 프로그램 가동을 시작하시겠습니까? (Y/N) : ");
					String answer = br.readLine().toUpperCase();
					
					if(answer.equals("Y")) {
						systemOperate = true;
					}
					
					else if(answer.equals("N")) {
						System.out.println("프로그램을 종료합니다.");
						System.exit(0);
					}
					else {
						System.out.println("Y/N의 형태로 입력하여 주세요!");
					}
				}
				
				
				System.out.println("조작 프로그램 가동을 시작합니다.");
			} catch (NumberFormatException e) {
				e.printStackTrace();
				logger.error("input data is not integer");
				System.out.println("숫자 형태로 작성해주세요!");
			} catch (IOException e) {
				e.printStackTrace();
				logger.error("IOException");
			} catch (DataOutOfRangeException e) {
				e.printStackTrace();
				logger.error("Data is out of range");
				System.out.println("조작 번호의 범위가 초과했습니다. 데이터 범위 : 1 ~ 9");
			} catch (RatioOutOfRangeException e) {
				e.printStackTrace();
				logger.error("Ratio is out of range");
				System.out.println("조작 비율 범위가 초과했습니다. 비율의 범위 : 1 ~ 100");
			} catch (RatioSumOutOfRangeException e) {
				e.printStackTrace();
				logger.error("RatioSum is not 100%");
				System.out.println("조작 비율의 합이 100%가 되지 않습니다.");
			}
		}
	}
}
