import java.lang.instrument.*;

public class LocomotionTransformer implements ClassFileTransformer {

	@Override
	public byte[] transform(ClassLoader loader, String classname, Class<?> klass, ProtectionDomain domain, byte[] klassFileBuffer)
		throws IllegalClassFormatException {
			System.out.println(className + " is about to be loaded");
		}

}