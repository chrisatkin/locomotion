package uk.ac.ed.inf.icsa.locomotion;

import static java.lang.Math.random;
import static io.atkin.io.console.println;
import static org.apache.commons.lang3.ArrayUtils.addAll;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import uk.ac.ed.inf.icsa.locomotion.instrumentation.AccessKind;
import uk.ac.ed.inf.icsa.locomotion.instrumentation.DependencyKind;

import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.random.MersenneTwister;
import org.apache.commons.math3.distribution.EnumeratedDistribution;
import org.apache.commons.math3.util.Pair;

public class Test {
	
	public void run() {
		final int length = 10;
		final int deps = 10;
		final double prob_write_write = 0.3d;
		final double prob_write_read = 0.3d;
		final double prob_read_write = 0.3d;
		
		int[] a = new int[length];
		Integer[] b = new Integer[length];
		AccessKind[] P = new AccessKind[length];
		RandomGenerator rand = new MersenneTwister();
		rand.setSeed(938839);
		
		
		// generate a first
		for (int i = 0; i < length; i++)
			a[i] = (int) (random() * length);
		
//		println(Arrays.toString(a));
		
		// generate b
		int dep_ops = deps;
		Integer[] b_temp1 = new Integer[dep_ops];
		Integer[] b_temp2 = range(dep_ops, length);
		int current_nextval = length - dep_ops;
		boolean use_current_nextval = true;
		
		for (int i = 0; i < dep_ops; i++) {
			b_temp1[i] = current_nextval;
			
			if (!use_current_nextval)
				current_nextval++;
			
			use_current_nextval = !use_current_nextval;
		}
		
		b = addAll(b_temp1, b_temp2);
		
		List<Integer> l = Arrays.asList(b);
		Collections.shuffle(l);
		b = (Integer[]) l.toArray();
		
		println(Arrays.toString(b));
		
		// generate P
		for (int i = 0; i < length; i++) {
			int this_index = b[i], match = 0;
			boolean has_match = false;
			
			for (int j = 0; j < length; j++) {
				if (b[j] == this_index && i != j && i < j) {
					has_match = true;
					match = j;
				}
			}
			
			if (has_match) {
				
				@SuppressWarnings("serial")
				ArrayList<Pair<DependencyKind, Double>> singletons = new ArrayList<Pair<DependencyKind, Double>>() {{
					add(new Pair<>(DependencyKind.WriteWrite, prob_write_write));
					add(new Pair<>(DependencyKind.WriteRead, prob_write_read));
					add(new Pair<>(DependencyKind.ReadWrite, prob_read_write));
				}};
				
				EnumeratedDistribution<DependencyKind> d = new EnumeratedDistribution<DependencyKind>(rand, singletons);
				
				switch (d.sample()) {
					case WriteWrite:
						P[i] = AccessKind.Store;
						P[match] = AccessKind.Store;
					break;
					
					case WriteRead:
						P[i] = AccessKind.Store;
						P[match] = AccessKind.Load;
					break;
					
					case ReadWrite:
						P[i] = AccessKind.Load;
						P[match] = AccessKind.Store;
					break;
				}
			}
		}
		
		for (int i = 0; i < length; i++)
			if (P[i] == null) {
				ArrayList<Pair<AccessKind, Double>> s = new ArrayList<Pair<AccessKind, Double>>() {{
					add(new Pair<>(AccessKind.Store, 0.5d));
					add(new Pair<>(AccessKind.Load, 0.5d));
				}};
				
				EnumeratedDistribution<AccessKind> d = new EnumeratedDistribution<>(s);
				P[i] = d.sample();
			}
		
		println(Arrays.toString(P));
		
		statistics(b, P);
	}
	
	private void statistics(Integer[] b, AccessKind k[]) {
		int count_writewrite = 0, count_readwrite = 0, count_writeread = 0, count_nodep = 0;
		
		for (int i = 0; i < b.length; i++) {
			int this_index = b[i], match = 0;
			boolean found_match = false;
					
			for (int j = 0; j < b.length; j++) {
				if (b[j] == this_index && i != j && i < j) {
//					System.out.println(i + " has " + j);
					found_match = true;
					match = j;
				}
			}
			
			System.out.println(found_match);
			System.out.println(i + "(" + k[i] + ") has " + match + "(" + k[match] + ")");
			
			if (!found_match) {
				count_nodep++;
			} else {
				if (k[i] == AccessKind.Store && k[match] == AccessKind.Store)
					count_writewrite++;
				
				else if (k[i] == AccessKind.Store && k[match] == AccessKind.Load)
					count_writeread++;
				
				else if (k[i] == AccessKind.Load && k[match]== AccessKind.Store)
					count_readwrite++;
				}	
		}
		
		System.out.println("write-write = " + count_writewrite);
		System.out.println("write-read = " + count_writeread);
		System.out.println("read-write = " + count_readwrite);
		System.out.println("none = " + count_nodep);
	}

	private Integer[] range(int start, int end) {
		Integer[] a = new Integer[end - start];
		
		for (int i = 0; i < end - start; i++)
			a[i] = i;
		
		return a;
	}
	
	public static void main(String[] args) {
		new Test().run();
	}
}
