package uk.ac.ed.inf.icsa.locomotion.testing;

import java.lang.reflect.InvocationTargetException;

import uk.ac.ed.inf.icsa.locomotion.testing.output.Output;

public class Test {
	private Class<? extends Experiment> clazz;
	private Object[] args;
	
	public Test(Class <? extends Experiment> clazz, Object[] args) {
		this.clazz = clazz;
		this.args = args;
	}
	
	public void run(Output output) {
		try {
			clazz.newInstance().run(output, args);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | SecurityException e) {
			e.printStackTrace();
		}
	}
}