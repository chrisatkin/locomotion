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
		
		for (int n = 1000; n <= 100000; n += 1000) {
			Access[] as = new Access[n];
			HashSet<Access> default_hash = new HashSet<>();
			HashSet<Access> hash = new HashSet<>(n, 0.75f);
			com.google.common.hash.BloomFilter<Access> guava_bloom = com.google.common.hash.BloomFilter.create(new AFunnel(), n, 0.01f);
			BloomFilter cassandra_bloom = new BloomFilter(n, 0.01f);
		
			Random random = new Random();
			for (int i = 0; i < n; i++)
				as[i] = new Access(random.nextInt(Integer.MAX_VALUE - 0), random.nextInt(Integer.MAX_VALUE - 0), AccessKind.Load);
			
			// insert all into hash set
			long hash_insert_total = 0, guava_bloom_insert_total = 0, cassandra_bloom_insert_total = 0, default_hash_insert_total = 0;
			for (Access current: as) {
				long start = System.nanoTime();
				hash.add(current);
				hash_insert_total += (System.nanoTime() - start);
				
				start = System.nanoTime();
				default_hash.add(current);
				default_hash_insert_total += (System.nanoTime() - start);
				
				start = System.nanoTime();
				guava_bloom.put(current);
				guava_bloom_insert_total += (System.nanoTime() - start);
				
				start = System.nanoTime();
				cassandra_bloom.add(current.toString());
				cassandra_bloom_insert_total += (System.nanoTime() - start);
			}
			
//			System.out.println(n + "\t" + (hash_insert_total/n) + "\t" + (default_hash_insert_total/n) + "\t" + (guava_bloom_insert_total/n) + "\t" + (cassandra_bloom_insert_total/n));
			
//			// test membership
			long hash_member_total = 0, guava_member_total = 0, cassandra_member_total = 0, default_hash_member_total = 0;
			boolean contains;
			
			for (Access current: as) {
				long start  = System.nanoTime();
				contains = guava_bloom.mightContain(current);
				guava_member_total += (System.nanoTime() - start);
				
				start = System.nanoTime();
				contains = default_hash.contains(current);
				default_hash_member_total += (System.nanoTime() - start);
				
				start = System.nanoTime();
				contains = cassandra_bloom.isPresent(as.toString());
				cassandra_member_total += (System.nanoTime() - start);
				
				start = System.nanoTime();
				contains = hash.contains(current);
				hash_member_total += (System.nanoTime() - start);
			}
			
			System.out.println(n + "\t" + (hash_member_total/n) + "\t" + (default_hash_member_total/n) + "\t" + (guava_member_total/n) + "\t" + (cassandra_member_total/n));
		}
	}

}
