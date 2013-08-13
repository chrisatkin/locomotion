package uk.ac.ed.inf.icsa.locomotion.instrumentation.storage;

import org.github.jamm.MemoryMeter;

import com.facebook.infrastructure.utils.BloomFilter;

import uk.ac.ed.inf.icsa.locomotion.instrumentation.Access;


public final class CassandraBloomFilterTrace extends Trace {
	public CassandraBloomFilterTrace(TraceConfiguration configuration) {
		super((BloomFilterConfiguration) configuration);
		this.bloom = new BloomFilter(configuration.insertions, 0.03);
	}

	private BloomFilter bloom;
	private int size;

	@Override
	public void add(Access entry) {
		bloom.add(entry.hashString());
		size++;
	}
	
	@Override
	public boolean contains(Access entry) {
		return bloom.isPresent(entry.hashString());
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
