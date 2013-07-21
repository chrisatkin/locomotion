package uk.ac.ed.inf.icsa.locomotion.benchmarks.basic;

import static uk.ac.ed.inf.icsa.locomotion.utilities.DebugUtilities.noop;
import static io.atkin.collections.literals.IntArray;
import static io.atkin.io.console.println;

import java.util.Arrays;

import com.google.common.hash.Funnel;
import com.google.common.hash.PrimitiveSink;

import uk.ac.ed.inf.icsa.locomotion.benchmarks.probabilistic.AllDependent;
import uk.ac.ed.inf.icsa.locomotion.benchmarks.probabilistic.Generator;
import uk.ac.ed.inf.icsa.locomotion.benchmarks.probabilistic.SomeDependent;
import uk.ac.ed.inf.icsa.locomotion.instrumentation.Access;
import uk.ac.ed.inf.icsa.locomotion.instrumentation.Configuration;
import uk.ac.ed.inf.icsa.locomotion.instrumentation.InstrumentSupport;
import uk.ac.ed.inf.icsa.locomotion.instrumentation.Kind;
import uk.ac.ed.inf.icsa.locomotion.instrumentation.storage.BloomFilterConfiguration;
import uk.ac.ed.inf.icsa.locomotion.instrumentation.storage.BloomFilterTrace;
import uk.ac.ed.inf.icsa.locomotion.instrumentation.storage.HashSetTrace;
import uk.ac.ed.inf.icsa.locomotion.instrumentation.storage.Trace;
import uk.ac.ed.inf.icsa.locomotion.instrumentation.storage.TraceConfiguration;

public class CodeSamples {
	public static Integer[] vectorAddition(Integer[] a, Integer[] b) {
		assert a.length == b.length: "vectors must be same length";
		
		Integer[] c = new Integer[a.length];
		
		for (int i = 0; i < a.length; i++) {
			Integer currentA = InstrumentSupport.arrayLookup(a, i, i, 1);
			Integer currentB = InstrumentSupport.arrayLookup(b, i, i, 1);
			Integer result = currentA + currentB;
			
			InstrumentSupport.arrayWrite(c, i, result, i, 1);
		}
		
		return c;
	}
	
	public static Integer[] loopDependency(Integer[]  a, Integer[] b) {
		assert a.length == b.length: "vectors must be same length";
		
		Integer[] c = new Integer[a.length];
		
		for (int i = 0; i < a.length; i++) {
			Integer val = InstrumentSupport.arrayLookup(b, InstrumentSupport.arrayLookup(a, i, i, 2), i, 2);
			
			InstrumentSupport.arrayWrite(c, i, val, i, 2);
		}
		
		return c;
	}
	
	public static void test() {
		//println(Arrays.toString(vectorAddition(IntArray(0, 4, 2, 9), IntArray(4, 6, 3, 2))));
		
		//Generator some = new SomeDependent(10, 0.5);
		
		Generator some = new SomeDependent(10, 0.5);
		some.generate();
		Integer[] a = some.getA();
		Integer[] b = some.getB();
		loopDependency(a, b);
	}
	
	public static void main(String[] args) {
		InstrumentSupport.setInstrumentConfiguration(new Configuration(
			true,						// enable any instrumentation
			BloomFilterTrace.class,		// loop trace class
			new BloomFilterConfiguration(
					100,
					new Funnel<Access>() {
						@Override
						public void funnel(Access access, PrimitiveSink sink) {
							sink.putInt(access.getArrayId())
								.putInt(access.getIndex());
						}
					}
			)
		));
		InstrumentSupport.startTimer();
		
		test();
		
		InstrumentSupport.stopTimer();
		//InstrumentSupport.report();
		System.out.println("time=" + InstrumentSupport.getTimeDifference());
		
//		Trace trace = new HashSetTrace(new TraceConfiguration());
//		trace.add(new Access(0, 0, 0, Kind.Store));
//		
//		noop();
	}
}
