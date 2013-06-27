package uk.ac.ed.inf.icsa.locomotion.instrumentation;

public class Entry {
	
	private long address;
	private long value;

	public Entry(long address, long value) {
		this.address = address;
		this.value = value;
	}
	
	public long getAddress() {
		return address;
	}
	
	public long getValue() {
		return value;
	}

}
