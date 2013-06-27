package uk.ac.ed.inf.icsa.locomotion.instrumentation.trace;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnel;
import com.google.common.hash.PrimitiveSink;

import uk.ac.ed.inf.icsa.locomotion.instrumentation.Entry;

public final class BloomFilterTrace implements Trace {
	
	private BloomFilter<Entry> bloom;
	private int entryCount = 0;

	@SuppressWarnings("serial")
	public BloomFilterTrace(int expectedInsertations) {
		this.bloom = BloomFilter.create(new Funnel<Entry>() {
			@Override
			public void funnel(Entry entry, PrimitiveSink into) {
				into.putLong(entry.getAddress());
				into.putLong(entry.getValue());
			}}, expectedInsertations);
	}

	@Override
	public void add(Entry entry) {
		bloom.put(entry);
		entryCount++;
	}
	
	@Override
	public boolean contains(Entry entry) {
		return bloom.mightContain(entry);
	}

	@Override
	public int entryCount() {
		return entryCount;
	}
}
