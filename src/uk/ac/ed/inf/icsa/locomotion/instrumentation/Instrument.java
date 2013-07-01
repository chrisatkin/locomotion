package uk.ac.ed.inf.icsa.locomotion.instrumentation;

import java.util.EnumMap;
import java.util.Map;

import uk.ac.ed.inf.icsa.locomotion.instrumentation.trace.BloomFilterTrace;
import uk.ac.ed.inf.icsa.locomotion.instrumentation.trace.Trace;

public class Instrument {
	
	public static int stores = 0;
	public static int loads = 0;
	
	public static class InstrumentationImpl {
		private Trace loadTrace;
		private Trace storeTrace;
		
		public <T> InstrumentationImpl(Class<T> traceClass) {
			try {
				this.loadTrace = (Trace) traceClass.newInstance();
				this.storeTrace = (Trace) traceClass.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		public void store(Entry e) {
			storeTrace.add(e);
		}
		
		public void load(Entry e) {
			loadTrace.add(e);
		}
		
		public String report() {
			StringBuilder report = new StringBuilder();
			
			report.append("stores=" + storeTrace.entryCount() + " loads=" + loadTrace.entryCount());
			
			return report.toString();
		}
	}
	
	private static EnumMap<Kind, InstrumentationImpl> instruments = new EnumMap<Kind, InstrumentationImpl>(Kind.class);
	
	public static InstrumentationImpl get(Kind k) {
		if (!instruments.containsKey(k))
			instruments.put(k, new InstrumentationImpl(BloomFilterTrace.class));
		
		return instruments.get(k);
	}
	
	public static String report() {
		StringBuilder report = new StringBuilder();
		
		for (Map.Entry<Kind, InstrumentationImpl> entry: instruments.entrySet()) {
			Kind k = entry.getKey();
			InstrumentationImpl i = entry.getValue();
			
			report.append(k.toString() + ": " + i.report());
		}
		
		report.append("stores ").append(stores).append("\n");
		report.append("loads ").append(loads);
		
		return report.toString();
	}
}