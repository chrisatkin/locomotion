package uk.ac.ed.inf.icsa.locomotion.instrumentation.storage;

import java.util.Map;
import java.util.HashMap;

import org.github.jamm.MemoryMeter;

import uk.ac.ed.inf.icsa.locomotion.instrumentation.Access;

import com.google.common.hash.BloomFilter;

public final class ExpandingBloomFilterTrace extends Trace {
	private Map<Integer, BloomFilter<Access>> blooms;
	private int size;
	private int lastUsedFilter;
	
	public ExpandingBloomFilterTrace(TraceConfiguration configuration) {
		super((BloomFilterConfiguration) configuration);
		
		this.blooms = new HashMap<>();
		this.lastUsedFilter = 0;
		addNewFilter(lastUsedFilter);
	}
	
	private void addNewFilter(int number) {
		BloomFilterConfiguration bfc = (BloomFilterConfiguration) configuration;
		
		blooms.put(number, BloomFilter.create(bfc.getFunnel(), bfc.getExpectedInsertations()));
	}

	@Override
	public void add(Access entry) {
		if (blooms.get(lastUsedFilter).expectedFpp() >= 0.03) {
			lastUsedFilter += 1;
			addNewFilter(lastUsedFilter);
			
		}
		
		blooms.get(lastUsedFilter).put(entry);
		size++;
	}
	
	@Override
	public boolean contains(Access entry) {
		boolean contains = false;
		
		for (Map.Entry<Integer, BloomFilter<Access>> b: blooms.entrySet())
			if (b.getValue().mightContain(entry))
				contains = true;
		
		return contains;
	}

	@Override
	public int size() {
		return size;
	}
	
	@Override
	public long memoryUsage() {
		return new MemoryMeter().measureDeep(blooms);
	}
}
