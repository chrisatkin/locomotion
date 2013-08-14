package uk.ac.ed.inf.icsa.locomotion.instrumentation.storage;

import java.util.HashSet;

import org.github.jamm.MemoryMeter;

import uk.ac.ed.inf.icsa.locomotion.instrumentation.Access;

public class HashSetWellConfigured extends Trace {
	private HashSet<Access> entries;
	
	public HashSetWellConfigured(TraceConfiguration configuration) {
		super(configuration);
		this.entries = new HashSet<>(configuration.insertions);
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
