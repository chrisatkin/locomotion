package uk.ac.ed.inf.icsa.locomotion.instrumentation;

public class Entry {
	
	private int address;
	private int value;
	
	public Entry(int address) {
		this.address = address;
		this.value = -0;
	}

	public Entry(int address, int value) {
		this.address = address;
		this.value = value;
	}
	
	public int getAddress() {
		return address;
	}
	
	public int getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		return new StringBuilder().append("address=").append(address).append(" value=").append(value).toString();
	}
}