package uk.ac.ed.inf.icsa.locomotion.snippets;

import static com.oracle.graal.replacements.SnippetTemplate.*;

import uk.ac.ed.inf.icsa.locomotion.instrumentation.ArrayAccessInstrumentation;
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
	public static void arrayIndexStore() {
		ArrayAccessInstrumentation.store();
	}
	
	@Snippet
	public static void arrayIndexLoad() {	
		ArrayAccessInstrumentation.load();
	}
	
	public static class Templates extends AbstractTemplates {
				
		private final SnippetInfo methodOnLoad = snippet(ArrayAccessSnippets.class, "arrayIndexLoad");
		private final SnippetInfo methodOnStore = snippet(ArrayAccessSnippets.class, "arrayIndexStore");
		
		public Templates(CodeCacheProvider runtime, Replacements replacements, TargetDescription target) {
			super(runtime, replacements, target);
		}
		
		public void lower(ArrayStoreNode node) {
			Arguments args = new Arguments(methodOnStore) {{
				
			}};
			
			template(args).instantiate(runtime, node, DEFAULT_REPLACER, args);
		}
		
		public void lower(ArrayLoadNode node) {
			Arguments args = new Arguments(methodOnLoad) {{
				
			}};
			
			template(args).instantiate(runtime, node, DEFAULT_REPLACER, args);
		}
	}
}
