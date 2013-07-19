package uk.ac.ed.inf.icsa.locomotion.instrumentation.storage;

import uk.ac.ed.inf.icsa.locomotion.instrumentation.Access;

public abstract class Trace {
	protected TraceConfiguration configuration;
	
	public Trace(TraceConfiguration configuration) {
		this.configuration = configuration;
	}
	
	public abstract void add(Access entry);
	
	public abstract boolean contains(Access entry);
	
	public abstract int size();
}
