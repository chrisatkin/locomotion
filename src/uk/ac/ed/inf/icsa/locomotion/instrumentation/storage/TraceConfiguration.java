package uk.ac.ed.inf.icsa.locomotion.instrumentation.storage;

public class TraceConfiguration {
	public Trace getLoopStore() {
		return new HashSetTrace(this);
	}
}
