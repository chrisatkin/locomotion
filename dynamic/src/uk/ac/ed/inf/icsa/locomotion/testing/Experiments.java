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
import uk.ac.ed.inf.icsa.locomotion.instrumentation.storage.BloomFilterTrace;
import uk.ac.ed.inf.icsa.locomotion.instrumentation.storage.HashSetTrace;
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
	private int length_start, length_end, steps, vector_start, vector_end, vector_step;
	double dependencies;
	//private double prob_writewrite, prob_writeread, prob_readwrite;
	private long seed;
	private Output output;
	private InstrumentSupport instrument;
	private List<Test> experiments;
	private int instr_mode;
	
	private Experiments(String name, String generator, int length_start, int length_end, int step, double dependencies, int vector_start, int vector_end, int vector_step, int instr_mode) {
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
		
		run_using = new boolean[] {true};
		
		for (boolean instrumentationEnabled: run_using) {
			runInexactMultiples(instrumentationEnabled);
			runExactExperiments(instrumentationEnabled);
			//runInexactExperiments(instrumentationEnabled);
			
		}
	}
	
	public void runInexactMultiples(boolean withInstrumentation) throws FileNotFoundException, InterruptedException, ExecutionException, TimeoutException {
		Class<? extends Trace> traceFormat = BloomFilterTrace.class;
		
		for (Test experiment: experiments) {
			HazardTest ht = (HazardTest) experiment.getExperiment();
			int length = ht.getLength();
			
			for (int i = vector_start; i <= vector_end; i+= vector_step) {
				//System.out.println("factor=" + i + " vector=" + (length * i));
				
				BloomFilterConfiguration bfc = new BloomFilterConfiguration(length * i, new BloomFunnel());
				Instrumentation.setConfiguration(new Configuration(withInstrumentation, traceFormat, bfc, false, output));
				
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
			}
		}
	}
	

	
	@SuppressWarnings("unchecked")
	private void runExactExperiments(boolean withInstrumentation) throws FileNotFoundException, InterruptedException, ExecutionException, TimeoutException {
		Class<? extends Trace> t = HashSetTrace.class;

		for (Test experiment: experiments) {	
			TraceConfiguration traceConfiguration = new TraceConfiguration();
			Instrumentation.setConfiguration(new Configuration(
					withInstrumentation,		// instrumentation enabled
					t,
					traceConfiguration,
					false,
					output));

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
			
			new Experiments(name, generator, length_start, length_end, step, dependencies, vector_start, vector_end, vector_step, instr_mode).run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}