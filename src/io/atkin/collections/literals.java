package io.atkin.collections;

import io.atkin.collections.types.Tuple;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class literals {
	@SafeVarargs
	public static <T> T[] array(final T... args) {
		return args;
	}
	
	@SafeVarargs
	public static <T> List<T> list(final T... args) {
		return Arrays.asList(array(args));
	}
	
	@SafeVarargs
	public static <T> Set<T> set(final T... args) {
		return new HashSet<T>(list(args));
	}
	
	@SafeVarargs
	@SuppressWarnings("serial")
	public static <K, V> Map<K, V> map(final Tuple<K, V>... entries) {		
		return new HashMap<K, V>() {{
			for(Tuple<K, V> entry: entries)
				put(entry._item_1, entry._item_2);
		}};
	}
}
