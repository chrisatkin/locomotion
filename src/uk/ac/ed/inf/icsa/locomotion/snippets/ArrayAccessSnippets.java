package uk.ac.ed.inf.icsa.locomotion.snippets;

import static com.oracle.graal.replacements.SnippetTemplate.*;
import uk.ac.ed.inf.icsa.locomotion.instrumentation.Instrument;
import uk.ac.ed.inf.icsa.locomotion.instrumentation.Kind;
import uk.ac.ed.inf.icsa.locomotion.nodes.ArrayLoadNode;
import uk.ac.ed.inf.icsa.locomotion.nodes.ArrayStoreNode;

import com.oracle.graal.api.code.CodeCacheProvider;
import com.oracle.graal.api.code.TargetDescription;
import com.oracle.graal.nodes.spi.Replacements;
import com.oracle.graal.replacements.Snippet;
import com.oracle.graal.replacements.SnippetTemplate.AbstractTemplates;
import com.oracle.graal.replacements.SnippetTemplate.Arguments;
import com.oracle.graal.replacements.SnippetTemplate.SnippetInfo;
import com.oracle.graal.replacements.Snippets;

public class ArrayAccessSnippets implements Snippets {
	
	@Snippet
	public static void arrayIndexStore(ArrayStoreNode node) {
		Instrument.get(Kind.Array).store(node.getEntry());
	}
	
	@Snippet
	public static void arrayIndexLoad(ArrayLoadNode node) {	
		Instrument.get(Kind.Array).load(node.getEntry());
	}
	
	public static class Templates extends AbstractTemplates {
				
		private final SnippetInfo methodOnLoad = snippet(ArrayAccessSnippets.class, "arrayIndexLoad");
		private final SnippetInfo methodOnStore = snippet(ArrayAccessSnippets.class, "arrayIndexStore");
		
		public Templates(CodeCacheProvider runtime, Replacements replacements, TargetDescription target) {
			super(runtime, replacements, target);
		}
		
		public void lower(final ArrayStoreNode node) {
			Arguments args = new Arguments(methodOnStore) {{
				add("node", node);
			}};
			
			template(args).instantiate(runtime, node, DEFAULT_REPLACER, args);
		}
		
		public void lower(final ArrayLoadNode node) {
			Arguments args = new Arguments(methodOnLoad) {{
				add("node", node);
			}};
			
			template(args).instantiate(runtime, node, DEFAULT_REPLACER, args);
		}
	}
}
