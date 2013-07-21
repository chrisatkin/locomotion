package uk.ac.ed.inf.icsa.locomotion.benchmarks.probabilistic;

public final class SomeDependent extends Generator {
	private final double probability;

	public SomeDependent(int length, double probability) {
		super(length);
		
		assert probability >= 0.0d && probability <= 1.0d: "probability must be between 0.0 and 1.0 inclusive";
		this.probability = probability;
	}

	@Override
	public void generate() {
		Integer a[] = new Integer[length];
		Integer b[] = new Integer[length];
		
		for (int i = 0; i < length; i++) {
			b[i] = i;
			
			if (Math.random() <= probability)
				a[i] = 0;
			else
				a[i] = i;
		}
		
		setA(a);
		setB(b);
	}
}