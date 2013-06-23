package uk.ac.ed.inf.icsa.locomotion.misc;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

import com.google.common.io.Files;
import com.oracle.graal.api.code.CodeCacheProvider;
import com.oracle.graal.api.meta.ResolvedJavaMethod;
import com.oracle.graal.nodes.StructuredGraph;
import com.oracle.graal.nodes.spi.GraalCodeCacheProvider;
import com.oracle.graal.printer.GraphPrinterDumpHandler;

public class Utils {
	public static void dumpGraphToIgv(StructuredGraph graph, String name) {
		GraphPrinterDumpHandler printer = new GraphPrinterDumpHandler();
		printer.dump(graph, name);
		printer.close();
	}
	
	public static Method getMethod(Class<?> clazz, String name) throws NoSuchMethodException, SecurityException {
		return clazz.getMethod(name);
	}
	
	public static ResolvedJavaMethod getResolvedMethod(CodeCacheProvider runtime, Method method) {
		return runtime.lookupJavaMethod(method);
	}
	
	public static ResolvedJavaMethod getResolvedMethod(Class<?> clazz,String name, CodeCacheProvider runtime) throws NoSuchMethodException, SecurityException {
		return getResolvedMethod(runtime, getMethod(clazz, name));
	}

	public static void log(String methodIR) {
		System.out.println(methodIR);
	}
	
	public static void writeByteCodeToFile(byte[] bytecode, String file) {
		try {
			Files.write(bytecode, new File(file));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
