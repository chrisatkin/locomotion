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
import uk.ac.ed.inf.icsa.locomotion.testing.output.Console;

public class CodeSamples {
	public static Integer[] vectorAddition(Integer[] a, Integer[] b) {
		assert a.length == b.length: "vectors must be same length";
		
		Integer[] c = new Integer[a.length];
		
		for (int i = 0; i < a.length; i++) {
			Integer currentA = InstrumentSupport.arrayLookup(a, i, i, "sample-vector-addition");
			Integer currentB = InstrumentSupport.arrayLookup(b, i, i, "sample-vector-addition");
			Integer result = currentA + currentB;
			
			InstrumentSupport.arrayWrite(c, i, result, i, "sample-vector-addition");
		}
		
		return c;
	}
	
	public static Integer[] loopDependency(Integer[]  a, Integer[] b) {
		assert a.length == b.length: "vectors must be same length";
		
		Integer[] c = new Integer[a.length];
		
		for (int i = 0; i < a.length; i++) {
			Integer val = InstrumentSupport.arrayLookup(b, InstrumentSupport.arrayLookup(a, i, i, "sample-loop-dependency"), i, "sample-loop-dependency");
			
			InstrumentSupport.arrayWrite(c, i, val, i, "sample-loop-dependency");
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
			),
			true,
			new Console()
		));
		InstrumentSupport.startTimer();
		
		test();
		
		InstrumentSupport.stopTimer();
		InstrumentSupport.report();
		System.out.println("time=" + InstrumentSupport.getTimeDifference());
	}
}
