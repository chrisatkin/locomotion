package uk.ac.ed.inf.icsa.locomotion.core;

import static com.oracle.graal.api.code.CodeUtil.getCallingConvention;

import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;
import java.util.logging.Logger;

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
import com.oracle.graal.phases.BasePhase;
import com.oracle.graal.phases.Phase;
import com.oracle.graal.phases.PhasePlan;
import com.oracle.graal.phases.PhasePlan.PhasePosition;
import com.oracle.graal.phases.common.LoweringPhase;
import com.oracle.graal.phases.tiers.HighTierContext;
import com.oracle.graal.phases.tiers.Suites;
import com.oracle.graal.phases.tiers.SuitesProvider;

public class Dispatch {
	private static class CacheItem {
		public ResolvedJavaMethod rjm;
		public StructuredGraph graph;
		public CompilationResult cr;
	}
	
	private final GraalCodeCacheProvider runtime;
	private final Backend backend;
	private final Replacements replacements;
	private final Suites suites;
	private final Configuration configuration;
	private PhasePlan phasePlan;
	private Map<Cycle, CacheItem> cache;
	private Logger log;
	
	public Dispatch(Configuration configuration) {
		this.runtime = Graal.getRequiredCapability(GraalCodeCacheProvider.class);
		this.backend = Graal.getRequiredCapability(Backend.class);
		this.replacements = Graal.getRequiredCapability(Replacements.class);
		this.suites = Graal.getRequiredCapability(SuitesProvider.class).createSuites();
		this.configuration = configuration;
		this.phasePlan = new PhasePlan();
		this.phasePlan.addPhase(PhasePosition.AFTER_PARSING, new GraphBuilderPhase(runtime, GraphBuilderConfiguration.getEagerDefault(), configuration.optimizations));
		this.cache = new HashMap<Cycle, CacheItem>();
		this.log = Logger.getLogger(this.getClass().getName());
		this.log.setLevel(configuration.level);
		
		if (this.configuration.debug)
			this.log.info("runtime=" + this.runtime.getClass().getName() + " backend=" + this.backend.getClass().getName());
	}
	
	public StructuredGraph parse(final Cycle method) {
		if (configuration.debug) {
			log.info("using " + method.getName());
			log.info("parsing");
		}
		
		cache.put(method, new CacheItem());
		
		try {
			ResolvedJavaMethod rjm = MethodUtils.getResolvedMethod(method, runtime);
			StructuredGraph graph = new StructuredGraph(rjm);
			new GraphBuilderPhase(this.runtime, GraphBuilderConfiguration.getEagerDefault(), configuration.optimizations).apply(graph);
			
			cache.get(method).rjm = rjm;
			cache.get(method).graph = graph;
			
			return graph;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public CompilationResult compile(final Cycle method, final Map<Phase, Position> phases) {
		if (configuration.debug)
			log.info("compiling");
		
		_addPhasesToSuites(phases);
		
		CompilationResult cr = GraalCompiler.compileGraph(
			cache.get(method).graph,
			getCallingConvention(this.runtime, Type.JavaCallee, cache.get(method).graph.method(), false),
			cache.get(method).rjm,
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
		
		cache.get(method).cr = cr;
		
		return cr;
	}
	
	public void execute(final Cycle method) throws InvalidInstalledCodeException {
		if (configuration.debug)
			log.info("executing");
		
		this.runtime.addMethod(cache.get(method).rjm, cache.get(method).cr, cache.get(method).graph).execute(new int[] {4, 3, 1, 2, 0}, new int[] {2, 4, 3, 0, 1}, null);
	}
	
	public void process(final Cycle method, final Map<Phase, Position> phases) throws InvalidInstalledCodeException {
		parse(method);
		compile(method, phases);
		execute(method);
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
	
	private void _addPhasesToSuites(final Map<Phase, Position> suite) {
		for (Map.Entry<Phase, Position> entry: suite.entrySet()) {
			Phase phase = entry.getKey();
			Position position = entry.getValue();
			
			if (configuration.debug)
				log.info("adding " + phase.getClass().getName() + " at " + position.getClass().getName() + "." + position);
			
			switch (position) {
				case Low:
					this.suites.getLowTier().appendPhase(phase);
				break;
				
				case Mid:
					this.suites.getMidTier().appendPhase(phase);
				break;
				
				case High:
				    ListIterator<BasePhase<? super HighTierContext>> iter = this.suites.getHighTier().findPhase(LoweringPhase.class);
				    iter.previous();
				    iter.add(phase);
					//this.suites.getHighTier().appendPhase(phase);
				break;
			}
		}
	}
}