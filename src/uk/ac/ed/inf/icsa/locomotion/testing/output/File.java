package uk.ac.ed.inf.icsa.locomotion.testing.output;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

public class File implements Output {
	private String directory;
	private PrintWriter writer;
	private List<String> strings;
	
	public File(String file) {
		this.directory = file;
		this.strings = new LinkedList<>();
	}

	@Override
	public void close() {
		for (String s: strings)
			writer.println(s);
		
		writer.close();
		this.strings = new LinkedList<>();
	}

	@Override
	public void put(String s) {
		strings.add(s);
	}

	@Override
	public void open(String identifier) throws FileNotFoundException {
		try {
			this.writer = new PrintWriter(directory + identifier, StandardCharsets.UTF_8.toString());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		strings.add("name=" + identifier);
	}

}