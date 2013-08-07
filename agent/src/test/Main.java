package test;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.io.FileNotFoundException;
import java.util.HashSet;

import javassist.*;
import javassist.CodeConverter.ArrayAccessReplacementMethodNames;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;

import java.util.Scanner;

public class Main {
	
    public static void main(String[] args) throws Exception {
        CtClass c = ClassPool.getDefault().get("test.MyBean");
        c.instrument(new ExprEditor() {
            @Override
			public void edit(FieldAccess f) throws CannotCompileException {
            	
                if (!f.where().getMethodInfo().isMethod() || !f.getClassName().equals("test.MyBean"))
                    return;
                
                int loopid = getLoop(f);
                
                if (loopid != -1) {
                	System.out.println(f.getFieldName() + " accessed in loop " + loopid);
                	f.replace("{ $_ = $proceed($$); $_ = ($r) test.MyInterceptor#modifyReturnValue($_, \"" + f.getFieldName() + "\", " + loopid + "); }");
            }	}
        });
        
        Class<MyBean> clazz = (Class<MyBean>)c.toClass();
        MyBean t = clazz.newInstance();
       // t.setMyProperty("myPropety");
        t.run();
        
        System.out.println("Field stores: " + MyInterceptor.fieldStores);
        System.out.println("Access counts:" + MyInterceptor.accessCounts);
    }
    
    @SuppressWarnings("unused")
	public static int getLoop(FieldAccess f) {
    	String filename = f.getFileName();
    	int lineNumber = f.getLineNumber();
    	List<Integer> loopLines = new ArrayList<Integer>();
    	
    	try {
			String[] lines = readLines("src/test/" + filename);
			int loopStart, loopEnd;
			boolean inLoop = true;
			
			for (int i = 0; i < lines.length; i++) {
				String line = lines[i];
				
				if (line.contains("for")) {
					loopLines.add(i + 1);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	return loopLines.indexOf(lineNumber - 1);
    }
    
    public static String[] readLines(String filename) throws IOException {
        FileReader fileReader = new FileReader(filename);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        List<String> lines = new ArrayList<String>();
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            lines.add(line);
        }
        bufferedReader.close();
        return lines.toArray(new String[lines.size()]);
    }
}