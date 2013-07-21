package uk.ac.ed.inf.icsa.locomotion.benchmarks.probabilistic;

public final class AllDependent extends Generator {

	public AllDependent(int length) {
		super(length);
	}

	@Override
	public void generate() {
		Integer a[] = new Integer[length];
		Integer b[] = new Integer[length];
		
		for (int i = 0; i < length; i++) {
			a[i] = 0;
			b[i] = i;
		}
		
		setA(a);
		setB(b);
	}
}