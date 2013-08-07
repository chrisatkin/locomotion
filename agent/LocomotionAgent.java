import java.lang.instrument.*;

public class LocomotionAgent {
	public static void premain(String args, Instrumentation inst) throws Exception {
		System.out.println("Loading agent");
		inst.addTransformer(new LocomotionTransformer());
	}
}