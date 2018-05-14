package org.insa.algo.shortestpath;

import java.util.ArrayList;
import java.util.Collections;

import org.insa.algo.AbstractSolution.Status;
import org.insa.algo.utils.BinaryHeap;
import org.insa.graph.Arc;
import org.insa.graph.Graph;
import org.insa.graph.Node;
import org.insa.graph.Path;

public class AStarAlgorithm extends DijkstraAlgorithm {

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }
    @Override

	protected ShortestPathSolution doRun() {

		// Retrieve the graph.
		ShortestPathData data = getInputData();
		Graph graph = data.getGraph();

		final int nbNodes = graph.size();

		// Initialize array of labels.
		LabelStar[] marks = new LabelStar[nbNodes];
		for (Node node: graph) {
			double coutToDest = node.getPoint().distanceTo(data.getDestination().getPoint());
			marks[node.getId()] = new LabelStar(node, coutToDest);
		}
		marks[data.getOrigin().getId()].setCout(0.0);

		// Initialize array of predecessors.
		Arc[] predecessorArcs = new Arc[nbNodes];

		//Creation of the BinaryHeap of Labels.
		BinaryHeap<LabelStar> tas = new BinaryHeap<LabelStar>();

		// Notify observers about the first event (origin processed).
		notifyOriginProcessed(data.getOrigin());

		//Insertion du sommet initial dans le tas
		tas.insert(marks[data.getOrigin().getId()]);

		boolean stillExistNotMarked = true;
		Node x;
		LabelStar lx;
		LabelStar ly;
		double oldCost, newCost;
		int iteration = 0;
		int nombredejeannedarc = 0;

		//Loop while all the nodes are not marked
		while(stillExistNotMarked) {
			iteration++;
			stillExistNotMarked = false;
			if (!tas.isEmpty()) {
				lx = tas.deleteMin();
				x = lx.getNode();
				notifyNodeMarked(x);
				lx.setMarked(true);
				//System.out.println(lx.getCout());

				for(Arc arc: x) {
					if (!data.isAllowed(arc)) {
						continue;
					}
					ly = marks[arc.getDestination().getId()];
					if (!ly.isMarked()) {
						stillExistNotMarked = true;
						oldCost = ly.getCout();
						newCost = Math.min(oldCost, lx.getCout()+data.getCost(arc));
						//System.out.println("old cost "+ oldCost + " other cost " + (lx.getCout()+data.getCost(arc)) + " new cost "+ newCost);
						ly.setCout(newCost);
						if (newCost != oldCost) {
							tas.insert(ly);
							predecessorArcs[arc.getDestination().getId()] = arc;
						}
						if (Double.isInfinite(oldCost) && Double.isFinite(newCost)) {
							notifyNodeReached(ly.getNode());
						}
					}
				}
				for (Node node: graph) {
					if (!marks[node.getId()].isMarked()) {
						stillExistNotMarked = true;
						break;
					}
				}
			}

		}
		
		ShortestPathSolution solution = null;

		// Destination has no predecessor, the solution is infeasible...
		if (predecessorArcs[data.getDestination().getId()] == null) {
			solution = new ShortestPathSolution(data, Status.INFEASIBLE);
		} else {

			// The destination has been found, notify the observers.
			notifyDestinationReached(data.getDestination());

			// Create the path from the array of predecessors...
			ArrayList<Arc> arcs = new ArrayList<>();
			Arc arc = predecessorArcs[data.getDestination().getId()];
			while (arc != null) {
				arcs.add(arc);
				nombredejeannedarc++;
				arc = predecessorArcs[arc.getOrigin().getId()];
			}

			// Reverse the path...
			Collections.reverse(arcs);

			// Create the final solution.
			solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, arcs));
		}
		
//		System.out.println("Nombre de nodes : " + nbNodes);
//		System.out.println("Nombre d'it�rations : " + iteration);
//		System.out.println("Nombre de Jeanne d'Arc : " + nombredejeannedarc);

		return solution;
	}

}
