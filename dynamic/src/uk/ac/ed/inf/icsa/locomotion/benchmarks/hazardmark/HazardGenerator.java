package uk.ac.ed.inf.icsa.locomotion.benchmarks.hazardmark;

import static io.atkin.io.console.println;
import static java.lang.Math.random;
import static org.apache.commons.lang3.ArrayUtils.addAll;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.apache.commons.math3.util.Pair;
import org.apache.commons.math3.distribution.EnumeratedDistribution;
import org.apache.commons.math3.random.MersenneTwister;
import org.apache.commons.math3.random.RandomGenerator;

import uk.ac.ed.inf.icsa.locomotion.instrumentation.AccessKind;
import uk.ac.ed.inf.icsa.locomotion.instrumentation.DependencyKind;

/**
 * @author chris
 *
 */
public class HazardGenerator implements Generator {

	public static Generator allDependentAllReadWrite(int length, long seed) {
		return new HazardGenerator(new MersenneTwister()).configure(length, length / 2, seed, 0.0d, 0.0d, 1.0d).generate();
	}
	
	public static Generator allDependentAllWriteRead(int length, long seed) {
		return new HazardGenerator(new MersenneTwister()).configure(length, length / 2, seed, 0.0d, 1.0d, 0.0d).generate();
	}
	
	public static Generator allDependentAllWriteWrite(int length, long seed) {
		return new HazardGenerator(new MersenneTwister()).configure(length, length / 2, seed, 1.0d, 0.0d, 0.0d).generate();
	}
	
	public static Generator allDependentEqual(int length, long seed) {
		return new HazardGenerator(new MersenneTwister()).configure(length, length / 2, seed, 0.3d, 0.3d, 0.3d).generate();
	}
	
	public static Generator someDependentEqual(int length, int deps, long seed) {
		return new HazardGenerator(new MersenneTwister()).configure(length, deps, seed, 0.3d, 0.3d, 0.3d).generate();
	}
	
	public static Generator noDependencies(int length, long seed) {
		return new HazardGenerator(new MersenneTwister()).configure(length, 0, seed, 0.3d, 0.3d, 0.3d).generate();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//Generator g = new HazardGenerator(new MersenneTwister()).configure(1000, 500, System.nanoTime(), 0.3d, 0.3d, 0.3d).generate();
		Generator g = HazardGenerator.allDependentEqual(6, 938839);
		
		int[] a = g.getA();
		int[] b = g.getB();
		AccessKind[] k = g.getAccessPattern();
		
		println(Arrays.toString(b));
		println(HazardGenerator.statistics(b, k));
	}
	
	/**
	 * @param b
	 * @param k
	 * @return
	 */
	public static String statistics(int[] b, AccessKind[] k) {
		StringBuilder result = new StringBuilder();
		
		int countWriteWrite = 0,
			countReadWrite= 0,
			countWriteRead = 0,
			countNone = 0;
		
		for (int i = 0; i < b.length; i++) {
			int match = 0;
			boolean found_match = false;
					
			for (int j = 0; j < b.length; j++) {
				if (b[j] == b[i] && i < j) {
					found_match = true;
					match = j;
				}
			}
			
			if (!found_match) {
				countNone++;
			} else {
				if (k[i] == AccessKind.Store && k[match] == AccessKind.Store)
					countWriteWrite++;
				
				else if (k[i] == AccessKind.Store && k[match] == AccessKind.Load)
					countWriteRead++;
				
				else if (k[i] == AccessKind.Load && k[match]== AccessKind.Store)
					countReadWrite++;
			}	
		}
		
		result.append(Arrays.toString(k)).append("\n");
		result.append("write-write=").append(countWriteWrite).append("\n");
		result.append("write-read=").append(countWriteRead).append("\n");
		result.append("read-write=").append(countReadWrite).append("\n");
		result.append("no-dep=").append(countNone);
		
		return result.toString();
	}
	
	public int[] a;
	private int[] b;
	private int dependencies;
	private AccessKind k[];
	
	private int length;
	private double prob_read_write;
	private double prob_write_read;
	private double prob_write_write;
	private long seed;
	
	/**
	 * 
	 */
	private RandomGenerator rand;
	
	/**
	 * @param gen
	 */
	public HazardGenerator(RandomGenerator gen) {
		this.rand = gen;
	}
	
	private class FillArrayThread implements Runnable {
		private int start;
		private int end;
		
		public FillArrayThread(int start, int end) {
			this.start = start;
			this.end = end;
		}
		
		@Override
		public void run() {
			for (int i = start; i < end; i++) {
				HazardGenerator.this.a[i] = i;
			}
		}
	}
	
