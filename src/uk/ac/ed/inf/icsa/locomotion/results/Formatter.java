package uk.ac.ed.inf.icsa.locomotion.results;

import static io.atkin.collections.literals.set;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public final class Formatter {
	private final File targetDirectory;
	private List<Result> results;
	private List<Format> formats;
	
	@SuppressWarnings("serial")
	private Formatter() {
		this.targetDirectory = new File(System.getProperty("user.dir") + File.separator + "results");
		this.results = _getFilesInDirectory(this.targetDirectory);
		this.formats = new LinkedList<Format>() {{
			
		}};
	}
	
	private List<Result> _getFilesInDirectory(File directory) {
		List<Result> result = new LinkedList<>();
		
		for (File file: directory.listFiles())
			try { result.add(Result.fromFile(file)); }
			catch (IOException e) { e.printStackTrace(); }
		
		return result;
	}
	
	private void run() {
		try {
			for (final String name: new String[] { "all-dependent", "none-dependent" }) {
				for (final String instrument: new String[] { "true", "false"}) {
					ThreeAxisVariables length_memory_accesses = new ThreeAxisVariables(
						getFile(name + "-memory-instrumentation=" + instrument),
						results,
						new HashMap<String, String>() {{
							put("name", name);
							put("instrumentation", instrument);
						}},
						"length",
						"finalmemory",
						"dependencies"
					);
					length_memory_accesses.run();
					length_memory_accesses.toFile();
					
					ThreeAxisVariables length_time_accesses= new ThreeAxisVariables(
						getFile(name + "-time-instrumentation=" + instrument),
						results,
						new HashMap<String, String>() {{
							put("name", name);
							put("instrumentation", instrument);
						}},
						"length",
						"time",
						"dependencies");
					length_time_accesses.run();
					length_time_accesses.toFile();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static File getFile(String f) {
		return new File(System.getProperty("user.dir") + File.separator + "formatted-results" + File.separator + f);
	}

	public static void main(String[] args) {
		new Formatter().run();
	}

}
