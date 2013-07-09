package uk.ac.ed.inf.icsa.locomotion.misc;

import uk.ac.ed.inf.icsa.locomotion.core.Method;

import com.oracle.graal.api.code.CodeCacheProvider;
import com.oracle.graal.api.meta.ResolvedJavaMethod;
import com.oracle.graal.nodes.StructuredGraph;
import com.oracle.graal.printer.GraphPrinterDumpHandler;

public class Utils {
	public static void dumpGraphToIgv(StructuredGraph graph, String name) {
		GraphPrinterDumpHandler printer = new GraphPrinterDumpHandler();
		printer.dump(graph, name);
		printer.close();
	}
	
	public static java.lang.reflect.Method getMethod(Method<?> m) throws NoSuchMethodException, SecurityException {
		return m.getClazz().getMethod(m.getName(), m.getTypes());
	}
	
	public static ResolvedJavaMethod getResolvedMethod(CodeCacheProvider runtime, java.lang.reflect.Method method) {
		return runtime.lookupJavaMethod(method);
	}
	
	public static ResolvedJavaMethod getResolvedMethod(Method<?> m, CodeCacheProvider runtime) throws NoSuchMethodException, SecurityException {
		return getResolvedMethod(runtime, getMethod(m));
	}

	public static void log(String methodIR) {
		System.out.println(methodIR);
	}
}
