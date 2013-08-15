package uk.ac.ed.inf.icsa.locomotion.instrumentation.storage;

import uk.ac.ed.inf.icsa.locomotion.instrumentation.Access;

import com.google.common.hash.Funnel;

public final class BloomFilterConfiguration extends TraceConfiguration {
	private int expectedInsertations;
	private Funnel<Access> funnel;
	private double fpp;
	
	public BloomFilterConfiguration(int expectedInsertations, Funnel<Access> funnel) {
		super(expectedInsertations);
		this.expectedInsertations = expectedInsertations;
		this.funnel = funnel;
		this.fpp = 0.03;
	}
	
	public BloomFilterConfiguration(int expectedInsertions, Funnel<Access> funnel, double fpp) {
		super(expectedInsertions);
		this.expectedInsertations = expectedInsertions;
		this.funnel = funnel;
		this.fpp = fpp;
	}
	
	public double getFpp() {
		return fpp;
	}
	
	public int getExpectedInsertations() {
		return expectedInsertations;
	}
	
	public Funnel<Access> getFunnel() {
		return funnel;
	}
	
	@Override
	public String toString() {
		return "bitvector=" + expectedInsertations;
	}
}
