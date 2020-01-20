package com.generator;
import java.util.Random; 

public class CodeGenerator {
	
	public static Integer generateUniqueCode() {
		int max = 999999;
		int min = 100000;
		Random rand = new Random();
		return rand.ints(min,max).findFirst().getAsInt();
		
	}

}
