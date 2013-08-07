package uk.ac.ed.inf.icsa.locomotion.testing.output;

import java.io.IOException;

public class Console implements Output {

	@Override
	public void close() {
		// do nothing
		;
	}

	@Override
	public void put(String s) {
		System.out.println(s);
	}

	@Override
	public void open(String identifier) {
		put("test=" + identifier);
	}
}
