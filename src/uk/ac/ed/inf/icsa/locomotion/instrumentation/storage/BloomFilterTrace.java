package uk.ac.ed.inf.icsa.locomotion.instrumentation.storage;

import uk.ac.ed.inf.icsa.locomotion.instrumentation.Access;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnel;

public final class BloomFilterTrace extends Trace {
	private BloomFilter<Access> bloom;
	private int size;

	
	public BloomFilterTrace(TraceConfiguration configuration) {
		super((BloomFilterConfiguration) configuration);
		
		BloomFilterConfiguration bfc = (BloomFilterConfiguration) this.configuration;
		this.bloom = BloomFilter.create(bfc.getFunnel(), 0.03d, bfc.getExpectedInsertations()/*, bfc.getExpectedInsertations()*/);
	}

	@Override
	public void add(Access entry) {
		bloom.put(entry);
		size++;
	}
	
	@Override
	public boolean contains(Access entry) {
		return bloom.mightContain(entry);
	}

	@Override
	public int size() {
		return size;
	}
}
