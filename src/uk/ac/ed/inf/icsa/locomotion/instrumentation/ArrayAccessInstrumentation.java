package uk.ac.ed.inf.icsa.locomotion.instrumentation;

import java.util.HashSet;
import java.util.Set;

public class ArrayAccessInstrumentation {
	private static int loadCount = 0;
	private static int storeCount = 0;
	public static Set<Integer> loadSet = new HashSet<Integer>();
	public static Set<Integer> storeSet = new HashSet<Integer>();
	
	public static void test(String s) {
		;
	}
	
	public static  void load() {
		loadCount++;
	}
	
	public static void load(int address) {
		load();
		loadSet.add(address);
	}
	
	public static void store() {
		storeCount++;
		
	}
	
	public static void store(int address) {
		store();
		storeSet.add(address);
	}
	
	public static int getLoadCount() {
		return loadCount;
	}
	
	public static int getStoreCount() {
		return storeCount;
	}
}
