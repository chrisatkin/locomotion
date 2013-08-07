package io.atkin.io;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class console {
	public static void println(final Object s) {
		System.out.println(s);
	}
	
	public static void print(final Object s) {
		System.out.print(s);
	}
	
	public static <T> void display(final T[] a) {
		println(Arrays.toString(a));
	}
	
	public static void display(final List<?> list) {
		println(list.toArray());
	}
	
	public static void display(final Set<?> set) {
		println(set.toArray());
	}
}
