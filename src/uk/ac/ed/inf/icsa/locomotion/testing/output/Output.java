package uk.ac.ed.inf.icsa.locomotion.testing.output;

import java.io.Closeable;
import java.io.FileNotFoundException;

public interface Output extends Closeable {
	public void open(String identifier) throws FileNotFoundException;
	
	public void put(String s);
	
	public void close();
}
