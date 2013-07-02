package uk.ac.ed.inf.icsa.locomotion.instrumentation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import uk.ac.ed.inf.icsa.locomotion.exception.LocomotionError;

public class Symbols {

	private static Map<Kind, Set<String>> symbols = new HashMap<Kind, Set<String>>();
	
	public static void addScope(Kind kind) {
		symbols.put(kind, new HashSet<String>());
	}
	
	public static void addToScope(Kind scope, String variable) {
		if (!scopeExists(scope))
			throw new LocomotionError("Scope " + scope + " does not exist");
		
		getVariablesInScope(scope).add(variable);
	}
	
	public static boolean scopeExists(Kind scope) {
		return symbols.containsKey(scope);
	}
	
	public static boolean variableExists(Kind scope, String variable) {
		return getVariablesInScope(scope).contains(variable);
	}
	
	public static Set<String> getVariablesInScope(Kind scope) {
		if (!scopeExists(scope))
			throw new LocomotionError("Scope " + scope + " does not exist");
		
		return symbols.get(scope);
	}
	
	public static Map<Kind, Set<String>> getSymbols() {
		return symbols;
	}
}
