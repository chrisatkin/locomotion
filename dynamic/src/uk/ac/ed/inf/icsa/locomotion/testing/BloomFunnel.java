package uk.ac.ed.inf.icsa.locomotion.testing;

import uk.ac.ed.inf.icsa.locomotion.instrumentation.Access;

import com.google.common.hash.Funnel;
import com.google.common.hash.PrimitiveSink;

final class BloomFunnel implements Funnel<Access> {
	@Override
	public void funnel(Access access, PrimitiveSink sink) {
		sink.putInt(access.getArrayId())
			.putInt(access.getIndex());
	}
}