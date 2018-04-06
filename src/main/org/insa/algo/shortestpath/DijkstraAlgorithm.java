package org.insa.algo.shortestpath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.insa.algo.AbstractSolution.Status;
import org.insa.algo.utils.BinaryHeap;
import org.insa.graph.Arc;
import org.insa.graph.Graph;
import org.insa.graph.Node;
import org.insa.graph.Path;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
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
			marks[node.getId()]= new Label(node);
		}
		marks[data.getOrigin().getId()].setCout(0.0);
		
		// Initialize array of predecessors.
		Arc[] predecessorArcs = new Arc[nbNodes];
		
		//Creation of the BinaryHeap of Labels.
		BinaryHeap<Label> tas = new BinaryHeap<Label>();
		
		// Notify observers about the first event (origin processed).
		notifyOriginProcessed(data.getOrigin());
		
		//Insertion du sommet initial dans le tas
		tas.insert(marks[data.getOrigin().getId()]);
        
        boolean stillExistNotMarked = true;
        Node x;
        Node y;
        Label lx;
        Label ly;
        double oldCost, newCost;
        
        while(stillExistNotMarked) {
        	
        	stillExistNotMarked = false;
        	
        	lx = tas.findMin();
        	x = tas.findMin().getNode();
        	
        	lx.setMarked(true);
        	System.out.println("node markee " + x.getId());
        	
        	for(Arc arc: x) {
        		
        		ly = marks[arc.getDestination().getId()];
        		y = marks[arc.getDestination().getId()].getNode();
        		
        		if (!ly.isMarked()) {
        			
        			stillExistNotMarked = true;
        			
        			oldCost = ly.getCout();
        			newCost = Math.min(oldCost, lx.getCout()+data.getCost(arc));
        			ly.setCout(newCost);
        			
        			if (newCost != oldCost) {
        				tas.insert(ly);
        				predecessorArcs[arc.getDestination().getId()] = arc;
        			}
        		}
        	}
        }
        System.out.println("sorti du while");
        
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
     				arc = predecessorArcs[arc.getOrigin().getId()];
     			}

     			// Reverse the path...
     			Collections.reverse(arcs);

     			// Create the final solution.
     			solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, arcs));
     		}

        return solution;
    }

}
