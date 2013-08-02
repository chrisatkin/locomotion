package uk.ac.ed.inf.icsa.locomotion.testing;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.github.jamm.MemoryMeter;

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
		this.experiments = new LinkedList<>();
		//this.output = new Console();
		
		InstrumentSupport.setInstrumentConfiguration(
			new Configuration(
				true,							// enable instrumentation
				HashSetTrace.class,				// storage class
				new TraceConfiguration(),		// storage configuration
				false,							// report memory usage
				output
			)
		);
		
		// add probabilistic tests
		for (int i = 1000; i <= 10000; i += 1000) {
			// Basic tests
			//experiments.add(new Test(AllDependent.class, instrument, new Object[] {i}, output));
			//experiments.add(new Test(NoneDependent.class, instrument, new Object[] {i}, output));
			
			// Probabilistic tests
			//experiments.add(new Test(FractionalDependent.class, instrument, new Object[] {i, 300, 300, 300}, output));

			// vector addition
			Integer[] a = new Integer[i];
			Integer[] b = new Integer[i];
			for (int j = 0; j < i; j++) {
				a[j] = (int) Math.random() * j;
				b[j] = (int) Math.random() * j;
			}
			//experiments.add(new Test(VectorAddition.class, instrument, new Object[] {a, b}, output));
			
			experiments.add(new Test(NBody.class, instrument, new Object[]{ "nbody-data/2body.txt", i }, output));
		}
		
		
		//experiments.add(new Test(NBody.class, instrument, new Object[]{ "nbody-data/3body.txt", 1000 }, output));
		//experiments.add(new Test(NBody.class, instrument, new Object[]{ "nbody-data/4body.txt", 1000 }, output));
	}
	
	private void run() throws IOException, InterruptedException, ExecutionException, TimeoutException {
		for (boolean instrumentationEnabled: new boolean[] {true, false}) {
			runExactExperiments(instrumentationEnabled);
			//runInexactExperiments(instrumentationEnabled);
		}
	}
	
	@SuppressWarnings("serial")
	private void runInexactExperiments(boolean withInstrumentation) throws FileNotFoundException, InterruptedException, ExecutionException, TimeoutException {
		// BloomFilter
		Class<? extends Trace> traceFormat = BloomFilterTrace.class;
		
		for (int i = 100; i <= 1000; i += 100) {
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
				false,
				output
			));

			for (Test experiment: experiments) {
				System.out.println("testing " + experiment.getName() + ";instrumentation=" + withInstrumentation + ";storage=" + traceFormat.getSimpleName() + ";storageconf=" + bfc.toString());
				output.open(experiment.getName() + ";instrumentation=" + withInstrumentation + ";storage=" + traceFormat.getSimpleName() + ";storageconf=" + bfc.toString());
				InstrumentSupport.startTimer();
				
				experiment.run();
				
				InstrumentSupport.stopTimer();
				//output.put("finalmemory=" + (new MemoryMeter().measureDeep(this)));
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
	private void runExactExperiments(boolean withInstrumentation) throws FileNotFoundException, InterruptedException, ExecutionException, TimeoutException {
		TraceConfiguration traceConfiguration = new TraceConfiguration();
		
		for(Class<?> t: new Class<?>[] { HashSetTrace.class }) {
			Class<? extends Trace> traceFormat = (Class<? extends Trace>) t;
			
			InstrumentSupport.setInstrumentConfiguration(new Configuration(
				withInstrumentation,		// instrumentation enabled
				traceFormat,
				traceConfiguration,
				false,
				output));
			
			for (Test experiment: experiments) {	
				System.out.println("testing " + experiment.getName() + ";instrumentation=" + withInstrumentation + ";storage=" + t.getSimpleName() + ";storageconf=" + traceConfiguration.toString());
				output.open(experiment.getName() + ";instrumentation=" + withInstrumentation + ";storage=" + t.getSimpleName() + ";storageconf=" + traceConfiguration.toString());
				InstrumentSupport.startTimer();

				experiment.run();
				
				InstrumentSupport.stopTimer();
				//output.put("finalmemory=" + (new MemoryMeter().measureDeep(this)));
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
