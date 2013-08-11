package uk.ac.ed.inf.icsa.locomotion.results;

import static io.atkin.collections.literals.set;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public final class Formatter {
	private interface Executable{
		public void execute(String  testName, String instrument, String storage) throws FileNotFoundException;
	}
	
	private final File targetDirectory;
	private List<Result> results;
	
	@SuppressWarnings("serial")
	private Formatter() {
		this.targetDirectory = new File(System.getProperty("user.dir") + File.separator + "results");
		this.results = _getFilesInDirectory(this.targetDirectory);
	}
	
	private List<Result> _getFilesInDirectory(File directory) {
		List<Result> result = new LinkedList<>();
		
		for (File file: directory.listFiles())
			if (file.isFile())
				try { result.add(Result.fromFile(file)); }
				catch (IOException e) { e.printStackTrace(); }
		
		return result;
	}
	
	private void run() {
		try {
			
			// hash sets
			executeFormatter(new Executable() {
				@SuppressWarnings("serial")
				@Override
				public void execute(final String name, final String instrument, final String storage) throws FileNotFoundException {
					if (storage.equals("HashSetTrace")) {
						FourVariables axis = new FourVariables(
							getFile(name + "-instrumentation=" + instrument + "-storage=" + storage),
							results,
							new HashMap<String, String>() {{
								put("name", name);
								put("instrumentation", instrument);
								put("storage", storage);
							}},
							"length",
							"finalmemory",
							"time",
							"dependencies");
						axis.run();
						axis.toFile();
					}
				}});
			
			executeFormatter(new Executable() {
				@SuppressWarnings("serial")
				public void execute(final String name, final String instrument, final String storage) throws FileNotFoundException {
					if (storage.equals("BloomFilterTrace")) {
						FiveVariables axis = new FiveVariables(
							getFile(name + "-instrumentation=" + instrument + "-storage=" + storage),
							results,
							new HashMap<String, String>() {{
								put("name", name);
								put("instrumentation", instrument);
								put("storage", storage);
							}},
							"length",
							"finalmemory",
							"bitvector",
							"dependencies",
							"time"
							);
						axis.run();
						axis.toFile();
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void executeFormatter(Executable e) throws FileNotFoundException {
		for (final String name: new String[] {
				/*"all-map",
				"all-smallvector",
				"none-smallvector",
				"all-longvector",
				"none-longvector",
				"all-tinyvector",
				"none-tinyvector",
				"some-0.8equal-smallvector",
				"some-0.6equal-smallvector",
				"some-0.4equal-smallvector",
				"some-0.2equal-smallvector",
				"some-0.2equal-longvector",
				"some-0.4equal-longvector"
				*/
				
				"test"}) {
			for (final String instrument: new String[] { "true", "false" }) {
				for (final String storage: new String[] { "HashSetTrace", "BloomFilterTrace"}) {
					System.out.println("name=" + name + " instrument=" + instrument + " storage=" + storage);
					e.execute(name, instrument, storage);
				}
			}
 		}
	}
	
	private static File getFile(String f) {
		return new File(System.getProperty("user.dir") + File.separator + "formatted-results" + File.separator + f);
	}

	public static void main(String[] args) {
		new Formatter().run();
	}

}
