package uk.ac.ed.inf.icsa.locomotion.results;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;


public final class ThreeAxisVariables extends Format {
	private String x;
	private String y1;
	private String y2;

	protected ThreeAxisVariables(File destination, List<Result> results, Map<String, String> restrictions, String x, String y1, String y2) {
		super(destination, results, restrictions);
		this.x = x;
		this.y1 = y1;
		this.y2 = y2;
	}

	@Override
	public void run() {
		for (Result result: filterResults()) {
			String y1_value = result.getValue(y1);
			String y2_value = result.getValue(y2);
			String x_value = result.getValue(x);
			
			items.add(new ChartItem<String, String, String>(x_value, y1_value, y2_value));
		}
	}
}
