package uk.ac.ed.inf.icsa.locomotion;

import java.util.HashSet;

public class TestApplication {
	
	private static class A{
		public int first;
		public int second;
		
		public A(int a, int b) {
			this.first = a;
			this.second = b;
		}
		
		@Override
		public int hashCode() {
			return first + second;
		}
	}
	
	public void run() {
		HashSet<A> set = new HashSet<>();
		
		A a = new A(1, 2);
		System.out.println(a.hashCode());
		
		A b = new A(1, 2);
		System.out.println(b.hashCode());
		
		set.add(a);
		
		System.out.println(set.contains(b));	
	}
	
	public static void main(String[] args) {
		new TestApplication().run();
	}
}