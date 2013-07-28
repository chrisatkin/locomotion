package uk.ac.ed.inf.icsa.locomotion.results;

import java.util.Set;

class Condition {
	private String key;
	private Set<String> values;
	
	public Condition(String key, Set<String> values) {
		this.key = key;
		this.values = values;
	}
	
	public String getKey() {
		return key;
	}
	
	public Set<String> getValues() {
		return values;
	}
}
