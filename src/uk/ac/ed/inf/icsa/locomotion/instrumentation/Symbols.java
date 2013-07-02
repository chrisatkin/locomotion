package uk.ac.ed.inf.icsa.locomotion.instrumentation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import uk.ac.ed.inf.icsa.locomotion.exception.LocomotionError;

public class Symbols {

	private static Map<String, Set<String>> symbols = new HashMap<String, Set<String>>();
	
	public static void addScope(String name) {
		symbols.put(name, new HashSet<String>());
	}
	
	public static void addToScope(String scope, String variable) {
		if (!scopeExists(scope))
			throw new LocomotionError("Scope " + scope + " does not exist");
		
		getVariablesInScope(scope).add(variable);
	}
	
	public static boolean scopeExists(String scope) {
		return symbols.containsKey(scope);
	}
	
	public static boolean variableExists(String scope, String variable) {
		return getVariablesInScope(scope).contains(variable);
	}
	
	public static Set<String> getVariablesInScope(String scope) {
		if (!scopeExists(scope))
			throw new LocomotionError("Scope " + scope + " does not exist");
		
		return symbols.get(scope);
	}
	
	public static Map<String, Set<String>> getSymbols() {
		return symbols;
	}
}
