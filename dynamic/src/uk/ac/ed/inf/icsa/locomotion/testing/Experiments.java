package uk.ac.ed.inf.icsa.locomotion.testing;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.apache.commons.math3.random.MersenneTwister;

import com.sun.org.apache.bcel.internal.generic.NEW;

import uk.ac.ed.inf.icsa.locomotion.benchmarks.hazardmark.Generator;
import uk.ac.ed.inf.icsa.locomotion.benchmarks.hazardmark.HazardGenerator;
import uk.ac.ed.inf.icsa.locomotion.instrumentation.AccessKind;
import uk.ac.ed.inf.icsa.locomotion.instrumentation.Configuration;
import uk.ac.ed.inf.icsa.locomotion.instrumentation.InstrumentSupport;
import uk.ac.ed.inf.icsa.locomotion.instrumentation.Instrumentation;
import uk.ac.ed.inf.icsa.locomotion.instrumentation.storage.BloomFilterConfiguration;
import uk.ac.ed.inf.icsa.locomotion.instrumentation.storage.*;
import uk.ac.ed.inf.icsa.locomotion.instrumentation.storage.HashSetTrace;
import uk.ac.ed.inf.icsa.locomotion.instrumentation.storage.HashSetWellConfigured;
import uk.ac.ed.inf.icsa.locomotion.instrumentation.storage.Trace;
import uk.ac.ed.inf.icsa.locomotion.instrumentation.storage.TraceConfiguration;
import uk.ac.ed.inf.icsa.locomotion.testing.experiments.*;
import uk.ac.ed.inf.icsa.locomotion.testing.output.Console;
import uk.ac.ed.inf.icsa.locomotion.testing.output.File;
import uk.ac.ed.inf.icsa.locomotion.testing.output.Output;

final class Experiments {
	private static class C {
		public int[] a;
		public int[] b;
		public AccessKind[] k;
		public String i;
	}
	
	private String name, generator;
	private int length_start, length_end, steps, vector_start, vector_end, vector_step, computation_start, computation_end, computation_step;
	double dependencies;
	//private double prob_writewrite, prob_writeread, prob_readwrite;
	private long seed;
	private Output output;
	private InstrumentSupport instrument;
	private List<Test> experiments;
	private int instr_mode;
	private double fpp;
	
	private Experiments(String name, String generator, int length_start, int length_end, int step, double dependencies, int vector_start, int vector_end, int vector_step, int instr_mode, int computation_start, int computation_end, int computation_step, double fpp) {
		this.output = new File("results/");
//		this.output = new Console();
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
		this.instr_mode = instr_mode;
		this.computation_start = computation_start;
		this.computation_end = computation_end;
		this.computation_step = computation_step;
		this.fpp = fpp;
		
		// generate test
		for (int i = length_start; i <= length_end; i += steps) {
			C c = new C();
			Generator g = getGenerator(i);
			c.a = g.getA();
			c.b = g.getB();
			c.k = g.getAccessPattern();
			c.i = name + ";" + g.toString();
			
			experiments.add(new Test(HazardTest.class, instrument, new Object[] { c.a, c.b, c.k, c.i }, output, c.a.length));
		}
		
//		Generator g = HazardGenerator.noDependencies(1000, seed);
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
				return HazardGenerator.someDependentEqual(l, ((int) (dependencies * l)), seed);
		}
		
		// public Generator configure(int length, int dependencies, long seed, double prob_writewrite, double prob_writeread, double prob_readwrite)
		
