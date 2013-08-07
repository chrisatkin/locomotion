package uk.ac.ed.inf.icsa.locomotion.benchmarks.generated;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;

import uk.ac.ed.inf.icsa.locomotion.instrumentation.AccessKind;

public final class FractionalGenerator extends Generator {
	private int num_ww, num_wr, num_rw;
	
	public FractionalGenerator(int length, int num_ww, int num_wr, int num_rw) {
		super(length);
		
		assert (num_ww + num_wr + num_rw) <= length: "configuration implies more dependencies than length";
		
		this.num_ww = num_ww;
		this.num_wr = num_wr;
		this.num_rw = num_rw;
	}

	@Override
	public void generate() {
		AccessKind[] first;// = new Kind[length];
		AccessKind[] second;// = new Kind[length];
		Integer[] array = new Integer[length];
		
		StaticGenerator gen;
		
		// Write write
		AccessKind[] ww_first, ww_second;
		gen = new StaticGenerator(num_ww, AccessKind.Store, AccessKind.Store);
		gen.generate();
		ww_first = gen.getFirst();
		ww_second = gen.getSecond();
		
		// Write-read
		AccessKind[] wr_first, wr_second;
		gen = new StaticGenerator(num_wr, AccessKind.Store, AccessKind.Load);
		gen.generate();
		wr_first = gen.getFirst();
		wr_second = gen.getSecond();
		
		// Read-write
		AccessKind[] rw_first, rw_second;
		gen = new StaticGenerator(num_rw, AccessKind.Load, AccessKind.Store);
		gen.generate();
		rw_first = gen.getFirst();
		rw_second = gen.getSecond();
		
		// Any left?
		int left = length - (num_ww + num_rw + num_wr);
		AccessKind[] rr_first, rr_second;
		gen = new StaticGenerator(left, AccessKind.Load, AccessKind.Load);
		gen.generate();
		rr_first = gen.getFirst();
		rr_second = gen.getSecond();
		
		// Add lists together		
		first = ArrayUtils.addAll(ww_first, wr_first);
		first = ArrayUtils.addAll(first, rw_first);
		first = ArrayUtils.addAll(first, rr_first);
	
		second = ArrayUtils.addAll(ww_second, wr_second);
		second = ArrayUtils.addAll(second, rw_second);
		second = ArrayUtils.addAll(second, rr_second);
		
		for (int i = 0; i < length; i++)
			array[i] = i;
		
		this.first = first;
		this.second = second;
		this.array = array;
	}
}