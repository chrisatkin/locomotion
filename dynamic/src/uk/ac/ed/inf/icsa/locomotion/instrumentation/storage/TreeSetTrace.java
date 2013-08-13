package uk.ac.ed.inf.icsa.locomotion.instrumentation.storage;

import uk.ac.ed.inf.icsa.locomotion.instrumentation.Access;

import java.util.TreeSet;

import org.github.jamm.MemoryMeter;

public class TreeSetTrace extends SetTrace {
	public TreeSetTrace(TraceConfiguration configuration) {
		super(configuration, new TreeSet<Access>());
	}
	
	@Override
	public long memoryUsage() {
		return new MemoryMeter().measureDeep(entries);
	}
}