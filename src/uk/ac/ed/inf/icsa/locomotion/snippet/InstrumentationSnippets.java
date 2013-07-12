package uk.ac.ed.inf.icsa.locomotion.snippet;

import static com.oracle.graal.graph.UnsafeAccess.unsafe;
import static com.oracle.graal.replacements.SnippetTemplate.DEFAULT_REPLACER;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import uk.ac.ed.inf.icsa.locomotion.node.LoadBehaviourNode;
import uk.ac.ed.inf.icsa.locomotion.node.StoreBehaviourNode;

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
		
		public ArrayAccess(String name, Set<ArrayAccess> group) {
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
		
		public static long sum(Set<ArrayAccess> accesses) {
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
	
	public static final Set<ArrayAccess> stores = Collections.synchronizedSet(new HashSet<ArrayAccess>());
    public static final Set<ArrayAccess> loads = Collections.synchronizedSet(new HashSet<ArrayAccess>());
    
	@Snippet
	public static void store(ArrayAccess access) {
		DirectObjectStoreNode.storeLong(access,  ArrayAccess.counterOffset(), 0, access.counter + 1);
	}
	
	@Snippet
	public static void load(ArrayAccess access) {
		DirectObjectStoreNode.storeLong(access, ArrayAccess.counterOffset(), 0, access.counter + 1);
	}
	
	public static class Templates extends AbstractTemplates {
		private final SnippetInfo store = snippet(InstrumentationSnippets.class, "store");
		private final SnippetInfo load = snippet(InstrumentationSnippets.class, "load");

		public Templates(MetaAccessProvider runtime, Replacements replacements, TargetDescription target) {
			super(runtime, replacements, target);
		}
		
		public void lower(final StoreBehaviourNode node) {
			Arguments args = new Arguments(store) {{
				add("access", new ArrayAccess(node.getNodeInfo().toString(), stores));
			}};
			
			template(args).instantiate(runtime, node, DEFAULT_REPLACER, args);
		}
		
		public void lower(final LoadBehaviourNode node) {
			Arguments args = new Arguments(load) {{
				add("access", new ArrayAccess(node.getNodeInfo().toString(), loads));
			}};
			
			template(args).instantiate(runtime, node, DEFAULT_REPLACER, args);
		}
	}
}