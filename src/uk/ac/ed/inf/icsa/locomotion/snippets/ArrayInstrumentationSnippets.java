package uk.ac.ed.inf.icsa.locomotion.snippets;

import static com.oracle.graal.graph.UnsafeAccess.unsafe;
import static com.oracle.graal.replacements.SnippetTemplate.DEFAULT_REPLACER;

import java.util.Collections;
import java.util.List;
import java.util.LinkedList;

import uk.ac.ed.inf.icsa.locomotion.nodes.ArrayLoadBehaviourNode;
import uk.ac.ed.inf.icsa.locomotion.nodes.ArrayStoreBehaviourNode;

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

@SuppressWarnings("unused")
public class ArrayInstrumentationSnippets implements Snippets {
	private static class ArrayAccess {
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
	
	public static final List<ArrayAccess> stores = Collections.synchronizedList(new LinkedList<ArrayAccess>());
	public static final List<ArrayAccess> loads = Collections.synchronizedList(new LinkedList<ArrayAccess>());
	
	@Snippet
	public static void store(ArrayAccess access) {
		DirectObjectStoreNode.storeLong(access,  ArrayAccess.counterOffset(), 0, access.counter + 1);
	}
	
	@Snippet
	public static void load(ArrayAccess access) {
		DirectObjectStoreNode.storeLong(access, ArrayAccess.counterOffset(), 0, access.counter + 1);
	}
	
	public static class Templates extends AbstractTemplates {
		private final SnippetInfo store = snippet(ArrayInstrumentationSnippets.class, "store");
		private final SnippetInfo load = snippet(ArrayInstrumentationSnippets.class, "load");

		public Templates(MetaAccessProvider runtime, Replacements replacements, TargetDescription target) {
			super(runtime, replacements, target);
		}
		
		public void lower(final ArrayStoreBehaviourNode node) {
			Arguments args = new Arguments(store) {{
				add("access", new ArrayAccess(node.getNodeInfo().toString(), stores));
			}};
			
			template(args).instantiate(runtime, node, DEFAULT_REPLACER, args);
		}
		
		public void lower(final ArrayLoadBehaviourNode node) {
			Arguments args = new Arguments(load) {{
				add("access", new ArrayAccess(node.getNodeInfo().toString(), loads));
			}};
			
			template(args).instantiate(runtime, node, DEFAULT_REPLACER, args);
		}
	}
}