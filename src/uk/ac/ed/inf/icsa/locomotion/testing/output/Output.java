package uk.ac.ed.inf.icsa.locomotion.testing.output;

import java.io.Closeable;

public interface Output extends Closeable {
	public void put(String s);
}
