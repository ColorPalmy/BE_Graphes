package org.insa.algo.shortestpath;

import org.insa.algo.AbstractInputData.Mode;
import org.insa.algo.utils.BinaryHeap;
import org.insa.algo.utils.Label;
import org.insa.algo.utils.LabelStar;
import org.insa.graph.Arc;
import org.insa.graph.Node;

public class AStarAlgorithm extends DijkstraAlgorithm {

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }
    
	private BinaryHeap<Label> initTas() {
		return new BinaryHeap<Label>();
	}

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
    
//    @Override
    private double conditionCost(Label ly, Label lx, Arc arc, ShortestPathData data) {
    	if (ly.getCout() == lx.getCout()) {
    		if (((LabelStar)ly).getCoutToDest() < (((LabelStar)lx).getCoutToDest() + data.getCost(arc))) {
    			return ly.getCout();
    		} else {
    			return lx.getCout()+ data.getCost(arc);
    		}
//    		return Math.min(((LabelStar)ly).getCoutToDest(), ((LabelStar)lx).getCoutToDest() + data.getCost(arc));
    	}
		return Math.min(ly.getCout(), lx.getCout()+ data.getCost(arc));
	}
}