	/**
	 * 
	 */
	private void _generate_a() {
		// assume dual core, since this is all I have access to
		new FillArrayThread(0, length / 2).run();
		new FillArrayThread(length /2, length).run();
	}
	
	/**
	 * 
	 */
	private void _generate_b() {
		// each dependency consists of two operations
		int numDependentOperations = dependencies * 2;
		int[] temp1 = new int[numDependentOperations];
		int[] temp2 = range(numDependentOperations, length);
		
		// generate the operations with dependencies
		// using a simple state machine to assign indexes pairwise
		int nextValue = length - numDependentOperations;
		boolean nextState = true;
		
		for (int i = 0; i < numDependentOperations; i++) {
			temp1[i] = nextValue;
		
			if (!nextState)
				nextValue++;
			
			nextState = !nextState;
		}
		
		// Concatenate the two temporary arrays
		b = addAll(temp1, temp2);
		
		// shuffle
		shuffleArray(b);
	}
	
	/**
	 * 
	 */
	private void _generate_k() {
		for (int i = 0; i < length; i++) {
			int match = 0;
			boolean has_match = false;
			
			for (int j = i; j < length; j++) {
				if (b[j] == b[i] && i < j) {
					has_match = true;
					match = j;
					continue;
				}
			}
			
			if (has_match) {
				@SuppressWarnings("serial")
				EnumeratedDistribution<DependencyKind> d = new EnumeratedDistribution<DependencyKind>(
					rand,
					new ArrayList<Pair<DependencyKind, Double>>() {{
						add(new Pair<>(DependencyKind.WriteWrite, prob_write_write));
						add(new Pair<>(DependencyKind.WriteRead, prob_write_read));
						add(new Pair<>(DependencyKind.ReadWrite, prob_read_write));
					}}
				);
				
				switch (d.sample()) {
					case WriteWrite:
						k[i] = AccessKind.Store;
						k[match] = AccessKind.Store;
					break;
					
					case WriteRead:
						k[i] = AccessKind.Store;
						k[match] = AccessKind.Load;
					break;
					
					case ReadWrite:
						k[i] = AccessKind.Load;
						k[match] = AccessKind.Store;
					break;
				}
			}
		}
		
		for (int i = 0; i < length; i++)
			if (k[i] == null) {
				ArrayList<Pair<AccessKind, Double>> s = new ArrayList<Pair<AccessKind, Double>>() {{
					add(new Pair<>(AccessKind.Store, 0.5d));
					add(new Pair<>(AccessKind.Load, 0.5d));
				}};
				
				EnumeratedDistribution<AccessKind> d = new EnumeratedDistribution<>(s);
				k[i] = d.sample();
			}
	}
	
	/**
	 * @param length
	 * @param dependencies
	 * @param seed
	 * @param prob_writewrite
	 * @param prob_writeread
	 * @param prob_readwrite
	 * @return
	 */
	@Override
	public Generator configure(int length, int dependencies, long seed, double prob_writewrite, double prob_writeread, double prob_readwrite) {
		this.length = length;
		this.dependencies = dependencies;
		this.prob_write_write = prob_writewrite;
		this.prob_write_read = prob_writeread;
		this.prob_read_write = prob_readwrite;
		this.seed = seed;
		this.a = new int[this.length];
		this.b = new int[this.length];
		this.k = new AccessKind[this.length];
		
		this.rand.setSeed(this.seed);
		
		return this;
	}
	
	/**
	 * @return
	 */
	@Override
	public Generator generate() {
		System.out.println("generating " + toString());
		
		_generate_a();
		_generate_b();
		_generate_k();
		
		return this;
	}
	
	/**
	 * @return
	 */
	@Override
	public int[] getA() {
		return a;
	}
	
	@Override
	public AccessKind[] getAccessPattern() {
		return k;
	}
	
	/**
	 * @return
	 */
	@Override
	public int[] getB() {
		return b;
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		
		result.append("length=").append(length);
		result.append(";writewrite=").append(prob_write_write);
		result.append(";writeread=").append(prob_write_read);
		result.append(";readwrite=").append(prob_read_write);
		
		return result.toString();
	}
	
	/**
	 * @param start
	 * @param end
	 * @return
	 */
	private int[] range(int start, int end) {
		int[] result = new int[end - start];
		
		for (int i = 0; i < (end - start); i++) {
			result[i] = i;
		}
		
		return result;
	}
	
	/**
	 * @param array
	 */
	private void shuffleArray(int[] array) {
		Random random = new Random();

		for (int i = array.length - 1; i >= 0; i--) {
			int index = random.nextInt(i + 1);

			int a = array[index];
			array[index] = array[i];
			array[i] = a;
		}
	}
}