package uk.ac.ed.inf.icsa.locomotion.instrumentation;

public final class Configuration {
	public final boolean enableInstrumentation;
	public final Class<?> backingStore;
	
	public Configuration(final boolean enableInstrumentation, final Class<?> backingStore) {
		this.enableInstrumentation = enableInstrumentation;
		this.backingStore = backingStore;
	}
	
	public boolean instrumentationEnabled() {
		return enableInstrumentation;
	}
	
	public Class<?> backingStoreClass() {
		return backingStore;
	}
}
