package uk.ac.ed.inf.icsa.locomotion.benchmarks.probabilistic;

public final class NoneDependent extends Generator {

	public NoneDependent(int length) {
		super(length);
	}

	@Override
	public void generate() {
		Integer a[] = new Integer[length];
		Integer b[] = new Integer[length];
		
		for (int i = 0; i < length; i++) {
			a[i] = i;
			b[i] = i;
		}
		
		setA(a);
		setB(b);
	}
}
