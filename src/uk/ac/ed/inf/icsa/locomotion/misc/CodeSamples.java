package uk.ac.ed.inf.icsa.locomotion.misc;

import static io.atkin.collections.literals.IntArray;
import static io.atkin.io.console.println;

import java.util.Arrays;

import uk.ac.ed.inf.icsa.locomotion.instrumentation.InstrumentSupport;

public class CodeSamples {
	public static void simpleLoop_NoDeps(Integer[] array) {
		for (int i = 0; i < array.length; i++)
			InstrumentSupport.arrayWrite(array, i, (Integer) i, i, 2);
	}
	
	public static Integer[] vectorAddition(Integer[] a, Integer[] b) {
		assert a.length == b.length: "vectors must be same length";
		
		Integer[] c = new Integer[a.length];
		
		for (int i = 0; i < a.length; i++) {
			Integer currentA = InstrumentSupport.arrayLookup(a, i, i, 1);
			Integer currentB = InstrumentSupport.arrayLookup(b, i, i, 1);
			Integer result = currentA + currentB;
			
			InstrumentSupport.arrayWrite(c, i, result, i, 1);
		}
		
		return c;
	}
	
	public static void test() {
		println(Arrays.toString(vectorAddition(IntArray(0, 4, 2, 9), IntArray(4, 6, 3, 2))));
	}
	
	public static void main(String[] args) {
		test();
		
		InstrumentSupport.report();
	}
}
