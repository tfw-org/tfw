package tfw.visualizer;

import tfw.immutable.ila.booleanila.BooleanIla;
import tfw.tsm.BranchProxy;
import tfw.tsm.CommitProxy;
import tfw.tsm.Converter;
import tfw.tsm.ConverterProxy;
import tfw.tsm.EventChannelProxy;
import tfw.tsm.InitiatorProxy;
import tfw.tsm.MultiplexedBranchProxy;
import tfw.tsm.RootProxy;
import tfw.tsm.SynchronizerProxy;
import tfw.tsm.TriggeredCommitProxy;
import tfw.tsm.TriggeredConverterProxy;
import tfw.tsm.ValidatorProxy;
import tfw.tsm.ecd.BooleanECD;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.ila.BooleanIlaECD;
import tfw.visualizer.graph.Graph;
import tfw.visualizer.graph.GraphECD;
import tfw.visualizer.graph.GraphEdgeEitherClassFilter;
import tfw.visualizer.graph.GraphEdgeNeitherClassFilter;
import tfw.visualizer.graph.GraphNodeClassFilter;
import tfw.visualizer.graph.GraphSelectionFilter;

public class NodeEdgeFilterConverter extends Converter {
    private final GraphECD graphECD;
    private final BooleanIlaECD selectedNodesECD;
    private final BooleanECD showBowTieECD;
    private final BooleanECD showBranchesECD;
    private final BooleanECD showCommitsECD;
    private final BooleanECD showConvertersECD;
    private final BooleanECD showEventChannelsECD;
    private final BooleanECD showInitiatorsECD;
    private final BooleanECD showMultiplexedBranchesECD;
    private final BooleanECD showRootsECD;
    private final BooleanECD showSynchronizersECD;
    private final BooleanECD showTriggeredCommitsECD;
    private final BooleanECD showTriggeredConvertersECD;
    private final BooleanECD showValidatorsECD;
    private final BooleanECD showStructureEdgesECD;
    private final BooleanECD showDataFlowEdgesECD;
    private final GraphECD filteredGraphECD;

    public NodeEdgeFilterConverter(
            GraphECD graphECD,
            BooleanIlaECD selectedNodesECD,
            BooleanECD showBowTieECD,
            BooleanECD showBranchesECD,
            BooleanECD showCommitsECD,
            BooleanECD showConvertersECD,
            BooleanECD showEventChannelsECD,
            BooleanECD showInitiatorsECD,
            BooleanECD showMultiplexedBranchesECD,
            BooleanECD showRootsECD,
            BooleanECD showSynchronizersECD,
            BooleanECD showTriggeredCommitsECD,
            BooleanECD showTriggeredConvertersECD,
            BooleanECD showValidatorsECD,
            BooleanECD showStructureEdgesECD,
            BooleanECD showDataFlowEdgesECD,
            GraphECD filteredGraphECD) {
        super(
                "FilterConverter",
                new ObjectECD[] {
                    graphECD,
                    showBowTieECD,
                    showBranchesECD,
                    showCommitsECD,
                    showConvertersECD,
                    showEventChannelsECD,
                    showInitiatorsECD,
                    showMultiplexedBranchesECD,
                    showRootsECD,
                    showSynchronizersECD,
                    showTriggeredCommitsECD,
                    showTriggeredConvertersECD,
                    showValidatorsECD,
                    showStructureEdgesECD,
                    showDataFlowEdgesECD
                },
                new ObjectECD[] {selectedNodesECD},
                new ObjectECD[] {filteredGraphECD});

        this.graphECD = graphECD;
        this.selectedNodesECD = selectedNodesECD;
        this.showBowTieECD = showBowTieECD;
        this.showBranchesECD = showBranchesECD;
        this.showCommitsECD = showCommitsECD;
        this.showConvertersECD = showConvertersECD;
        this.showEventChannelsECD = showEventChannelsECD;
        this.showInitiatorsECD = showInitiatorsECD;
        this.showMultiplexedBranchesECD = showMultiplexedBranchesECD;
        this.showRootsECD = showRootsECD;
        this.showSynchronizersECD = showSynchronizersECD;
        this.showTriggeredCommitsECD = showTriggeredCommitsECD;
        this.showTriggeredConvertersECD = showTriggeredConvertersECD;
        this.showValidatorsECD = showValidatorsECD;
        this.showStructureEdgesECD = showStructureEdgesECD;
        this.showDataFlowEdgesECD = showDataFlowEdgesECD;
        this.filteredGraphECD = filteredGraphECD;
    }

    protected void convert() {
        boolean showBowTie = ((Boolean) get(showBowTieECD)).booleanValue();

        Graph graph = (Graph) get(graphECD);

        if (showBowTie) {
            graph = GraphSelectionFilter.create(graph, (BooleanIla) get(selectedNodesECD));
        }
        if (!((Boolean) get(showBranchesECD)).booleanValue()) {
            graph = GraphNodeClassFilter.create(graph, BranchProxy.class);
        }
        if (!((Boolean) get(showCommitsECD)).booleanValue()) {
            graph = GraphNodeClassFilter.create(graph, CommitProxy.class);
        }
        if (!((Boolean) get(showConvertersECD)).booleanValue()) {
            graph = GraphNodeClassFilter.create(graph, ConverterProxy.class);
        }
        if (!((Boolean) get(showEventChannelsECD)).booleanValue()) {
            graph = GraphNodeClassFilter.create(graph, EventChannelProxy.class);
        }
        if (!((Boolean) get(showInitiatorsECD)).booleanValue()) {
            graph = GraphNodeClassFilter.create(graph, InitiatorProxy.class);
        }
        if (!((Boolean) get(showMultiplexedBranchesECD)).booleanValue()) {
            graph = GraphNodeClassFilter.create(graph, MultiplexedBranchProxy.class);
        }
        if (!((Boolean) get(showRootsECD)).booleanValue()) {
            graph = GraphNodeClassFilter.create(graph, RootProxy.class);
        }
        if (!((Boolean) get(showSynchronizersECD)).booleanValue()) {
            graph = GraphNodeClassFilter.create(graph, SynchronizerProxy.class);
        }
        if (!((Boolean) get(showTriggeredCommitsECD)).booleanValue()) {
            graph = GraphNodeClassFilter.create(graph, TriggeredCommitProxy.class);
        }
        if (!((Boolean) get(showTriggeredConvertersECD)).booleanValue()) {
            graph = GraphNodeClassFilter.create(graph, TriggeredConverterProxy.class);
        }
        if (!((Boolean) get(showValidatorsECD)).booleanValue()) {
            graph = GraphNodeClassFilter.create(graph, ValidatorProxy.class);
        }
        if (!((Boolean) get(showStructureEdgesECD)).booleanValue()) {
            graph = GraphEdgeNeitherClassFilter.create(graph, EventChannelProxy.class);
        }
        if (!((Boolean) get(showDataFlowEdgesECD)).booleanValue()) {
            graph = GraphEdgeEitherClassFilter.create(graph, EventChannelProxy.class);
        }

        set(filteredGraphECD, graph);
    }
}
