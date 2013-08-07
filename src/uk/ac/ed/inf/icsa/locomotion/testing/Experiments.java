package uk.ac.ed.inf.icsa.locomotion.testing;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
import uk.ac.ed.inf.icsa.locomotion.instrumentation.Instrumentation;
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
		
		long seed = 938839;
//		this.output = new Console();
		
		
		// generate test
		int scale = 1000;
		int base = 50;
		
		for (int i = 100000; i <= 1000000; i += 100000) {
			C c = new C();
			Generator g = HazardGenerator.allDependentEqual(i, seed);
			c.a = g.getA();
			c.b = g.getB();
			c.k = g.getAccessPattern();
			c.i = "all-dependent;" + g.toString();
			
			experiments.add(new Test(HazardTest.class, instrument, new Object[] { c.a, c.b, c.k, c.i }, output));
		}
		
//		Generator g = HazardGenerator.allDependentEqual(100000, seed);
//		C c = new C();
//		c.a = g.getA(); c.b = g.getB(); c.k = g.getAccessPattern(); c.i = g.toString();
//		
//		experiments.add(new Test(HazardTest.class, instrument, new Object[] {c.a, c.b, c.k, "all-dependent;" + c.i}, output));
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
		
		for (int i = 100000; i <= 100000000; i += 100000) {
			BloomFilterConfiguration bfc = new BloomFilterConfiguration(i, new Funnel<Access>() {

				@Override
				public void funnel(Access access, PrimitiveSink sink) {
					sink.putInt(access.getArrayId())
						.putInt(access.getIndex());
				}});
			
			Instrumentation.setConfiguration(new Configuration(
				withInstrumentation,
				traceFormat,
				bfc,
				false,
				output
			));

			for (Test experiment: experiments) {
				System.out.println("testing " + experiment.getName() + ";instrumentation=" + withInstrumentation + ";storage=" + traceFormat.getSimpleName() + ";" + bfc.toString());
				output.open(experiment.getName() + ";instrumentation=" + withInstrumentation + ";storage=" + traceFormat.getSimpleName() + ";" + bfc.toString());
				//InstrumentSupport.startTimer();
				long startTime = System.nanoTime();
				
				experiment.run();
				
				long endTime = System.nanoTime();
				//InstrumentSupport.stopTimer();
				//output.put("finalmemory=" + (InstrumentSupport.getTraceMemoryUsage()));
				//output.put("finalmemory=" + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				//output.put("dependencies=" + InstrumentSupport.getDependencies().size() * 2);
				
				output.put("finalmemory=" + Instrumentation.memoryUsage());
				output.put("dependencies=" + Instrumentation.dependencies().size() * 2);
				output.put("time=" + (endTime - startTime));
				output.close();
				
				Instrumentation.clean();
//				InstrumentSupport.clean();
				Runtime.getRuntime().gc();
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private void runExactExperiments(boolean withInstrumentation) throws FileNotFoundException, InterruptedException, ExecutionException, TimeoutException {
		TraceConfiguration traceConfiguration = new TraceConfiguration();
		
		for(Class<?> t: new Class<?>[] { HashSetTrace.class }) {
			Class<? extends Trace> traceFormat = (Class<? extends Trace>) t;
			
			Instrumentation.setConfiguration(new Configuration(
				withInstrumentation,		// instrumentation enabled
				traceFormat,
				traceConfiguration,
				false,
				output));
			
			for (Test experiment: experiments) {	
				System.out.println("testing " + experiment.getName() + ";instrumentation=" + withInstrumentation + ";storage=" + t.getSimpleName()/* + ";" + traceConfiguration.toString()*/);
				output.open(experiment.getName() + ";instrumentation=" + withInstrumentation + ";storage=" + t.getSimpleName()/* + ";" + traceConfiguration.toString()*/);
				//InstrumentSupport.startTimer();
				long startTime = System.nanoTime();
				
				experiment.run();
				
				long endTime = System.nanoTime();
				//InstrumentSupport.stopTimer();
				//output.put("finalmemory=" + (InstrumentSupport.getTraceMemoryUsage()));
				//output.put("finalmemory=" + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				//output.put("dependencies=" + InstrumentSupport.getDependencies().size() * 2);
				
				output.put("finalmemory=" + Instrumentation.memoryUsage());
				output.put("dependencies=" + Instrumentation.dependencies().size() * 2);
				output.put("time=" + (endTime - startTime));
				output.close();
				
				Instrumentation.clean();
//				InstrumentSupport.clean();
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