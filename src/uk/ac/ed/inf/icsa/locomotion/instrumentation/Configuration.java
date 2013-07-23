package uk.ac.ed.inf.icsa.locomotion.instrumentation;

import java.lang.reflect.InvocationTargetException;

import uk.ac.ed.inf.icsa.locomotion.exceptions.LocomotionError;
import uk.ac.ed.inf.icsa.locomotion.instrumentation.storage.HashSetTrace;
import uk.ac.ed.inf.icsa.locomotion.instrumentation.storage.Trace;
import uk.ac.ed.inf.icsa.locomotion.instrumentation.storage.TraceConfiguration;
import uk.ac.ed.inf.icsa.locomotion.testing.output.Output;

public final class Configuration {
	private final boolean enableInstrumentation;
	private Class<? extends Trace> loopStore;
	private TraceConfiguration loopStoreConfiguration;
	private boolean reportMemory;
	private Output output;
	
	public Configuration(final boolean enableInstrumentation,
						 final Class<? extends Trace> class1,
						 final TraceConfiguration loopStoreConfiguration,
						 final boolean reportMemory,
						 Output output) {
		this.enableInstrumentation = enableInstrumentation;
		this.loopStore = class1;
		this.loopStoreConfiguration = loopStoreConfiguration;
		this.reportMemory = reportMemory;
		this.output = output;
	}

	public boolean instrumentationEnabled() {
		return enableInstrumentation;
	}
	
	public Class<? extends Trace> getLoopStoreClass() {
		return loopStore;
	}
	
	public TraceConfiguration getLoopStoreConfiguration() {
		return loopStoreConfiguration;
	}
	
	public Output getOutput() {
		return output;
	}
	
	public boolean reportMemory() {
		return reportMemory;
	}
}
