package uk.ac.ed.inf.icsa.locomotion.results;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public final class Formatter {
	private final File targetDirectory;
	private List<Result> results;
	
	private Formatter() {
		this.targetDirectory = new File(System.getProperty("user.dir") + File.separator + "results");
		this.results = _getFilesInDirectory(this.targetDirectory);
	}
	
	private List<Result> _getFilesInDirectory(File directory) {
		List<Result> result = new LinkedList<>();
		
		for (File file: directory.listFiles())
			try { result.add(Result.fromFile(file)); }
			catch (IOException e) { e.printStackTrace(); }
		
		return result;
	}
	
	private void run() {
		
	}

	public static void main(String[] args) {
		new Formatter().run();
	}

}
