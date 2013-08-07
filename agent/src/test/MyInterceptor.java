package test;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class MyInterceptor {

   // private static final AtomicInteger counter = new AtomicInteger();
	
	public static List<String> fieldStores = new LinkedList<String>();
	public static Map<String, Integer> accessCounts = new HashMap<String, Integer>();
	

    public static String modifyReturnValue(Object value, String name, int loopID) {
    	System.out.println("O access " + name);
    	fieldStores.add(name);
    	
    	
    	if (accessCounts.containsKey(name))
    		accessCounts.put(name, accessCounts.get(name) + 1);
    	else
    		accessCounts.put(name, 1);

   		
    	return String.valueOf(value);
    }
    
    public static int modifyReturnValue(int value, String name, int loopID) {
    	System.out.println("I access " + name);
    	
    	if (accessCounts.containsKey(name))
    		accessCounts.put(name, accessCounts.get(name) + 1);
    	else
    		accessCounts.put(name, 1);
    	
    	return value;
    }
}
