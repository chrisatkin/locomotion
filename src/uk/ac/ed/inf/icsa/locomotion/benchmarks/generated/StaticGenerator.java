package uk.ac.ed.inf.icsa.locomotion.benchmarks.generated;

import uk.ac.ed.inf.icsa.locomotion.instrumentation.Kind;

public final class StaticGenerator extends Generator {
	private Kind k1;
	private Kind k2;

	public StaticGenerator(int length, Kind k1, Kind k2) {
		super(length);
		this.k1 = k1;
		this.k2 = k2;
	}

	@Override
	public void generate() {
		Integer[] array = new Integer[length];
		Kind[] first = new Kind[length];
		Kind[] second = new Kind[length];
		
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