		throw new IllegalArgumentException("Generator " + generator + " unknown");
	}
	
	private void run() throws IOException, InterruptedException, ExecutionException, TimeoutException {
		Runtime.getRuntime().gc();
		
		boolean[] run_using = new boolean[] {};
		
		if (instr_mode == 0)
			run_using = new boolean[] {};
		
		if (instr_mode == 1)
			run_using = new boolean[] {true};
		
		if (instr_mode == 2)
			run_using = new boolean[] {false};
		
		if (instr_mode == 3)
			run_using = new boolean[] {true, false};
		
		for (boolean instrumentationEnabled: run_using) {
			runInexactMultiples(instrumentationEnabled);
//			runExactExperiments(instrumentationEnabled);
//			runInexactExperiments(instrumentationEnabled);
//			runExpandingExperiments(instrumentationEnabled);
		}
	}
	
	public void runExpandingExperiments(boolean withInstrumentation) throws FileNotFoundException {
		Class<? extends Trace> traceFormat = ExpandingBloomFilterTrace.class;
		
		for (Test test: experiments) {
			for (int i = vector_start; i <= vector_end; i += vector_step) {
				BloomFilterConfiguration bfc = new BloomFilterConfiguration(i, new BloomFunnel());
				Instrumentation.setConfiguration(new Configuration(withInstrumentation, traceFormat, bfc, false, output));
				
				System.out.println("testing " + test.getName() + ";instrumentation=" + withInstrumentation + ";storage=" + traceFormat.getSimpleName() + ";" + bfc.toString());
				
				output.open(test.getName() + ";instrumentation=" + withInstrumentation +";storage=" + traceFormat.getSimpleName() + ";" + bfc.toString());
				
				long startTime = System.nanoTime();
				test.run();
				long endTime = System.nanoTime();
				
				output.put("finalmemory=" + Instrumentation.memoryUsage());
				output.put("dependencies=" + Instrumentation.dependencies().size() * 2);
				output.put("time=" + (endTime - startTime));
				output.close();
				
				Instrumentation.clean();
			}
		}
	}
	
	public void runInexactExperiments(boolean withInstrumentation) throws FileNotFoundException, InterruptedException, ExecutionException, TimeoutException {
		Class<? extends Trace> traceFormat = BloomFilterTrace.class;
		
		for (Test test: experiments) {
			for (int i = vector_start; i <= vector_end; i += vector_step) {
				for (int j = computation_start; j <= computation_end; j += computation_step) {
					BloomFilterConfiguration bfc = new BloomFilterConfiguration(i, new BloomFunnel(), fpp);
					Instrumentation.setConfiguration(new Configuration(withInstrumentation, traceFormat, bfc, false, output));
//					((HazardTestComputation) test.getExperiment()).setComputationAmount(j);
					
					System.out.println("testing " + test.getName() + ";instrumentation=" + withInstrumentation + ";storage=" + traceFormat.getSimpleName() + ";" + bfc.toString());
					
					output.open(test.getName() + ";instrumentation=" + withInstrumentation +";storage=" + traceFormat.getSimpleName() + ";" + bfc.toString());
					
					long startTime = System.nanoTime();
					test.run();
					long endTime = System.nanoTime();
					
					output.put("finalmemory=" + Instrumentation.memoryUsage());
					output.put("dependencies=" + Instrumentation.dependencies().size() * 2);
					output.put("time=" + (endTime - startTime));
					output.close();
					
					Instrumentation.clean();
				}
			}
		}
	}
	
	public void runInexactMultiples(boolean withInstrumentation) throws FileNotFoundException, InterruptedException, ExecutionException, TimeoutException {
		Class<? extends Trace> traceFormat = BloomFilterTrace.class;
		
		for (Test experiment: experiments) {
			for (int i = vector_start; i <= vector_end; i+= vector_step) {
				//for (int j = computation_start; j <= computation_end; j += computation_step) {
				
					BloomFilterConfiguration bfc = new BloomFilterConfiguration(experiment.getLength() * i, new BloomFunnel(), fpp);
					Instrumentation.setConfiguration(new Configuration(withInstrumentation, traceFormat, bfc, false, output));
//					((HazardTestComputation) experiment.getExperiment()).setComputationAmount(j);
					
					System.out.println("testing " + experiment.getName() + ";fpp=" + fpp + ";instrumentation=" + withInstrumentation + ";storage=" + traceFormat.getSimpleName() + ";" + bfc.toString());
					
					output.open(experiment.getName() + ";fpp=" + fpp + ";instrumentation=" + withInstrumentation + ";storage=" + traceFormat.getSimpleName() + ";" + bfc.toString());
	
					long startTime = System.nanoTime();
					
					experiment.run();
					
					long endTime = System.nanoTime();
					
					output.put("finalmemory=" + Instrumentation.memoryUsage());
					output.put("dependencies=" + Instrumentation.dependencies().size() * 2);
					output.put("time=" + (endTime - startTime));
					output.close();
					
					Instrumentation.clean();
				//}
			}
		}
	}
	

	
	@SuppressWarnings("unchecked")
	private void runExactExperiments(boolean withInstrumentation) throws FileNotFoundException, InterruptedException, ExecutionException, TimeoutException {
		for (Class<?> storageBackend: new Class<?>[] { HashSetTrace.class, HashSetWellConfigured.class }) {
			for (Test experiment: experiments) {
				for (int j = computation_start; j <= computation_end; j += computation_step) {
					Instrumentation.setConfiguration(new Configuration(
						withInstrumentation,		// instrumentation enabled
						(Class<? extends Trace>) storageBackend,
						new TraceConfiguration(experiment.getLength()),
						false,
						output
					));
					
//					((HazardTestComputation) experiment.getExperiment()).setComputationAmount(j);
					System.out.println("testing " + experiment.getName() + ";instrumentation=" + withInstrumentation + ";storage=" + storageBackend.getSimpleName()/* + ";" + traceConfiguration.toString()*/);
					output.open(experiment.getName() + ";instrumentation=" + withInstrumentation + ";storage=" + storageBackend.getSimpleName()/* + ";" + traceConfiguration.toString()*/);
		
					long startTime = System.nanoTime();
		
					experiment.run();
		
					long endTime = System.nanoTime();
		
					output.put("finalmemory=" + Instrumentation.memoryUsage());
					output.put("dependencies=" + Instrumentation.dependencies().size() * 2);
					output.put("time=" + (endTime - startTime));
					output.close();
		
					Instrumentation.clean();
				}
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
			double dependencies = Double.parseDouble(args[5]);
			int vector_start = Integer.parseInt(args[6]);
			int vector_end = Integer.parseInt(args[7]);
			int vector_step = Integer.parseInt(args[8]);
			
			int instr_mode = 3;
			if (args.length == 10)
				instr_mode = Integer.parseInt(args[9]);
			
			int computation_start = Integer.parseInt(args[10]);
			int computation_end = Integer.parseInt(args[11]);
			int computation_step = Integer.parseInt(args[12]);
			double fpp = Double.parseDouble(args[13]);
			
			new Experiments(name, generator, length_start, length_end, step, dependencies, vector_start, vector_end, vector_step, instr_mode, computation_start, computation_end, computation_step, fpp).run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}