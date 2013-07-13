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
	
	public static int[] vectorAddition(Integer[] a, Integer[] b) {
		int[] c = new int[5];
		
		for (int i = 0; i < 5; i++)
			c[i] = a[i] + b[i];
		
		return c;
	}
	
	public static int[] loopDependency(Integer[] a, Integer[] b) {
		int[] c = new int[a.length];
		
		for (int i = 0; i < a.length; i++)
			c[i] = a[b[i]];
 		
		return c;
	}
	
	public static void copyArray() {
		int[] a = {1, 2, 3};
		int[] b = new int[3];
		
		b[0] = a[0];
		b[1] = a[1];
		b[2] = a[2];
		
		String s = Arrays.toString(b);
	}
	
	public static void main(String[] args) {
		arrayAccess();
		fieldAccess();
	}
}
