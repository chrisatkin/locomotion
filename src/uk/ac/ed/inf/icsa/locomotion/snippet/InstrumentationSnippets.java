package uk.ac.ed.inf.icsa.locomotion.snippet;

import static com.oracle.graal.replacements.SnippetTemplate.DEFAULT_REPLACER;

import uk.ac.ed.inf.icsa.locomotion.instrumentation.Instrument;
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
	public static void store(String s) {
		Instrument.stores++;
		Instrument.addStore(1);
	}
	
	@Snippet
	public static void load(String s) {
		Instrument.loads++;
		Instrument.addLoad(1);
	}
	
	public static class Templates extends AbstractTemplates {
		private final SnippetInfo store = snippet(InstrumentationSnippets.class, "store");
		private final SnippetInfo load = snippet(InstrumentationSnippets.class, "load");

		public Templates(MetaAccessProvider runtime, Replacements replacements, TargetDescription target) {
			super(runtime, replacements, target);
		}
		
		public void lower(final ArrayStoreBehaviourNode<?> node) {
			Arguments args = new Arguments(store) {{
				add("s", node.toString());
			}};
			
			template(args).instantiate(runtime, node, DEFAULT_REPLACER, args);
		}
		
		public void lower(final ArrayLoadBehaviourNode<?> node) {
			Arguments args = new Arguments(load) {{
				add("s", node.toString());
			}};
			
			template(args).instantiate(runtime, node, DEFAULT_REPLACER, args);
		}
	}
}