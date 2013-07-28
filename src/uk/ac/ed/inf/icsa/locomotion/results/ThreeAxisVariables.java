package uk.ac.ed.inf.icsa.locomotion.results;

import static io.atkin.collections.literals.set;

import java.io.File;
import java.util.List;


public final class ThreeAxisVariables extends Format {
	private String x;
	private String y1;
	private String y2;
	private String test;

	protected ThreeAxisVariables(File destination, List<Result> results, String x, String y1, String y2, String test) {
		super(destination, results);
		this.x = x;
		this.y1 = y1;
		this.y2 = y2;
		this.test = test;
	}

	@Override
	public void run() {
		for (Result result: filterResults(new Condition("name", set(test)))) {
			String y1_value = result.getValue(y1);
			String y2_value = result.getValue(y2);
			String x_value = result.getValue(x);
			
			items.add(new ChartItem<String, String, String>(x_value, y1_value, y2_value));
		}
	}
}
