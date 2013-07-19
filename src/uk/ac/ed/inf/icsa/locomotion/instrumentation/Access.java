package uk.ac.ed.inf.icsa.locomotion.instrumentation;

public class Access implements Comparable<Access> {
	private final Kind kind;
	private final int arrayId;
	private final int index;
	private final int number; // this is the i'th access this iteration
	
	public Access(final int arrayId, final int index, final int number, final Kind kind) {
		this.kind = kind;
		this.number = number;
		this.arrayId = arrayId;
		this.index = index;
	}
	
	public String toString() {
		return new StringBuilder().append("kind=").append(kind.toString()).append(" arrayId=").append(arrayId).append(" index=").append(index).append(" number=").append(number).toString();
	}
	
	@Override
	public int hashCode() {
		Long l = Long.parseLong("" + arrayId + index + kind.ordinal());
		return l.hashCode();
	}
	
	public int getNumber() {
		return number;
	}

	@Override
	public int compareTo(Access other) {			
		if (this.getNumber() > other.getNumber())
			return +1;
		
		if (this.getNumber() < other.getNumber())
			return -1;
		
		return 0;
	}
	
	public boolean equals(Access other) {
		return (this.arrayId == other.arrayId) && (this.index == other.index);
	}
}