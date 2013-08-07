package test;
public class MyBean {

    public String myProperty = "property";
    private int test = 2;
    private int test2 = 5;

    public String getMyProperty() {
        return myProperty;
    }
    
    public int getTest() {
    	return test;
    }
    
    public int getAnother() {
    	return test2;
    }

    public void setMyProperty(String myProperty) {
        this.myProperty = myProperty;
    }

    public void run() {
    	for(int i=0; i<100; i++) {
            System.out.println(getMyProperty() + " test=" + getTest() + " another=" + test2);
        }
    	
    	int[] a = new int[10];
    	for (int i =0; i < 10; i++)
    		a[i] = i;
    	
    	System.out.println("outside loop property=" + getMyProperty());
    }
    
}
