package uk.ac.ed.inf.icsa.locomotion.results;

import java.io.File;
import java.util.List;
import java.util.Map;

import uk.ac.ed.inf.icsa.locomotion.results.Format.ChartItem4;

public class FiveVariables extends FourVariables {
	protected String y4;

	public FiveVariables(File destination, List<Result> results,
			Map<String, String> restrictions, String x, String y1, String y2,
			String y3, String y4) {
		super(destination, results, restrictions, x, y1, y2, y3);
		this.y4 = y4;
	}
	
	@Override
	public void run() {
		for (Result result: filterResults()) {
			String y4_value = result.getValue(y4);
			String y3_value = result.getValue(y3);
			String y1_value = result.getValue(y1);
			String y2_value = result.getValue(y2);
			String x_value = result.getValue(x);
			
			items.add(new ChartItem5<String, String, String, String, String>(x_value, y1_value, y2_value, y3_value, y4_value));
		}
	}

}
