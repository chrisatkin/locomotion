package uk.ac.ed.inf.icsa.locomotion.instrumentation.storage;

import java.util.Set;

import uk.ac.ed.inf.icsa.locomotion.instrumentation.Access;

abstract class SetTrace extends Trace {
	private Set<Access> entries;

	public SetTrace(TraceConfiguration configuration, Set<Access> set) {
		super(configuration);
		this.entries = set;
	}

	@Override
	public void add(Access entry) {
		entries.add(entry);
	}

	@Override
	public boolean contains(Access entry) {
//		return entries.contains(entry);
		
		for (Access access: entries)
			if (access.equals(entry))
				return true;
		
		return false;
	}
	
	@Override
	public int size() {
		return entries.size();
	}
	
	public String toString() {
		return entries.toString();
	}
}
