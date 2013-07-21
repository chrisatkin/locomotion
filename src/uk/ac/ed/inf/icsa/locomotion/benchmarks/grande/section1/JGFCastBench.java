/**************************************************************************
*                                                                         *
*             Java Grande Forum Benchmark Suite - Version 2.0             *
*                                                                         *
*                            produced by                                  *
*                                                                         *
*                  Java Grande Benchmarking Project                       *
*                                                                         *
*                                at                                       *
*                                                                         *
*                Edinburgh Parallel Computing Centre                      *
*                                                                         * 
*                email: epcc-javagrande@epcc.ed.ac.uk                     *
*                                                                         *
*                                                                         *
*      This version copyright (c) The University of Edinburgh, 1999.      *
*                         All rights reserved.                            *
*                                                                         *
**************************************************************************/


package uk.ac.ed.inf.icsa.locomotion.benchmarks.grande.section1;
import uk.ac.ed.inf.icsa.locomotion.benchmarks.grande.jgfutil.*; 

public class JGFCastBench implements JGFSection1{


  private static final int INITSIZE = 10000;
  private static final int MAXSIZE = 1000000000;
  private static final double TARGETTIME = 10.0; 
  

  public void JGFrun(){

    int i,size;
    double time; 

    int i1 = 0; 
    long l1 = 0;
    float f1 = 0.0F;
    double d1 = 0.0D;
  
    JGFInstrumentor.addTimer("Section1:Cast:IntFloat", "casts");

    time = 0.0; 
    size = INITSIZE;
    i1 = 6;  
    
    while (time < TARGETTIME && size < MAXSIZE){
      JGFInstrumentor.resetTimer("Section1:Cast:IntFloat"); 
      JGFInstrumentor.startTimer("Section1:Cast:IntFloat"); 
      for (i=0; i<size; i++){
        f1 = (float) i1; i1 = (int) f1;
        f1 = (float) i1; i1 = (int) f1;
        f1 = (float) i1; i1 = (int) f1;
        f1 = (float) i1; i1 = (int) f1;
        f1 = (float) i1; i1 = (int) f1;
        f1 = (float) i1; i1 = (int) f1;
        f1 = (float) i1; i1 = (int) f1;
        f1 = (float) i1; i1 = (int) f1;
        f1 = (float) i1; i1 = (int) f1;
        f1 = (float) i1; i1 = (int) f1;
        f1 = (float) i1; i1 = (int) f1;
        f1 = (float) i1; i1 = (int) f1;
        f1 = (float) i1; i1 = (int) f1;
        f1 = (float) i1; i1 = (int) f1;
        f1 = (float) i1; i1 = (int) f1;
        f1 = (float) i1; i1 = (int) f1;
      }
      JGFInstrumentor.stopTimer("Section1:Cast:IntFloat"); 

      // try to defeat dead code elimination 
      if (f1 == -1.0F) System.out.println(f1); 
      time = JGFInstrumentor.readTimer("Section1:Cast:IntFloat"); 
      JGFInstrumentor.addOpsToTimer("Section1:Cast:IntFloat", (double) 32*size);  
      size *=2; 
    }
   
    JGFInstrumentor.printperfTimer("Section1:Cast:IntFloat"); 

    JGFInstrumentor.addTimer("Section1:Cast:IntDouble", "casts");

    time = 0.0; 
    size = INITSIZE;
    i1 = 6;  
    
    while (time < TARGETTIME && size < MAXSIZE){
      JGFInstrumentor.resetTimer("Section1:Cast:IntDouble"); 
      JGFInstrumentor.startTimer("Section1:Cast:IntDouble"); 
      for (i=0; i<size; i++){
        d1 = (double) i1; i1 =(int) d1;
        d1 = (double) i1; i1 =(int) d1;
        d1 = (double) i1; i1 =(int) d1;
        d1 = (double) i1; i1 =(int) d1;
        d1 = (double) i1; i1 =(int) d1;
        d1 = (double) i1; i1 =(int) d1;
        d1 = (double) i1; i1 =(int) d1;
        d1 = (double) i1; i1 =(int) d1;
        d1 = (double) i1; i1 =(int) d1;
        d1 = (double) i1; i1 =(int) d1;
        d1 = (double) i1; i1 =(int) d1;
        d1 = (double) i1; i1 =(int) d1;
        d1 = (double) i1; i1 =(int) d1;
        d1 = (double) i1; i1 =(int) d1;
        d1 = (double) i1; i1 =(int) d1;
        d1 = (double) i1; i1 =(int) d1;
      }
      JGFInstrumentor.stopTimer("Section1:Cast:IntDouble"); 

      // try to defeat dead code elimination 
      if (d1 == -1.0D) System.out.println(d1); 
      time = JGFInstrumentor.readTimer("Section1:Cast:IntDouble"); 
      JGFInstrumentor.addOpsToTimer("Section1:Cast:IntDouble", (double) 32*size);  
      size *=2; 
    }
   
    JGFInstrumentor.printperfTimer("Section1:Cast:IntDouble"); 

    JGFInstrumentor.addTimer("Section1:Cast:LongFloat", "casts");

    time = 0.0; 
    size = INITSIZE; 
    l1 = 7; 
    
    while (time < TARGETTIME && size < MAXSIZE){
      JGFInstrumentor.resetTimer("Section1:Cast:LongFloat"); 
      JGFInstrumentor.startTimer("Section1:Cast:LongFloat"); 
      for (i=0; i<size; i++){
        f1 = (float) l1; l1 =(long) f1;
        f1 = (float) l1; l1 =(long) f1;
        f1 = (float) l1; l1 =(long) f1;
        f1 = (float) l1; l1 =(long) f1;
        f1 = (float) l1; l1 =(long) f1;
        f1 = (float) l1; l1 =(long) f1;
        f1 = (float) l1; l1 =(long) f1;
        f1 = (float) l1; l1 =(long) f1;
        f1 = (float) l1; l1 =(long) f1;
        f1 = (float) l1; l1 =(long) f1;
        f1 = (float) l1; l1 =(long) f1;
        f1 = (float) l1; l1 =(long) f1;
        f1 = (float) l1; l1 =(long) f1;
        f1 = (float) l1; l1 =(long) f1;
        f1 = (float) l1; l1 =(long) f1;
        f1 = (float) l1; l1 =(long) f1;
      }
      JGFInstrumentor.stopTimer("Section1:Cast:LongFloat"); 

      // try to defeat dead code elimination 
      if (f1 == -1.0F) System.out.println(f1); 
      time = JGFInstrumentor.readTimer("Section1:Cast:LongFloat"); 
      JGFInstrumentor.addOpsToTimer("Section1:Cast:LongFloat", (double) 32*size);  
      size *=2; 
    }
   
    JGFInstrumentor.printperfTimer("Section1:Cast:LongFloat"); 

    JGFInstrumentor.addTimer("Section1:Cast:LongDouble", "casts");

    time = 0.0; 
    size = INITSIZE; 
    l1 = 7; 
    
    while (time < TARGETTIME && size < MAXSIZE){
      JGFInstrumentor.resetTimer("Section1:Cast:LongDouble"); 
      JGFInstrumentor.startTimer("Section1:Cast:LongDouble"); 
      for (i=0; i<size; i++){
        d1 = (double) l1; l1 =(long) d1;
        d1 = (double) l1; l1 =(long) d1;
        d1 = (double) l1; l1 =(long) d1;
        d1 = (double) l1; l1 =(long) d1;
        d1 = (double) l1; l1 =(long) d1;
        d1 = (double) l1; l1 =(long) d1;
        d1 = (double) l1; l1 =(long) d1;
        d1 = (double) l1; l1 =(long) d1;
        d1 = (double) l1; l1 =(long) d1;
        d1 = (double) l1; l1 =(long) d1;
        d1 = (double) l1; l1 =(long) d1;
        d1 = (double) l1; l1 =(long) d1;
        d1 = (double) l1; l1 =(long) d1;
        d1 = (double) l1; l1 =(long) d1;
        d1 = (double) l1; l1 =(long) d1;
        d1 = (double) l1; l1 =(long) d1;
      }
      JGFInstrumentor.stopTimer("Section1:Cast:LongDouble"); 

      // try to defeat dead code elimination 
      if (d1 == -1.0D) System.out.println(d1); 
      time = JGFInstrumentor.readTimer("Section1:Cast:LongDouble"); 
      JGFInstrumentor.addOpsToTimer("Section1:Cast:LongDouble", (double) 32*size);  
      size *=2; 
    }
   
    JGFInstrumentor.printperfTimer("Section1:Cast:LongDouble"); 

  }
  
public static void main (String argv[]){

    JGFInstrumentor.printHeader(1,0);

    JGFCastBench cb = new JGFCastBench(); 
    cb.JGFrun();
    
  }
}


