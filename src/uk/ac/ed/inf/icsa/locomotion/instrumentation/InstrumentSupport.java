package uk.ac.ed.inf.icsa.locomotion.instrumentation;

import static io.atkin.io.console.*;

import java.util.HashSet;

public final class InstrumentSupport {
	private static Instrument instrument;
	
	static {
		instrument = new Instrument(new Configuration(true, HashSet.class));
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
		//println(instrument.dependencyReport());
	}
}