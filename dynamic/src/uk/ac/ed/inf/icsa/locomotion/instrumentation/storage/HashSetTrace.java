package uk.ac.ed.inf.icsa.locomotion.instrumentation.storage;

import uk.ac.ed.inf.icsa.locomotion.instrumentation.Access;

import java.util.HashSet;

import org.github.jamm.MemoryMeter;

public class HashSetTrace extends SetTrace {
	public HashSetTrace(TraceConfiguration configuration) {
		super(configuration, new HashSet<Access>());
	}
	
	@Override
	public long memoryUsage() {
		return new MemoryMeter().measureDeep(entries);
	}
}
