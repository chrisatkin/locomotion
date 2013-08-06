package uk.ac.ed.inf.icsa.locomotion.instrumentation;

public class Access/* implements Comparable<Access>*/ {
	private final AccessKind kind;
	private final int arrayId;
	private final int index;
	//private final int number; // this is the i'th access this iteration
	
	public Access(final int arrayId, final int index,/* final int number,*/ final AccessKind kind) {
		this.kind = kind;
		//this.number = number;
		this.arrayId = arrayId;
		this.index = index;
	}
	
	public String toString() {
		return new StringBuilder().append("[kind=").append(kind.toString()).append(" arrayId=").append(arrayId).append(" index=").append(index)/*.append(" number=").append(number)*/.append("]").toString();
	}
	
	@Override
	public int hashCode() {
		return arrayId + index;
	}
	
//	public int getNumber() {
//		return number;
//	}
	
	public int getArrayId() {
		return arrayId;
	}
	
	public int getIndex() {
		return index;
	}
	
	public AccessKind getKind() {
		return kind;
	}

//	@Override
//	public int compareTo(Access other) {			
//		if (this.getNumber() > other.getNumber())
//			return +1;
//		
//		if (this.getNumber() < other.getNumber())
//			return -1;
//		
//		return 0;
//	}
	
	public boolean equals(Object other) {
		return (this.arrayId == ((Access) other).arrayId) && (this.index == ((Access) other).index);
	}
}