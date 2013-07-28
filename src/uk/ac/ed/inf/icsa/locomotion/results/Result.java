package uk.ac.ed.inf.icsa.locomotion.results;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Result {
	private List<String> lines;
	private Map<String, String> keys;
	private Map<Long, Long> memory;
	
	private Result(File file) throws IOException {
		// Get contents of file
		this.lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
		this.keys = new HashMap<>();
		this.memory = new HashMap<>();
		
		undoPairs();
	}
	
	private void undoPairs() {
		for (String line: lines) {
			String[] pairs = line.split(";");
			
			for(String pair:  pairs) {
				String[] key_value = pair.split("=");
				String key = key_value[0];
				String value = key_value[1];
				
				// memory is a special case
				if (key.equals("memory")) {
					String[] memory_key_value = value.split(",");
					long time = Long.parseLong(memory_key_value[0]);
					long memory = Long.parseLong(memory_key_value[1]);
					
					this.memory.put(time, memory);
				} else {
					keys.put(key, value);
				}
			}
		}
	}
	
	public String getValue(String key) {
		return keys.get(key);
	}
	
	public Map<Long, Long> getMemory() {
		return memory;
	}
	
	public boolean matches(Condition c) {
		return c.getValues().contains(getValue(c.getKey()));
	}
	
	public static Result fromFile(File file) throws IOException {
		return new Result(file);
	}
}