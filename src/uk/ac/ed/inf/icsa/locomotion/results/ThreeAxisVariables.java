package uk.ac.ed.inf.icsa.locomotion.results;

import java.io.File;
import java.util.List;
import java.util.Set;


public final class ThreeAxisVariables extends Format {
	private String x;
	private String y1;
	private String y2;
	private Set<String> tests;

	protected ThreeAxisVariables(File destination, List<Result> results, String x, String y1, String y2, Set<String> tests) {
		super(destination, results);
		this.x = x;
		this.y1 = y1;
		this.y2 = y2;
		this.tests = tests;
	}

	@Override
	public void run() {
		for (Result result: filterResults(new Condition("name", tests))) {
			String y1_value = result.getValue(y1);
			String y2_value = result.getValue(y2);
			String x_value = result.getValue(x);
			
			items.add(new ChartItem<String, String, String>(x_value, y1_value, y2_value));
		}
	}
}
