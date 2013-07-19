package uk.ac.ed.inf.icsa.locomotion.instrumentation;

import static io.atkin.io.console.*;

import java.util.HashSet;

import uk.ac.ed.inf.icsa.locomotion.instrumentation.storage.HashSetTrace;
import uk.ac.ed.inf.icsa.locomotion.instrumentation.storage.Trace;
import uk.ac.ed.inf.icsa.locomotion.instrumentation.storage.TraceConfiguration;

public final class InstrumentSupport {
	private static Instrument instrument;
	
	static {
		instrument = new Instrument(
				new Configuration(
						true,						// enable any instrumentation
						HashSetTrace.class,			// loop trace class
						new TraceConfiguration()	// loop trace configuration
				)
		);
	}
	
	public static <T> T arrayLookup(T[] array, int index, int loopIterator, int loopId) {
		instrument.instrumentArrayLoad(array, index, loopIterator, loopId);
		return array[index];
	}
	
	public static <T> void arrayWrite(T[] array, int index, T value, int loopIterator, int loopId) {
		instrument.instrumentArrayWrite(array, index, value, loopIterator, loopId);
		array[index] = value;
	}
	
	public static void report() {
		println(instrument.report());
	}
	
	public static Trace getTracer(Class<? extends Trace> kind) {
		try {
			return (Trace) kind.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}