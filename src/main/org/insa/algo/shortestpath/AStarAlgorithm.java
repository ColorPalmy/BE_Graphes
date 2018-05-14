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
    public Label initLabel(Node node,ShortestPathData data) {
    	double coutToDest = node.getPoint().distanceTo(data.getDestination().getPoint());
		return new LabelStar(node, coutToDest);
	}
    
    @Override
	public double conditionCost(double oldCost, Label lx, Arc arc, ShortestPathData data) {
    	if (oldCost == lx.getCout()) {
    		
    	}
		return Math.min(oldCost, lx.getCout()+ data.getCost(arc));
	}
}