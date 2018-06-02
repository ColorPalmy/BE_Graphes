package org.insa.algo.shortestpath;

import java.util.ArrayList;
import java.util.Collections;

import org.insa.algo.AbstractSolution.Status;
import org.insa.algo.utils.BinaryHeap;
import org.insa.algo.utils.Label;
import org.insa.graph.Arc;
import org.insa.graph.Graph;
import org.insa.graph.Node;
import org.insa.graph.Path;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

	public DijkstraAlgorithm(ShortestPathData data) {
		super(data);
	}
	
	protected BinaryHeap<Label> initTas() {
		return new BinaryHeap<Label>();
	}
	
	protected Label initLabel(Node node,ShortestPathData data) {
		return new Label(node);
	}
	
	protected double conditionCost(Label ly, Label lx, Arc arc, ShortestPathData data) {
		return Math.min(ly.getCout(), lx.getCout()+ data.getCost(arc));
	}

	@Override
	protected ShortestPathSolution doRun() {

		// Retrieve the graph.
		ShortestPathData data = getInputData();
		Graph graph = data.getGraph();

		final int nbNodes = graph.size();

		// Initialize array of labels.
		Label[] marks = new Label[nbNodes];
		for (Node node: graph) {
			marks[node.getId()] = initLabel(node, data);
		}
		marks[data.getOrigin().getId()].setCout(0.0);

		// Initialize array of predecessors.
		Arc[] predecessorArcs = new Arc[nbNodes];

		//Creation of the BinaryHeap of Labels.
//		BinaryHeap<Label> tas = new BinaryHeap<Label>();
		BinaryHeap<Label> tas = initTas();
		
		// Notify observers about the first event (origin processed).
		notifyOriginProcessed(data.getOrigin());

		//Insertion du sommet initial dans le tas
		tas.insert(marks[data.getOrigin().getId()]);

		boolean stillExistNotMarked = true;
		Node x;
		Label lx;
		Label ly;
		double oldCost, newCost;
		int iteration = 0;
		int nombredejeannedarc = 0;
		int nodesNb = 0;

		//Loop while all the nodes are not marked or we find the destination
		while(stillExistNotMarked) {
			iteration++;
			stillExistNotMarked = false;
			if (!tas.isEmpty()) {
				lx = tas.deleteMin();
				x = lx.getNode();
				notifyNodeMarked(x);
				lx.setMarked(true);
				nodesNb ++;
				//System.out.println("We are at node id =" + lx.getNode().getId());
				//System.out.println(lx.getCout());

				for(Arc arc: x) {
					if (!data.isAllowed(arc)) {
						continue;
					}
					ly = marks[arc.getDestination().getId()];
					if (!ly.isMarked()) {
						stillExistNotMarked = true;
						oldCost = ly.getCout();
						newCost = conditionCost(ly, lx, arc, data);
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
//				if ((x.equals(data.getDestination())) || (nodesNb >= nbNodes)) {
				if (x.equals(data.getDestination())) {
					stillExistNotMarked = false;
				} else {
					stillExistNotMarked = true;
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
//		System.out.println("Nombre d'itérations : " + iteration);
//		System.out.println("Nombre de Jeanne d'Arc : " + nombredejeannedarc);

		return solution;
	}

}
