package uk.ac.ed.inf.icsa.locomotion.benchmarks.nbody;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;



//import uk.ac.ed.inf.icsa.locomotion.benchmarks.mandelbrot.StdDraw;
import static uk.ac.ed.inf.icsa.locomotion.instrumentation.InstrumentSupport.arrayLookup;
import static uk.ac.ed.inf.icsa.locomotion.instrumentation.InstrumentSupport.arrayWrite;

public class Universe {
	private final double radius;     // radius of universe
    private final int N;             // number of bodies
    private final double[] rx;
    private final double[] ry;
    private final double[] vx;
    private final double[] vy;
    private final double[] mass;
    
    // read universe from standard input
    public Universe() {

        // number of bodies
        N = StdIn.readInt(); 

        // the set scale for drawing on screen
        radius = StdIn.readDouble(); 
        //StdDraw.setXscale(-radius, +radius); 
        //StdDraw.setYscale(-radius, +radius); 

        // read in the N bodies
        rx = new double[N];
        ry = new double[N];
        vx = new double[N];
        vy = new double[N];
        mass = new double[N];
        
        for (int i = 0; i < N; i++) { 
            rx[i]   = StdIn.readDouble(); 
            ry[i]   = StdIn.readDouble(); 
            vx[i]   = StdIn.readDouble(); 
            vy[i]   = StdIn.readDouble(); 
            mass[i] = StdIn.readDouble(); 
        } 
    } 

    // increment time by dt units, assume forces are constant in given interval
    public void increaseTime(double dt) {

        // initialize the forces to zero
        double[] fx = new double[N];
        double[] fy = new double[N];

        computeForces(rx, ry, mass, fx, fy);

        // move the bodies
        for (int i = 0; i < N; i++) {
        	double xresult = arrayLookup(fx, i, i, "increase-time") * (1.0 / arrayLookup(mass, i, i, "increase-time"));
        	double yresult = arrayLookup(fy, i, i, "increase-time") * (1.0 / arrayLookup(mass, i, i, "increase-time"));
            xresult *= dt;
            yresult *= dt;
            
            arrayWrite(vx, i, arrayLookup(vx, i, i, "increase-time") + xresult, i, "increase-time");
            arrayWrite(vy, i, arrayLookup(vy, i, i, "increase-time") + yresult, i, "increase-time");

            arrayWrite(rx, i, arrayLookup(rx, i, i, "increase-time") + arrayLookup(vx, i, i, "increase-time") * dt, i, "increase-time");
            arrayWrite(ry, i, arrayLookup(ry, i, i, "increase-time") + arrayLookup(vy, i, i, "increase-time") * dt, i, "increase-time");
        }
    }
    
    // public static double arrayLookup(double[] array, int index, int loopIterator, String loopId)
    
    // public static void arrayWrite(double[] array, int index, double value, int loopIterator, String loopId)
    
    public void computeForces(double[] rx, double[] ry, double[] mass, double[] fx, double[] fy) {
        // compute the forces
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (i != j) {
                double rxresult = arrayLookup(rx, j, j, "compute-forces") - arrayLookup(rx, i, j, "compute-forces");
                double ryresult = arrayLookup(ry, j, j, "compute-forces") - arrayLookup(ry, i, j, "compute-forces");
                double G = 6.67e-11;
                double dist = Math.sqrt((rxresult * rxresult) + (ryresult * ryresult));
                double F = (G * arrayLookup(mass, i, j, "compute-forces") * arrayLookup(mass, j, j, "compute-forces") / dist * dist);
                rxresult = rxresult * (1.0/dist);
                ryresult = ryresult * (1.0/dist);
                rxresult = rxresult * F;
                ryresult = ryresult * F;
                arrayWrite(fx, i, rxresult, j, "compute-forces");
                arrayWrite(fy, i, rxresult, j, "compute-forces");
                }
            }
        }

    }

    public void draw() {
//        for (int i = 0; i < N; i++) {
//            //StdDraw.setPenRadius(0.025);
//            //Vector r = new Vector(new double[]{rx[i],ry[i]});
//            //StdDraw.point(r.cartesian(0), r.cartesian(1));
//        }
    } 

    // client to simulate a universe
    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
    	StdIn.setScanner(new Scanner(new File(args[0])));
    	int steps = Integer.parseInt(args[1]);
    	
        Universe newton = new Universe();
        Double dt = 25000d;//Double.parseDouble();
        
        for (int i = 0; i < steps; i++) {
            //StdDraw.clear(); 
            newton.increaseTime(dt); 
            //newton.draw(); 
            //StdDraw.show(10); 
        }
        
        //Thread.currentThread().join();
    }
}