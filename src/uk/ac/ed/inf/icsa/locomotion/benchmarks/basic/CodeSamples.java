package uk.ac.ed.inf.icsa.locomotion.benchmarks.basic;

import uk.ac.ed.inf.icsa.locomotion.benchmarks.probabilistic.StaticGenerator;
import uk.ac.ed.inf.icsa.locomotion.benchmarks.probabilistic.Generator;
import uk.ac.ed.inf.icsa.locomotion.benchmarks.probabilistic.FractionalGenerator;
import uk.ac.ed.inf.icsa.locomotion.instrumentation.Access;
import uk.ac.ed.inf.icsa.locomotion.instrumentation.Configuration;
import uk.ac.ed.inf.icsa.locomotion.instrumentation.InstrumentSupport;
import uk.ac.ed.inf.icsa.locomotion.instrumentation.Kind;
import uk.ac.ed.inf.icsa.locomotion.instrumentation.storage.BloomFilterConfiguration;
import uk.ac.ed.inf.icsa.locomotion.instrumentation.storage.BloomFilterTrace;
import uk.ac.ed.inf.icsa.locomotion.instrumentation.storage.HashSetTrace;
import uk.ac.ed.inf.icsa.locomotion.instrumentation.storage.TraceConfiguration;
import uk.ac.ed.inf.icsa.locomotion.testing.output.Console;

import com.google.common.hash.Funnel;
import com.google.common.hash.PrimitiveSink;

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
	
	public static void loopDependency(Integer[] array, Kind[] first, Kind[] second) {
		for (int i = 0; i <= 1; i++) {
			for (int j = 0; j < array.length; j++) {
				// Get the right array
				Kind[] which = (i == 0) ? first : second;
				
				// Get the actual kind
				Kind type = which[j];
				
				if (type == Kind.Load)
					InstrumentSupport.arrayLookup(array, j, i, "loop-dependency");
				else if (type == Kind.Store)
					InstrumentSupport.arrayWrite(array, j, j, i, "loop-dependency");
			}
		}
	}
	
	public static void test() {
		//println(Arrays.toString(vectorAddition(IntArray(0, 4, 2, 9), IntArray(4, 6, 3, 2))));
		
		//Generator some = new SomeDependent(10, 0.5);
		
		//Generator some = new StaticGenerator(10, Kind.Store, Kind.Load);
		Generator some = new FractionalGenerator(10, 5, 3, 2);
		some.generate();
		Kind[] first = some.getFirst();
		Kind[] second = some.getSecond();
		Integer[] array = some.getArray();
		loopDependency(array, first, second);
	}
	
	public static void main(String[] args) {
//		InstrumentSupport.setInstrumentConfiguration(new Configuration(
//			true,						// enable any instrumentation
//			BloomFilterTrace.class,		// loop trace class
//			new BloomFilterConfiguration(
//					100,
//					new Funnel<Access>() {
//						private static final long serialVersionUID = 6238641208245001860L;
//
//						@Override
//						public void funnel(Access access, PrimitiveSink sink) {
//							sink.putInt(access.getArrayId())
//								.putInt(access.getIndex());
//						}
//					}
//			),
//			true,
//			new Console()
//		));
		
		InstrumentSupport.setInstrumentConfiguration(new Configuration(
			true,
			HashSetTrace.class,
			new TraceConfiguration(),
			true,
			new Console()));
		
		InstrumentSupport.startTimer();
		
		test();
		
		InstrumentSupport.stopTimer();
		InstrumentSupport.report();
		System.out.println("time=" + InstrumentSupport.getTimeDifference());
	}
}
