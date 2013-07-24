package uk.ac.ed.inf.icsa.locomotion.testing;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import com.google.common.hash.Funnel;
import com.google.common.hash.PrimitiveSink;

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
			//add(new Test(VectorAddition.class, instrument, new Object[] {new Integer[] {0, 1, 2}, new Integer[] {3, 4, 5}}));
			//add(new Test(AllDependent.class, instrument, new Object[] {100}));
			//add(new Test(SomeDependent.class, instrument, new Object[] {100, 0.5d}));
			//add(new Test(NoneDependent.class, instrument, new Object[] {100}));
		}};
		
		// add probabilistic tests
		for (int i = 0; i <= 1000; i += 50) {
			experiments.add(new Test(AllDependent.class, instrument, new Object[] {i}));
			experiments.add(new Test(NoneDependent.class, instrument, new Object[] {i}));
			
			//for (double p = 0.1d; p <= 0.9d; p += 0.1d) {
			for (int p_i = 1; p_i <= 9; p_i++) {
				double p = p_i / 10.0d;
				experiments.add(new Test(SomeDependent.class, instrument, new Object[] {i, p}));
			}
		}
	}
	
	private void run() throws IOException {
		for (boolean instrumentationEnabled: new boolean[] {true, false}) {
			runExactExperiments(instrumentationEnabled);
			runInexactExperiments(instrumentationEnabled);
		}
	}
	
	@SuppressWarnings("serial")
	private void runInexactExperiments(boolean withInstrumentation) throws FileNotFoundException {
		// BloomFilter
		Class<? extends Trace> traceFormat = BloomFilterTrace.class;
		
		for (int i = 50; i <= 10000; i += 50) {
			BloomFilterConfiguration bfc = new BloomFilterConfiguration(i, new Funnel<Access>() {
				@Override
				public void funnel(Access access, PrimitiveSink sink) {
					sink.putInt(access.getArrayId())
						.putInt(access.getIndex());
				}});
			
			InstrumentSupport.setInstrumentConfiguration(new Configuration(
				withInstrumentation,
				traceFormat,
				bfc,
				withInstrumentation,
				output
			));
			
			for (Test experiment: experiments) {
				output.open(experiment.getName() + ";instrumentation=" + withInstrumentation + ";storage=" + traceFormat.getSimpleName() + ";storageconf=" + bfc.toString());
				InstrumentSupport.startTimer();
				experiment.run(output);
				InstrumentSupport.stopTimer();
				output.put("finalmemory=" + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				output.put("dependencies=" + InstrumentSupport.getDependencies().size());
				output.put("time=" + InstrumentSupport.getTimeDifference());
				output.close();
				
				InstrumentSupport.clean();
				Runtime.getRuntime().gc();
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private void runExactExperiments(boolean withInstrumentation) throws FileNotFoundException {
		TraceConfiguration traceConfiguration = new TraceConfiguration();
		
		for(Class<?> t: new Class<?>[] { HashSetTrace.class }) {
			Class<? extends Trace> traceFormat = (Class<? extends Trace>) t;
			
			InstrumentSupport.setInstrumentConfiguration(new Configuration(
				withInstrumentation,		// instrumentation enabled
				traceFormat,
				traceConfiguration,
				withInstrumentation,
				output));
			
			for (Test experiment: experiments) {
				output.open(experiment.getName() + ";instrumentation=" + withInstrumentation + ";storage=" + t.getSimpleName() + ";storageconf=" + traceConfiguration.toString());
				InstrumentSupport.startTimer();
				experiment.run(output);
				InstrumentSupport.stopTimer();
				output.put("finalmemory=" + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				output.put("dependencies=" + InstrumentSupport.getDependencies().size());
				output.put("time=" + InstrumentSupport.getTimeDifference());
				output.close();
				
				InstrumentSupport.clean();
				Runtime.getRuntime().gc();
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
