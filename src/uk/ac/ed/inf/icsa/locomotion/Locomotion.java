package uk.ac.ed.inf.icsa.locomotion;

import java.util.Map;

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
import com.oracle.graal.phases.PhasePlan.PhasePosition;

/**
 * @author	Chris Atkin <me@chrisatk.in>
 * @version 1.0
 */
public class Locomotion {
	public static class Configuration {
		public OptimisticOptimizations optimizations;
	}
	
	public enum Position {
		High,
		Mid,
		Low,
		Parsing
	}

	private final GraalCodeCacheProvider runtime;
	private final Backend backend;
	private final Replacements replacements;
	//private final Suites suites;
	private final Configuration configuration;
	private PhasePlan phasePlan;
	
	public Locomotion(Configuration configuration) {
		this.runtime = Graal.getRequiredCapability(GraalCodeCacheProvider.class);
		this.backend = Graal.getRequiredCapability(Backend.class);
		this.replacements = Graal.getRequiredCapability(Replacements.class);
		//this.suites = Graal.getRequiredCapability(SuitesProvider.class).createSuites();
		this.configuration = configuration;
		this.phasePlan = new PhasePlan();
		this.phasePlan.addPhase(PhasePosition.AFTER_PARSING, new GraphBuilderPhase(runtime, GraphBuilderConfiguration.getEagerDefault(), configuration.optimizations));
		
		System.out.println("[locomotion] using runtime=" + this.runtime.getClass().getName());
	}
	
	public CompilationResult compile(StructuredGraph graph, ResolvedJavaMethod method, Map<Phase, Position> phases) {
		System.out.println("[locomotion] compilation");
		
		_addPhasesToSuites(phases);
		
		/*return GraalCompiler.compileMethod(
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
			this.suites
		);*/
		
		return GraalCompiler.compileMethod(runtime, replacements, backend, runtime.getTarget(), method, graph, null, phasePlan, OptimisticOptimizations.ALL, new SpeculationLog());
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
		new GraphBuilderPhase(this.runtime, GraphBuilderConfiguration.getEagerDefault(), configuration.optimizations).apply(graph);
		return graph;
	}
	
	private void _addPhasesToSuites(final Map<Phase, Position> suite) {
		for (Map.Entry<Phase, Position> entry: suite.entrySet()) {
			Phase phase = entry.getKey();
			Position position = entry.getValue();
			
			switch (position) {
				case Low:
					phasePlan.addPhase(PhasePosition.LOW_LEVEL, phase);
					//this.suites.getLowTier().appendPhase(phase);
					//throw new LocomotionError("Low-level not supported");
				
				case Mid:
					phasePlan.addPhase(PhasePosition.MID_LEVEL, phase);
					//this.suites.getMidTier().appendPhase(phase);
					//this.phasePlan.addPhase(PhasePosition.HIGH_LEVEL, phase);
				break;
				
				case High:
					phasePlan.addPhase(PhasePosition.HIGH_LEVEL, phase);
					//this.suites.getHighTier().appendPhase(phase);
					//this.phasePlan.addPhase(PhasePosition.AFTER_PARSING, phase);
				break;
				
				case Parsing:
					phasePlan.addPhase(PhasePosition.AFTER_PARSING, phase);
					break;
			}
		}
	}
}
