package uk.ac.ed.inf.icsa.locomotion.instrumentation.storage;

import uk.ac.ed.inf.icsa.locomotion.instrumentation.Access;

import java.util.HashSet;

import org.github.jamm.MemoryMeter;

public class HashSetTrace extends Trace {
	private HashSet<Access> entries;
	
	public HashSetTrace(TraceConfiguration configuration) {
		super(configuration);
		this.entries = new HashSet<>();
	}
	
	@Override
	public long memoryUsage() {
		return new MemoryMeter().measureDeep(entries);
	}

	@Override
	public void add(Access entry) {
		entries.add(entry);
	}

	@Override
	public boolean contains(Access entry) {
		return entries.contains(entry);
	}

	@Override
	public int size() {
		return entries.size();
	}
}
