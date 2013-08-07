package uk.ac.ed.inf.icsa.locomotion.utilities;

import uk.ac.ed.inf.icsa.locomotion.core.Cycle;

import com.oracle.graal.api.code.CodeCacheProvider;
import com.oracle.graal.api.meta.ResolvedJavaMethod;

public final class MethodUtilities {
	public static java.lang.reflect.Method getMethod(Cycle m) throws NoSuchMethodException, SecurityException {
		return m.getClazz().getMethod(m.getName(), m.getTypes());
	}
	
	public static ResolvedJavaMethod getResolvedMethod(CodeCacheProvider runtime, java.lang.reflect.Method method) {
		return runtime.lookupJavaMethod(method);
	}
	
	public static ResolvedJavaMethod getResolvedMethod(Cycle m, CodeCacheProvider runtime) throws NoSuchMethodException, SecurityException {
		return getResolvedMethod(runtime, getMethod(m));
	}

	public static void log(String methodIR) {
		System.out.println(methodIR);
	}
}
