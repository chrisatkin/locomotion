package uk.ac.ed.inf.icsa.locomotion.misc;

import java.util.Arrays;

@SuppressWarnings("unused")
public class CodeSamples {
	public static int publicInt = 0;
	protected static int protectedInt = 1;
	private static int privateInt = 2;
	
	public static void arrayAccess() {
		int[] test = new int[1];
		test[0] = 1;
	}
	
	public static void fieldAccess() {
		publicInt = 1;
	}
	
	public static int[] vectorAddition(Integer[] a, Integer[] b) {
		int[] c = new int[5];
		
		for (int i = 0; i < 5; i++)
			c[i] = a[i] + b[i];
		
		return c;
	}
	
	
	public static Integer arrayAccess(Integer[] a, int index) {
		return a[index];
	}
	
	public static Integer test(Integer[] a) {
		int t = a[0];
		return t;
	}
	
	public static int loopDependency(Integer[] a, Integer[] b) {
		int t = a[0];
		return t;
		
//		return c;
	}
	
	public static void copyArray() {
		int[] a = {1, 2, 3};
		int[] b = new int[3];
		
		b[0] = a[0];
		b[1] = a[1];
		b[2] = a[2];
		
		String s = Arrays.toString(b);
	}
	
	public static void testInstrument() {
		
	}
	
	public static void main(String[] args) {
		arrayAccess();
		fieldAccess();
	}
}
