package io.atkin.locomotion.bytecode;

import java.io.IOException;
import java.io.InputStream;

import org.objectweb.asm.ClassReader;

public class Application {
	private String className;
	private ClassReader classReader;
	
	public Application() throws IOException {
		this.className = "Test";
		
		InputStream in = Application.class.getResourceAsStream("/io/atkin/locomotion/bytecode/" + className + ".class");
		this.classReader = new ClassReader(in);
	}
	
	public void run() {
		
	}
	
	public static void main(String[] args) {
		try {
			new Application().run();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
