package uk.ac.ed.inf.icsa.locomotion.core;

public class Cycle {
	protected Class<?> clazz;
	protected String name;
	protected Class<?>[] types;
	protected Object[] arguments;
	
	public Cycle() {
		this.clazz = null;
		this.name = null;
		this.types = null;
		this.arguments = null;
	}
	
	public Cycle(Class<?> clazz, String name, Class<?>... types) {
		this.clazz = clazz;
		this.name = name;
		this.types = types;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setTypes(Class<?>... types) {
		this.types = types;
	}
	
	public String getName() {
		return name;
	}
	
	public Class<?>[] getTypes() {
		return types;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	public Object[] getArguments() {
		return arguments;
	}

	public void setArguments(Object[] arguments) {
		this.arguments = arguments;
	}
}