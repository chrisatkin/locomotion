package uk.ac.ed.inf.icsa.locomotion.misc;

import java.util.Arrays;

public class CodeSamples {
	public static int publicInt = 0;
	protected static int protectedInt = 1;
	private static int privateInt = 2;
	
	public static void arrayAccess() {
		int[] test = new int[1];
		test[0] = 1;
	}
	
	public static void fieldAccess() {
		System.out.println("Old public: " + publicInt);
		System.out.println("Old protected: " + protectedInt);
		System.out.println("Old private: " + privateInt);
		
		publicInt++;
		protectedInt++;
		privateInt++;
		
		System.out.println("New public: " + publicInt);
		System.out.println("New protected: " + protectedInt);
		System.out.println("New private: " + privateInt);
	}
	
	public static void vectorAddition() {
//		int[] a = { 5, 3, 9, 10, 2};
//		int[] b = { 5, 7, 1, 0,  8};
//		
//		assert a.length == b.length : "vector operands must be same length";
//		
//		int[] c = new int[a.length];
//		
//		for (int i = 0; i < a.length; i++)
//			c[i] = a[i] + b[i];
//		
//		System.out.println(Arrays.toString(c));
		
		int[] a = {4};
		int b = a[0];
	}
	
	public static void main(String[] args) {
		arrayAccess();
		fieldAccess();
		vectorAddition();
	}
}
