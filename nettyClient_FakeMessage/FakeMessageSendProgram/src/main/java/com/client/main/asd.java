package com.client.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

public class asd {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String message = br.readLine();
		Pattern p = Pattern.compile("^NACK[a-zA-Z]{4}");
		System.out.println(p.matches("^NACK[a-zA-Z]{4}", "NACKASDF"));
		switch (message) {

		case "OACK":
			System.out.println("oack");
			break;

		case "ASD":
			System.out.println("nack");
			break;
		}
	}

	private static boolean isNumber(String input) {

		char[] inputArray = input.toCharArray();

		for (int idx = 0; idx < inputArray.length; idx++) {
			if ('0' > inputArray[idx] || inputArray[idx] > '9') {
				return false;
			}
		}

		return true;
	}

}
