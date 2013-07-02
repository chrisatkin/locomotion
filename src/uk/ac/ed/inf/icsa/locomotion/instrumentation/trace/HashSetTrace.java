package uk.ac.ed.inf.icsa.locomotion.instrumentation.trace;

import java.util.HashSet;

import uk.ac.ed.inf.icsa.locomotion.instrumentation.Entry;

public class HashSetTrace extends SetTrace {

	public HashSetTrace() {
		super(new HashSet<Entry>());
	}

}
