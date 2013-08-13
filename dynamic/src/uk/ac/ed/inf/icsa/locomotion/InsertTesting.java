package uk.ac.ed.inf.icsa.locomotion;

import java.util.HashSet;
import java.util.Random;

import uk.ac.ed.inf.icsa.locomotion.instrumentation.Access;
import uk.ac.ed.inf.icsa.locomotion.instrumentation.AccessKind;

import com.facebook.infrastructure.utils.BloomFilter;
//import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnel;
import com.google.common.hash.PrimitiveSink;

import static java.lang.Math.random;

public class InsertTesting {
	
	public static class AFunnel implements Funnel<Access> {
		@Override
		public void funnel(Access access, PrimitiveSink sink) {
			sink.putInt(access.getArrayId())
				.putInt(access.getIndex());
		}	
	}
	
	public static void main(String[] args) {
		final int N = 100000;
		
		
		
		//System.out.println("n\thash\tguava\tcassandra");
		//System.out.println("insert");
		
		for (int n = 10000; n <= 100000; n += 10000) {
			Access[] as = new Access[n];
			HashSet<Access> hash = new HashSet<>(n);
			com.google.common.hash.BloomFilter<Access> guava_bloom = com.google.common.hash.BloomFilter.create(new AFunnel(), n);
			BloomFilter cassandra_bloom = new BloomFilter(n, 0.03);
		
			Random random = new Random();
			for (int i = 0; i < n; i++)
				as[i] = new Access(random.nextInt(Integer.MAX_VALUE - 0), random.nextInt(Integer.MAX_VALUE - 0), AccessKind.Load);
			
			// insert all into hash set
			long hash_insert_total = 0, guava_bloom_insert_total = 0, cassandra_bloom_insert_total = 0;
			for (Access current: as) {
				long start = System.nanoTime();
				hash.add(current);
				hash_insert_total += (System.nanoTime() - start);
				
				start = System.nanoTime();
				guava_bloom.put(current);
				guava_bloom_insert_total += (System.nanoTime() - start);
				
				start = System.nanoTime();
				cassandra_bloom.add(current.toString());
				cassandra_bloom_insert_total += (System.nanoTime() - start);
			}
			
//			System.out.println(n + "\t" + (hash_insert_total/n) + "\t" + (guava_bloom_insert_total/n) + "\t" + (cassandra_bloom_insert_total/n));
			
//			// test membership
			long hash_member_total = 0, guava_member_total = 0, cassandra_member_total = 0;
			boolean contains;
			
			for (Access current: as) {
				long start  = System.nanoTime();
				contains = guava_bloom.mightContain(current);
				guava_member_total += (System.nanoTime() - start);
				
				start = System.nanoTime();
				contains = cassandra_bloom.isPresent(as.toString());
				cassandra_member_total += (System.nanoTime() - start);
				
				start = System.nanoTime();
				contains = hash.contains(current);
				hash_member_total += (System.nanoTime() - start);
			}
			
			System.out.println(n + "\t" + (hash_member_total/n) + "\t" + (guava_member_total/n) + "\t" + (cassandra_member_total/n));
			
//			long bloom_member_total = 0;
//			for (A curent: as) {
//				long start = System.nanoTime();
//	//			boolean contains = bloom.mightContain(curent);
//				boolean contains = cassandra_bloom.isPresent(curent.toString());
//				bloom_member_total += (System.nanoTime() - start);
//				//System.out.println("contains " + curent + ": " + contains);
//			}
//			
//			long hash_member_total = 0;
//			for (A current: as) {
//				long start = System.nanoTime();
//				boolean contains = hash.contains(current);
//				hash_member_total += (System.nanoTime() - start);
//			}
//		
//			System.out.println("hash avg contains=" + (hash_member_total / N) + " ns");
//			System.out.println("bloom avg contains=" + (bloom_member_total /N) + " ns");
//			double d = ((double) bloom_insert_total/(double) N) / ((double) hash_insert_total/(double) N);
//			System.out.println(d);
		}
	}

}
