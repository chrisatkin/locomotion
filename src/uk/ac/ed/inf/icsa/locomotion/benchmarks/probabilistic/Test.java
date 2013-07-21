package uk.ac.ed.inf.icsa.locomotion.benchmarks.probabilistic;

import java.util.Arrays;

final class Test {
	private static final int N = 10;

	public static void main(String[] args) {
		Integer[] a, b;
		
		// test all
		Generator all = new AllDependent(N);
		all.generate();
		a = all.getA();
		b = all.getB();
		
		display("all", a, b);
		
		// test none
		Generator none = new NoneDependent(N);
		none.generate();
		a = none.getA();
		b = none.getB();
		
		display("none", a, b);
		
		// test some
		Generator some = new SomeDependent(N, 0.5d);
		some.generate();
		a = some.getA();
		b = some.getB();
		
		display("some", a, b);
	}
	
	private static void display(String n, Integer[] a, Integer[] b) {
		System.out.println(n);
		System.out.println("a: " + Arrays.toString(a));
		System.out.println("b: " + Arrays.toString(b));
	}

}
