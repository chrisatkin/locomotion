package uk.ac.ed.inf.icsa.locomotion.results;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

abstract class Format {
	protected static class ChartItem<A,B,C> {
		private A a;
		private B b;
		private C c;
		
		public ChartItem(A a, B b, C c) {
			this.a = a;
			this.b = b;
			this.c = c;
		}
		
		public A getA() {
			return a;
		}
		
		public B getB() {
			return b;
		}
		
		public C getC() {
			return c;
		}
		
		public String toString() {
			StringBuilder string = new StringBuilder();
			string.append(a.toString()).append("\t").append(b.toString()).append("\t").append(c.toString());
			return string.toString();
		}
	}
	
	protected File destination;
	protected List<Result> results;
	protected List<ChartItem<String, String, String>> items;
	
	protected Format(File destination, List<Result> results) {
		this.destination = destination;
		this.results = results;
		this.items = new LinkedList<>();
	}
	
	public File getDestination() {
		return destination;
	}
	
	protected List<Result> filterResults(Condition c) {
		List<Result> result = new LinkedList<>();
		
		for (Result r: results)
			if (r.matches(c))
				result.add(r);
		
		return result;
	}
	
	public abstract void run();
	
	public void toFile() throws FileNotFoundException {
		try (PrintWriter writer = new PrintWriter(destination)) {
			for (ChartItem<?, ?, ?> item: items)
				writer.println(item.toString());
		}
	}
}
