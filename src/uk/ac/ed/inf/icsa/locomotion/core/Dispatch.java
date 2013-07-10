package uk.ac.ed.inf.icsa.locomotion.core;

import static com.oracle.graal.api.code.CodeUtil.*;

import java.util.Map;

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
import com.oracle.graal.phases.Phase;
import com.oracle.graal.phases.PhasePlan;
import com.oracle.graal.phases.PhasePlan.PhasePosition;
import com.oracle.graal.phases.tiers.Suites;
import com.oracle.graal.phases.tiers.SuitesProvider;

/**
 * @author	Chris Atkin <me@chrisatk.in>
 * @version 1.0
 */
public class Dispatch {
	private final GraalCodeCacheProvider runtime;
	private final Backend backend;
	private final Replacements replacements;
	private final Suites suites;
	private final Configuration configuration;
	private PhasePlan phasePlan;
	
	public Dispatch(Configuration configuration) {
		this.runtime = Graal.getRequiredCapability(GraalCodeCacheProvider.class);
		this.backend = Graal.getRequiredCapability(Backend.class);
		this.replacements = Graal.getRequiredCapability(Replacements.class);
		this.suites = Graal.getRequiredCapability(SuitesProvider.class).createSuites();
		this.configuration = configuration;
		this.phasePlan = new PhasePlan();
		this.phasePlan.addPhase(PhasePosition.AFTER_PARSING, new GraphBuilderPhase(runtime, GraphBuilderConfiguration.getEagerDefault(), configuration.optimizations));
		
		System.out.println("[locomotion] using runtime=" + this.runtime.getClass().getName());
	}
	
	public CompilationResult compile(StructuredGraph graph, ResolvedJavaMethod method, Map<Phase, Position> phases) {
		System.out.println("[locomotion] compilation");
		
		for (Map.Entry<Phase, Position> entry: phases.entrySet()) {
			Phase phase = entry.getKey();
			Position position = entry.getValue();
			
			switch (position) {
				case Low:
					this.suites.getLowTier().appendPhase(phase);
				break;
				
				case Mid:
					this.suites.getMidTier().appendPhase(phase);
				break;
				
				case High:
					this.suites.getHighTier().appendPhase(phase);
				break;
			}
		}
		
		return GraalCompiler.compileGraph(
			graph,
			getCallingConvention(this.runtime, Type.JavaCallee, graph.method(), false),
			method,
			this.runtime,
			this.replacements,
			this.backend,
			this.runtime.getTarget(),
			null,
			this.phasePlan,
			configuration.optimizations,
			new SpeculationLog(),
			this.suites,
			new CompilationResult()
		);
	}
	
	public void execute(final ResolvedJavaMethod method, final CompilationResult result, final StructuredGraph graph) throws InvalidInstalledCodeException {
		System.out.println("[locomotion] executing");
		
		this.runtime.addMethod(method, result, graph).execute(new int[] {4, 3, 1, 2, 0}, new int[] {2, 4, 3, 0, 1}, null);
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
		new GraphBuilderPhase(this.runtime, GraphBuilderConfiguration.getEagerDefault(), configuration.optimizations).apply(graph);
		return graph;
	}
}
