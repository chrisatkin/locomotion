package uk.ac.ed.inf.icsa.locomotion.snippet;

import static com.oracle.graal.replacements.SnippetTemplate.DEFAULT_REPLACER;
import uk.ac.ed.inf.icsa.locomotion.instrumentation.Entry;
import uk.ac.ed.inf.icsa.locomotion.instrumentation.Instrument;
import uk.ac.ed.inf.icsa.locomotion.instrumentation.Kind;
import uk.ac.ed.inf.icsa.locomotion.node.ArrayLoadBehaviourNode;
import uk.ac.ed.inf.icsa.locomotion.node.ArrayStoreBehaviourNode;

import com.oracle.graal.api.code.TargetDescription;
import com.oracle.graal.api.meta.MetaAccessProvider;
import com.oracle.graal.nodes.spi.Replacements;
import com.oracle.graal.replacements.Snippet;
import com.oracle.graal.replacements.SnippetTemplate.AbstractTemplates;
import com.oracle.graal.replacements.SnippetTemplate.Arguments;
import com.oracle.graal.replacements.SnippetTemplate.SnippetInfo;
import com.oracle.graal.replacements.Snippets;

public class InstrumentationSnippets implements Snippets {

	@Snippet
	public static void store(final ArrayStoreBehaviourNode<?> node) {
		Instrument.stores++;
		//Instrument.arrayStores.add(node.getTarget().hashCode());
		//Instrument.get(Kind.Array).store(new Entry(node.getTarget().hashCode()));
	}
	
	@Snippet
	public static void load(final ArrayLoadBehaviourNode<?> node) {
		Instrument.loads++;
		Instrument.arrayLoads.add(node.test());
		//Instrument.get(Kind.Array).load(new Entry(node.getTarget().hashCode()));
	}
	
	public static class Templates extends AbstractTemplates {
		private final SnippetInfo store = snippet(InstrumentationSnippets.class, "store");
		private final SnippetInfo load = snippet(InstrumentationSnippets.class, "load");

		public Templates(MetaAccessProvider runtime, Replacements replacements, TargetDescription target) {
			super(runtime, replacements, target);
		}
		
		public void lower(final ArrayStoreBehaviourNode<?> node) {
			Arguments args = new Arguments(store);
			args.add("node", node);
			
			template(args).instantiate(runtime, node, DEFAULT_REPLACER, args);
		}
		
		public void lower(final ArrayLoadBehaviourNode<?> node) {
			Arguments args = new Arguments(load);
			args.add("node", node);
			
			template(args).instantiate(runtime, node, DEFAULT_REPLACER, args);
		}
	}

}
