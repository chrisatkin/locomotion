package uk.ac.ed.inf.icsa.locomotion.results;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class ThreeVariables extends Format {
	protected String x;
	protected String y1;
	protected String y2;

	protected ThreeVariables(File destination, List<Result> results, Map<String, String> restrictions, String x, String y1, String y2) {
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
			
			items.add(new ChartItem3<String, String, String>(x_value, y1_value, y2_value));
		}
	}
}
