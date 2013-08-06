package uk.ac.ed.inf.icsa.locomotion.testing;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.apache.commons.math3.random.MersenneTwister;

import uk.ac.ed.inf.icsa.locomotion.benchmarks.hazardmark.Generator;
import uk.ac.ed.inf.icsa.locomotion.benchmarks.hazardmark.HazardGenerator;
import uk.ac.ed.inf.icsa.locomotion.instrumentation.Access;
import uk.ac.ed.inf.icsa.locomotion.instrumentation.AccessKind;
import uk.ac.ed.inf.icsa.locomotion.instrumentation.Configuration;
import uk.ac.ed.inf.icsa.locomotion.instrumentation.InstrumentSupport;
import uk.ac.ed.inf.icsa.locomotion.instrumentation.storage.BloomFilterConfiguration;
import uk.ac.ed.inf.icsa.locomotion.instrumentation.storage.BloomFilterTrace;
import uk.ac.ed.inf.icsa.locomotion.instrumentation.storage.HashSetTrace;
import uk.ac.ed.inf.icsa.locomotion.instrumentation.storage.Trace;
import uk.ac.ed.inf.icsa.locomotion.instrumentation.storage.TraceConfiguration;
import uk.ac.ed.inf.icsa.locomotion.testing.experiments.*;
import uk.ac.ed.inf.icsa.locomotion.testing.output.Console;
import uk.ac.ed.inf.icsa.locomotion.testing.output.File;
import uk.ac.ed.inf.icsa.locomotion.testing.output.Output;

import com.google.common.hash.Funnel;
import com.google.common.hash.PrimitiveSink;

final class Experiments {
	private static class C {
		public int[] a;
		public int[] b;
		public AccessKind[] k;
		public String i;
	}
	
	private Output output;
	private InstrumentSupport instrument;
	private List<Test> experiments;
	
	private Experiments() {
		this.output = new File("results/");
		this.experiments = new LinkedList<>();
		
		long seed = System.nanoTime();
//		this.output = new Console();
		
		
		// generate tests
		C[] tests = new C[11];
		for (int i = 100; i <= 1000; i += 100) {
			Generator g = HazardGenerator.noDependencies(i, seed);
			C c = new C();
			c.a = g.getA();
			c.b = g.getB();
			c.k = g.getAccessPattern();
			c.i = "none-dependent;" + g.toString();
			
			System.out.println(HazardGenerator.statistics(c.b, c.k));
			
			tests[i/100] = c;
		}
		
		for (int i = 100; i <= 1000; i += 100) {
			C c = tests[i / 100];
			experiments.add(new Test(HazardTest.class, instrument, new Object[] { c.a, c.b, c.k, c.i }, output));
		}
	}
	
	private void run() throws IOException, InterruptedException, ExecutionException, TimeoutException {
		Runtime.getRuntime().gc();
		
		for (boolean instrumentationEnabled: new boolean[] {true}) {
			runExactExperiments(instrumentationEnabled);
			runInexactExperiments(instrumentationEnabled);
		}
	}
	
	@SuppressWarnings("serial")
	private void runInexactExperiments(boolean withInstrumentation) throws FileNotFoundException, InterruptedException, ExecutionException, TimeoutException {
		// BloomFilter
		Class<? extends Trace> traceFormat = BloomFilterTrace.class;
		
		for (int i = 100; i <= 1000; i += 100) {
			BloomFilterConfiguration bfc = new BloomFilterConfiguration(i/100, new Funnel<Access>() {

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
				System.out.println("testing " + experiment.getName() + ";instrumentation=" + withInstrumentation + ";storage=" + traceFormat.getSimpleName() + ";" + bfc.toString());
				output.open(experiment.getName() + ";instrumentation=" + withInstrumentation + ";storage=" + traceFormat.getSimpleName() + ";" + bfc.toString());
				InstrumentSupport.startTimer();
				
				experiment.run();
				
				InstrumentSupport.stopTimer();
				output.put("finalmemory=" + (InstrumentSupport.getTraceMemoryUsage()));
				//output.put("finalmemory=" + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				output.put("dependencies=" + InstrumentSupport.getDependencies().size() * 2);
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
				System.out.println("testing " + experiment.getName() + ";instrumentation=" + withInstrumentation + ";storage=" + t.getSimpleName()/* + ";" + traceConfiguration.toString()*/);
				output.open(experiment.getName() + ";instrumentation=" + withInstrumentation + ";storage=" + t.getSimpleName()/* + ";" + traceConfiguration.toString()*/);
				InstrumentSupport.startTimer();

				experiment.run();
				
				InstrumentSupport.stopTimer();
				//output.put("finalmemory=" + (new MemoryMeter().measureDeep(this)));
				output.put("finalmemory=" + (InstrumentSupport.getTraceMemoryUsage()));
				output.put("dependencies=" + InstrumentSupport.getDependencies().size() * 2);
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
