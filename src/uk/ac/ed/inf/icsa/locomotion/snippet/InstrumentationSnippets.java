package uk.ac.ed.inf.icsa.locomotion.snippet;

import static com.oracle.graal.graph.UnsafeAccess.unsafe;
import static com.oracle.graal.replacements.SnippetTemplate.DEFAULT_REPLACER;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import uk.ac.ed.inf.icsa.locomotion.instrumentation.Instrument;
import uk.ac.ed.inf.icsa.locomotion.node.ArrayLoadBehaviourNode;
import uk.ac.ed.inf.icsa.locomotion.node.ArrayStoreBehaviourNode;

import com.oracle.graal.api.code.TargetDescription;
import com.oracle.graal.api.meta.MetaAccessProvider;
import com.oracle.graal.graph.GraalInternalError;
import com.oracle.graal.nodes.spi.Replacements;
import com.oracle.graal.replacements.Snippet;
import com.oracle.graal.replacements.Snippet.Fold;
import com.oracle.graal.replacements.SnippetTemplate.AbstractTemplates;
import com.oracle.graal.replacements.SnippetTemplate.Arguments;
import com.oracle.graal.replacements.SnippetTemplate.SnippetInfo;
import com.oracle.graal.replacements.Snippets;
import com.oracle.graal.replacements.nodes.DirectObjectStoreNode;

public class InstrumentationSnippets implements Snippets {
	public static class ArrayAccess {
		public final String name;
		public long counter;
		
		public ArrayAccess(String name, List<ArrayAccess> group) {
			this.name = name;
			group.add(this);
		}
		
		public void inc() {
			DirectObjectStoreNode.storeLong(this, counterOffset(), 0, counter + 1);
		}
		
		@Override
		public String toString() {
			return new StringBuilder().append(name).append(" ").append(counter).toString();
		}
		
		public static long sum(List<ArrayAccess> accesses) {
			long total = 0;
			
			for (ArrayAccess a: accesses)
				total += a.counter;
			
			return total;
		}
		
		@Fold
		private static int counterOffset() {
			try {
				return (int) unsafe.objectFieldOffset(ArrayAccess.class.getDeclaredField("counter"));
			} catch (Exception e) {
				throw new GraalInternalError(e);
			}
		}
	}
	
	public static final List<ArrayAccess> stores = Collections.synchronizedList(new ArrayList<ArrayAccess>());
    public static final List<ArrayAccess> loads = Collections.synchronizedList(new ArrayList<ArrayAccess>());
    
	@Snippet
	public static void store(ArrayAccess access) {
		Instrument.stores++;
		DirectObjectStoreNode.storeLong(access,  ArrayAccess.counterOffset(), 0, access.counter + 1);
	}
	
	@Snippet
	public static void load(ArrayAccess access) {
		Instrument.loads++;
		DirectObjectStoreNode.storeLong(access, ArrayAccess.counterOffset(), 0, access.counter + 1);
	}
	
	public static class Templates extends AbstractTemplates {
		private final SnippetInfo store = snippet(InstrumentationSnippets.class, "store");
		private final SnippetInfo load = snippet(InstrumentationSnippets.class, "load");

		public Templates(MetaAccessProvider runtime, Replacements replacements, TargetDescription target) {
			super(runtime, replacements, target);
		}
		
		public void lower(final ArrayStoreBehaviourNode<?> node) {
			Arguments args = new Arguments(store) {{
				add("access", new ArrayAccess(node.toString(), stores));
			}};
			
			template(args).instantiate(runtime, node, DEFAULT_REPLACER, args);
		}
		
		public void lower(final ArrayLoadBehaviourNode<?> node) {
			Arguments args = new Arguments(load) {{
				add("access", new ArrayAccess(node.toString(), loads));
			}};
			
			template(args).instantiate(runtime, node, DEFAULT_REPLACER, args);
		}
	}
}