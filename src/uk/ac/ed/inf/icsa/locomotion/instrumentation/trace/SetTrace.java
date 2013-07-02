package uk.ac.ed.inf.icsa.locomotion.instrumentation.trace;

import java.util.Set;

import uk.ac.ed.inf.icsa.locomotion.instrumentation.Entry;

abstract class SetTrace implements Trace {
	private Set<Entry> entries;

	public SetTrace(Set<Entry> set) {
		this.entries = set;
	}

	@Override
	public void add(Entry entry) {
		entries.add(entry);
	}

	@Override
	public boolean contains(Entry entry) {
		return entries.contains(entry);
	}

	@Override
	public int entryCount() {
		return entries.size();
	}

}
