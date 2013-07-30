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
            double xresult = fx[i] * (1.0/mass[i]);
            double yresult = fy[i] * (1.0/mass[i]);
            xresult *= dt;
            yresult *= dt;
            vx[i] += xresult;
            vy[i] += yresult;
            rx[i] += vx[i] * dt;
            ry[i] += vy[i] * dt;
        }
    }
    
    public void computeForces(double[] rx, double[] ry, double[] mass, double[] fx, double[] fy) {
        // compute the forces
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (i != j) {
                double rxresult = rx[j] - rx[i];
                double ryresult = ry[j] - ry[i];
                double G = 6.67e-11;
                double dist = Math.sqrt((rxresult * rxresult) + (ryresult * ryresult));
                double F = (G * mass[i] * mass[j]) / (dist * dist);
                rxresult = rxresult * (1.0/dist);
                ryresult = ryresult * (1.0/dist);
                rxresult = rxresult * F;
                ryresult = ryresult * F;
                fx[i] += rxresult;
                fy[i] += ryresult;
                }
            }
        }

    }

    public void draw() {
        for (int i = 0; i < N; i++) {
            //StdDraw.setPenRadius(0.025);
            Vector r = new Vector(new double[]{rx[i],ry[i]});
            //StdDraw.point(r.cartesian(0), r.cartesian(1));
        }
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