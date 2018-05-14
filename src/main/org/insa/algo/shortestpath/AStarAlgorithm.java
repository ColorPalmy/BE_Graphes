package org.insa.algo.shortestpath;

import java.util.ArrayList;
import java.util.Collections;

import org.insa.algo.AbstractInputData.Mode;
import org.insa.algo.AbstractSolution.Status;
import org.insa.algo.utils.BinaryHeap;
import org.insa.algo.shortestpath.LabelStar;
import org.insa.graph.Arc;
import org.insa.graph.Graph;
import org.insa.graph.Node;
import org.insa.graph.Path;

public class AStarAlgorithm extends DijkstraAlgorithm {

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }
    
    @Override
	private BinaryHeap<LabelStar> initTas() {
		return new BinaryHeap<LabelStar>();
	}

    @Override
    private Label initLabel(Node node, ShortestPathData data) {
    	double coutToDest = node.getPoint().distanceTo(data.getDestination().getPoint());
    	if (data.getMode() == Mode.LENGTH) {
    		return new LabelStar(node, coutToDest);
    	} else if (data.getMode() == Mode.TIME) {
            //cost in travel time (in sec) at the speed of 130 km/h
    		return new LabelStar(node, coutToDest/36.1);
    	}
    	return new LabelStar(node, coutToDest);
	}
    
    @Override
	private double conditionCost(double oldCost, Label lx, Arc arc, ShortestPathData data) {
    	if (oldCost == lx.getCout()) {
    		
    	}
		return Math.min(oldCost, lx.getCout()+ data.getCost(arc));
	}
}