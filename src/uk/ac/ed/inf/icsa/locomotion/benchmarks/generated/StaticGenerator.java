package uk.ac.ed.inf.icsa.locomotion.benchmarks.generated;

import uk.ac.ed.inf.icsa.locomotion.instrumentation.AccessKind;

public final class StaticGenerator extends Generator {
	private AccessKind k1;
	private AccessKind k2;

	public StaticGenerator(int length, AccessKind k1, AccessKind k2) {
		super(length);
		this.k1 = k1;
		this.k2 = k2;
	}

	@Override
	public void generate() {
		Integer[] array = new Integer[length];
		AccessKind[] first = new AccessKind[length];
		AccessKind[] second = new AccessKind[length];
		
		for (int i = 0; i < length; i++) {
			first[i] = k1;
			second[i] = k2;
			array[i] = i;
		}
		
		this.array = array;
		this.first = first;
		this.second = second;
	}
}