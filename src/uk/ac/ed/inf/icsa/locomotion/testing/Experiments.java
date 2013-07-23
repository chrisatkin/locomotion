package uk.ac.ed.inf.icsa.locomotion.testing;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import uk.ac.ed.inf.icsa.locomotion.instrumentation.*;
import uk.ac.ed.inf.icsa.locomotion.instrumentation.storage.*;
import uk.ac.ed.inf.icsa.locomotion.testing.experiments.*;
import uk.ac.ed.inf.icsa.locomotion.testing.output.*;

final class Experiments {
	private Output output;
	private InstrumentSupport instrument;
	private List<Test> experiments;
	
	private Experiments() {
		this.output = new File("results/");
		//this.output = new Console();
		
		InstrumentSupport.setInstrumentConfiguration(
			new Configuration(
				true,							// enable instrumentation
				HashSetTrace.class,				// storage class
				new TraceConfiguration(),		// storage configuration
				true,							// report memory usage
				output
			)
		);
		
		this.experiments = new LinkedList<Test>() {{
			add(new Test(VectorAddition.class, instrument, new Object[] {new Integer[] {0, 1, 2}, new Integer[] {3, 4, 5}}));
			add(new Test(AllDependent.class, instrument, new Object[] {100}));
			add(new Test(SomeDependent.class, instrument, new Object[] {100, 0.5d}));
			add(new Test(NoneDependent.class, instrument, new Object[] {100}));
		}};
	}
	
	private void run() throws IOException {
		for (boolean instrumentationEnabled: new boolean[] {true, false}) {
			InstrumentSupport.setInstrumentConfiguration(
					new Configuration(
						instrumentationEnabled,			// enable instrumentation
						HashSetTrace.class,				// storage class
						new TraceConfiguration(),		// storage configuration
						false,							// report memory usage
						output
					)
				);
			
			for (Test experiment: experiments) {
				output.open(experiment.getName() + ";instrumentation=" + instrumentationEnabled);
				InstrumentSupport.startTimer();
				experiment.run(output);
				InstrumentSupport.stopTimer();
				output.put("finalmemory=" + (Runtime.getRuntime().maxMemory() - Runtime.getRuntime().freeMemory()));
				output.put("dependencies=" + InstrumentSupport.getDependencies().size());
				output.put("time=" + InstrumentSupport.getTimeDifference());
				output.close();
				
				InstrumentSupport.clean();
			}
		}
		
		
			
	}

	public static void main(String[] args) {
		try {
			new Experiments().run();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
