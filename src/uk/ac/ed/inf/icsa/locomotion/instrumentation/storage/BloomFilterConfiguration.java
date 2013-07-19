package uk.ac.ed.inf.icsa.locomotion.instrumentation.storage;

import uk.ac.ed.inf.icsa.locomotion.instrumentation.Access;

import com.google.common.hash.Funnel;

public final class BloomFilterConfiguration extends TraceConfiguration {
	private int expectedInsertations;
	private Funnel<Access> funnel;
	
	public BloomFilterConfiguration(int expectedInsertations, Funnel<Access> funnel) {
		this.expectedInsertations = expectedInsertations;
		this.funnel = funnel;
	}
	
	public int getExpectedInsertations() {
		return expectedInsertations;
	}
	
	public Funnel<Access> getFunnel() {
		return funnel;
	}
}
