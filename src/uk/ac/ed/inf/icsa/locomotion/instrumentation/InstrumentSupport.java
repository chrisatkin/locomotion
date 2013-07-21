package uk.ac.ed.inf.icsa.locomotion.instrumentation;

import static io.atkin.io.console.println;

import java.util.HashSet;
import java.util.List;
import java.util.LinkedList;

import uk.ac.ed.inf.icsa.locomotion.exceptions.LoopDependencyException;
import uk.ac.ed.inf.icsa.locomotion.instrumentation.storage.HashSetTrace;
import uk.ac.ed.inf.icsa.locomotion.instrumentation.storage.Trace;
import uk.ac.ed.inf.icsa.locomotion.instrumentation.storage.TraceConfiguration;

public final class InstrumentSupport {
	private static Instrument instrument;
	private static long startTime = 0;
	private static long endTime = 0;
	
	static {
		instrument = null;
	}
	
	public static void setInstrumentConfiguration(Configuration config) {
		instrument = new Instrument(config);
	}
	
	public static <T> T arrayLookup(T[] array, int index, int loopIterator, int loopId) {
		assert instrument != null: "instrument configuration not set";
		
		instrument.instrumentArrayLoad(array, index, loopIterator, loopId);
		return array[index];
	}
	
	public static <T> void arrayWrite(T[] array, int index, T value, int loopIterator, int loopId) {
		assert instrument != null: "instrument configuration not set";

		instrument.instrumentArrayWrite(array, index, value, loopIterator, loopId);
		array[index] = value;
	}
	
	public static void report() {
		println(instrument.report());
	}
	
	public static void startTimer() {
		startTime = System.nanoTime();
	}
	
	public static void stopTimer() {
		endTime = System.nanoTime();
	}
	
	public static long getTimeDifference() {
		return endTime - startTime;
	}
}