package uk.ac.ed.inf.icsa.locomotion.results;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

public class Result {
	private List<String> lines;
	
	private Result(File file) throws IOException {
		// Get contents of file
		this.lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
	}
	
	public static Result fromFile(File file) throws IOException {
		return new Result(file);
	}
}