package uk.ac.ed.inf.icsa.locomotion.results;

import java.io.File;
import java.util.List;
import java.util.Map;

import uk.ac.ed.inf.icsa.locomotion.results.Format.ChartItem6;

public class SixVariables extends FiveVariables {
	protected String y5;

	public SixVariables(File destination, List<Result> results,
			Map<String, String> restrictions, String x, String y1, String y2,
			String y3, String y4, String y5) {
		super(destination, results, restrictions, x, y1, y2, y3, y4);
		this.y5 = y5;
	}
	
	@Override
	public void run() {
		for (Result result: filterResults()) {
			String y5_value = result.getValue(y5);
			String y4_value = result.getValue(y4);
			String y3_value = result.getValue(y3);
			String y1_value = result.getValue(y1);
			String y2_value = result.getValue(y2);
			String x_value = result.getValue(x);
			
			items.add(new ChartItem6<String, String, String, String, String, String>(x_value, y1_value, y2_value, y3_value, y4_value, y5_value));
		}
	}
}