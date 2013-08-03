package uk.ac.ed.inf.icsa.locomotion.results;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

abstract class Format {
	private static class Item {}
	
	protected static class ChartItem3<A,B,C> extends Item {
		private A a;
		private B b;
		private C c;
		
		public ChartItem3(A a, B b, C c) {
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
	
	protected static class ChartItem4<A,B,C,D> extends Item {
		private A a;
		private B b;
		private C c;
		private D d;

		public ChartItem4(A a, B b, C c, D d) {
			this.a = a;
			this.b = b;
			this.c = c;
			this.d = d;
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
		
		public D getD() {
			return d;
		}
		
		public String toString() {
			StringBuilder string = new StringBuilder();
			string.append(a.toString()).append("\t").append(b.toString()).append("\t").append(c.toString()).append("\t").append(d.toString());
			return string.toString();
		}
	}
	
	protected File destination;
	protected List<Result> results;
	protected List<Item> items;
	protected Map<String, String> restrictions;
	
	protected Format(File destination, List<Result> results, Map<String, String> restrictions) {
		this.destination = destination;
		this.results = results;
		this.items = new LinkedList<>();
		this.restrictions = restrictions;
	}
	
	public File getDestination() {
		return destination;
	}
	
	protected List<Result> filterResults() {
		List<Result> result = new LinkedList<>();
		
		for (Result r: results) {
			boolean[] include = new boolean[restrictions.size()];
			int i = 0;
			
			for (Map.Entry<String, String> requirement: restrictions.entrySet()) {
				String requirement_name = requirement.getKey();
				String requirement_must_equal = requirement.getValue();
				
				if (r.getValue(requirement_name).equals(requirement_must_equal)) {
					include[i] = true;
				}
				
				i++;
			}
			
			boolean really_include = true;
			
			for (boolean this_include: include)
				if (this_include != true)
					really_include = false;
			
			if (really_include)
				result.add(r);
		}
		
		return result;
	}
	
	public abstract void run();
	
	public void toFile() throws FileNotFoundException {
		try (PrintWriter writer = new PrintWriter(destination)) {
			for (Item item: items)
				writer.println(item.toString());
		}
	}
}
