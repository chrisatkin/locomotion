package uk.ac.ed.inf.icsa.locomotion.testing;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import uk.ac.ed.inf.icsa.locomotion.testing.experiments.*;
import uk.ac.ed.inf.icsa.locomotion.testing.output.Console;
import uk.ac.ed.inf.icsa.locomotion.testing.output.Output;

public final class Experiments {
	private Class<? extends Output> output;
	private List<Test> experiments;
	
	private Experiments() {
		this.experiments = new LinkedList<Test>() {{
			add(new Test(ExactVectorAddition.class, new Object[] {}));
		}};
	}
	
	private void run() throws IOException {
		
	}

	public static void main(String[] args) {
		try {
			new Experiments().run();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
