package uk.ac.ed.inf.icsa.locomotion.instrumentation.trace;

import uk.ac.ed.inf.icsa.locomotion.instrumentation.Entry;

public interface Trace {
	public void add(Entry entry);
	
	public boolean contains(Entry entry);
	
	public int entryCount();
}
