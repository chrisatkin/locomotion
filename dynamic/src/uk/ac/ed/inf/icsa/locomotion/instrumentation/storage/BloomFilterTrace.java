package uk.ac.ed.inf.icsa.locomotion.instrumentation.storage;

import org.github.jamm.MemoryMeter;

import uk.ac.ed.inf.icsa.locomotion.instrumentation.Access;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnel;

public final class BloomFilterTrace extends Trace {
	private BloomFilter<Access> bloom;
	private int size;

	
	public BloomFilterTrace(TraceConfiguration configuration) {
		super((BloomFilterConfiguration) configuration);
		
		BloomFilterConfiguration bfc = (BloomFilterConfiguration) this.configuration;
		this.bloom = BloomFilter.create(bfc.getFunnel(), bfc.getExpectedInsertations(), bfc.getFpp());
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
	
	@Override
	public long memoryUsage() {
		return new MemoryMeter().measureDeep(bloom);
	}
}
