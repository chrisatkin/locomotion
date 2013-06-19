package uk.ac.ed.inf.icsa.locomotion;

import static com.oracle.graal.api.code.CodeUtil.getCallingConvention;

import java.util.Map;
import java.util.Map.Entry;

import com.oracle.graal.api.code.CallingConvention.Type;
import com.oracle.graal.api.code.CompilationResult;
import com.oracle.graal.api.code.InvalidInstalledCodeException;
import com.oracle.graal.api.code.SpeculationLog;
import com.oracle.graal.api.meta.ResolvedJavaMethod;
import com.oracle.graal.api.runtime.Graal;
import com.oracle.graal.compiler.GraalCompiler;
import com.oracle.graal.compiler.target.Backend;
import com.oracle.graal.graph.Node;
import com.oracle.graal.graph.Node.Verbosity;
import com.oracle.graal.java.GraphBuilderConfiguration;
import com.oracle.graal.java.GraphBuilderPhase;
import com.oracle.graal.nodes.StructuredGraph;
import com.oracle.graal.nodes.spi.GraalCodeCacheProvider;
import com.oracle.graal.nodes.spi.Replacements;
import com.oracle.graal.phases.OptimisticOptimizations;
import com.oracle.graal.phases.Phase;
import com.oracle.graal.phases.PhasePlan;
import com.oracle.graal.phases.tiers.Suites;
import com.oracle.graal.phases.tiers.SuitesProvider;

public class Locomotion {
	private final GraalCodeCacheProvider runtime;
	private final Backend backend;
	private final Replacements replacements;
	private final Suites suites;
	
	public Locomotion() {
		this.runtime = Graal.getRequiredCapability(GraalCodeCacheProvider.class);
		this.backend = Graal.getRequiredCapability(Backend.class);
		this.replacements = Graal.getRequiredCapability(Replacements.class);
		this.suites = Graal.getRequiredCapability(SuitesProvider.class).createSuites();
		
		System.out.println("[locomotion] using runtime=" + this.runtime.getClass().getName());
	}
	
	public CompilationResult compile(StructuredGraph graph, ResolvedJavaMethod method, final Map<Phase, PhasePlan.PhasePosition> phases) {
		System.out.println("[locomotion] compilation");
		
		return GraalCompiler.compileGraph(
			graph,
			getCallingConvention(this.runtime, Type.JavaCallee, graph.method(), false),
			method,
			this.runtime,
			this.replacements,
			this.backend,
			this.runtime.getTarget(),
			null,
			new PhasePlan() {{
				addPhase(PhasePosition.AFTER_PARSING, new GraphBuilderPhase(runtime, GraphBuilderConfiguration.getEagerDefault(), OptimisticOptimizations.NONE));

				for (Entry<Phase, PhasePlan.PhasePosition> entry: phases.entrySet())
					addPhase(entry.getValue(), entry.getKey());
			}},
			OptimisticOptimizations.ALL,
			new SpeculationLog(),
			this.suites
		);
	}
	
	public void execute(final ResolvedJavaMethod method, final CompilationResult result, final StructuredGraph graph) throws InvalidInstalledCodeException {
		System.out.println("[locomotion] executing");
		
		this.runtime.addMethod(method, result, graph).execute(null, null, null);
	}
	
	public String getGraphIR(StructuredGraph graph) {
		StringBuilder ir = new StringBuilder();
		
		for (Node node: graph.getNodes())
			ir.append(node.toString(Verbosity.All) + "\n");
		
		return ir.toString();
	}
	
	public Replacements getReplacements() {
		return this.replacements;
	}
	
	public GraalCodeCacheProvider getRuntime() {
		return this.runtime;
	}
	
	public StructuredGraph parse(ResolvedJavaMethod method) {
		System.out.println("[locomotion] parsing " + method.getName());
		
		StructuredGraph graph = new StructuredGraph(method);
		new GraphBuilderPhase(this.runtime, GraphBuilderConfiguration.getEagerDefault(), OptimisticOptimizations.NONE).apply(graph);
		return graph;
	}
}
