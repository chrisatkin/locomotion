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
	
	private String name, generator;
	private int length_start, length_end, steps, dependencies, vector_start, vector_end, vector_step;
	//private double prob_writewrite, prob_writeread, prob_readwrite;
	private long seed;
	private Output output;
	private InstrumentSupport instrument;
	private List<Test> experiments;
	
	private Experiments(String name, String generator, int length_start, int length_end, int step, int dependencies, int vector_start, int vector_end, int vector_step) {
		this.output = new File("results/");
		this.experiments = new LinkedList<>();
		
		this.name = name;
		this.generator = generator;
		this.length_start = length_start;
		this.length_end = length_end;
		this.steps = step;
		this.dependencies = dependencies;
		this.vector_start = vector_start;
		this.vector_end = vector_end;
		this.vector_step = vector_step;
		
		// generate test
		for (int i = length_start; i <= length_end; i += steps) {
			C c = new C();
			Generator g = getGenerator(i);
			c.a = g.getA();
			c.b = g.getB();
			c.k = g.getAccessPattern();
			c.i = name + ";" + g.toString();
			
			experiments.add(new Test(HazardTest.class, instrument, new Object[] { c.a, c.b, c.k, c.i }, output));
		}
		
//		Generator g = HazardGenerator.allDependentEqual(100000, seed);
//		C c = new C();
//		c.a = g.getA(); c.b = g.getB(); c.k = g.getAccessPattern(); c.i = g.toString();
//		
//		experiments.add(new Test(HazardTest.class, instrument, new Object[] {c.a, c.b, c.k, "all-dependent;" + c.i}, output));
	}
	
	private Generator getGenerator(int l) {
		switch (generator) {
			case "all-dependent":
				return HazardGenerator.allDependentEqual(l, seed);
				
			case "none-dependent":
				return HazardGenerator.noDependencies(l, seed);
				
			case "some-dependent-equal":
				return HazardGenerator.someDependentEqual(l, dependencies, seed);
		}
		
		// public Generator configure(int length, int dependencies, long seed, double prob_writewrite, double prob_writeread, double prob_readwrite)
		
		throw new IllegalArgumentException("Generator " + generator + " unknown");
	}
	
	private void run() throws IOException, InterruptedException, ExecutionException, TimeoutException {
		Runtime.getRuntime().gc();
		
		for (boolean instrumentationEnabled: new boolean[] {true, false}) {
			runExactExperiments(instrumentationEnabled);
			runInexactExperiments(instrumentationEnabled);
		}
	}
	
	@SuppressWarnings("serial")
	private void runInexactExperiments(boolean withInstrumentation) throws FileNotFoundException, InterruptedException, ExecutionException, TimeoutException {
		// BloomFilter
		Class<? extends Trace> traceFormat = BloomFilterTrace.class;
		
		for (int i = vector_start; i <= vector_end; i += vector_step) {
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

				long startTime = System.nanoTime();
				
				experiment.run();
				
				long endTime = System.nanoTime();
				
				output.put("finalmemory=" + Instrumentation.memoryUsage());
				output.put("dependencies=" + Instrumentation.dependencies().size() * 2);
				output.put("time=" + (endTime - startTime));
				output.close();
				
				Instrumentation.clean();
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

				long startTime = System.nanoTime();
				
				experiment.run();
				
				long endTime = System.nanoTime();
				
				output.put("finalmemory=" + Instrumentation.memoryUsage());
				output.put("dependencies=" + Instrumentation.dependencies().size() * 2);
				output.put("time=" + (endTime - startTime));
				output.close();
				
				Instrumentation.clean();
				Runtime.getRuntime().gc();
			}
		}
	}

	public static void main(String[] args) {
		try {
			// parse arguments
			String name = args[0];
			String generator = args[1];
			int length_start = Integer.parseInt(args[2]);
			int length_end = Integer.parseInt(args[3]);
			int step = Integer.parseInt(args[4]);
			int dependencies = Integer.parseInt(args[5]);
			int vector_start = Integer.parseInt(args[6]);
			int vector_end = Integer.parseInt(args[7]);
			int vector_step = Integer.parseInt(args[8]);
			
			new Experiments(name, generator, length_start, length_end, step, dependencies, vector_start, vector_end, vector_step).run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}