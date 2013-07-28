package uk.ac.ed.inf.icsa.locomotion.benchmarks;

import java.util.Arrays;

import uk.ac.ed.inf.icsa.locomotion.benchmarks.probabilistic.StaticGenerator;
import uk.ac.ed.inf.icsa.locomotion.benchmarks.probabilistic.Generator;
import uk.ac.ed.inf.icsa.locomotion.benchmarks.probabilistic.NoneDependent;
import uk.ac.ed.inf.icsa.locomotion.benchmarks.probabilistic.FractionalGenerator;

final class Test {
	private static final int N = 10;

	public static void main(String[] args) {
		Integer[] a, b;
		
		// test all
		Generator all = new StaticGenerator(N);
		all.generate();
		a = all.getA();
		b = all.getB();
		
		display("all", a, b);
		
		// test some
		Generator some = new FractionalGenerator(N, 0.5d);
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
