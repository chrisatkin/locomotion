package uk.ac.ed.inf.icsa.locomotion.testing.output;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public class File implements Output {
	private PrintWriter writer;
	
	public File(String file) throws FileNotFoundException {
		this.writer = new PrintWriter(file);
	}

	@Override
	public void close() {
		writer.close();
	}

	@Override
	public void put(String s) {
		writer.println(s);
	}

}
