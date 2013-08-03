package uk.ac.ed.inf.icsa.locomotion.results;

import java.io.File;
import java.util.List;
import java.util.Map;

public class FourVariables extends ThreeVariables {
	private String y3;

	protected FourVariables(File destination, List<Result> results,	Map<String, String> restrictions, String x, String y1, String y2, String y3) {
		super(destination, results, restrictions, x, y1, y2);
		this.y3 = y3;
	}
	
	@Override
	public void run() {
		for (Result result: filterResults()) {
			String y3_value = result.getValue(y3);
			String y1_value = result.getValue(y1);
			String y2_value = result.getValue(y2);
			String x_value = result.getValue(x);
			
			items.add(new ChartItem4<String, String, String, String>(x_value, y1_value, y2_value, y3_value));
		}
	}

}
