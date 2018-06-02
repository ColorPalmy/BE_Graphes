package org.insa.algo.shortestpath;

import java.util.ArrayList;
import java.util.Collections;

import org.insa.algo.AbstractInputData.Mode;
import org.insa.algo.AbstractSolution.Status;
import org.insa.algo.utils.BinaryHeap;
import org.insa.algo.utils.Label;
import org.insa.algo.utils.LabelStar;
import org.insa.graph.Arc;
import org.insa.graph.Graph;
import org.insa.graph.GraphStatistics;
import org.insa.graph.Node;
import org.insa.graph.Path;

public class AStarAlgorithm extends DijkstraAlgorithm {

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }
    
    @Override
	protected BinaryHeap<Label> initTas() {
		return new BinaryHeap<Label>();
	}

    @Override
	protected LabelStar initLabel(Node node, ShortestPathData data) {
    	double coutToDest = node.getPoint().distanceTo(data.getDestination().getPoint());
    	if (data.getMode() == Mode.LENGTH) {
    		return new LabelStar(node, coutToDest);
    	} else if (data.getMode() == Mode.TIME) {
            //cost in travel time (in sec) at the maximum speed
    		if (data.getMaximumSpeed() == GraphStatistics.NO_MAXIMUM_SPEED) {
    			return new LabelStar(node, coutToDest/(130/3.6));
    		} else {
        		return new LabelStar(node, coutToDest/(data.getMaximumSpeed()/3.6));
    		}
    	}
    	return new LabelStar(node, coutToDest);
//    	return new LabelStar(node, 0.0);
	}
    
    private double createCoutToDest(Node node, ShortestPathData data) {
    	double coutToDest = node.getPoint().distanceTo(data.getDestination().getPoint());
    	if (data.getMode() == Mode.LENGTH) {
    		return coutToDest;
    	} else if (data.getMode() == Mode.TIME) {
            //cost in travel time (in sec) at the maximum speed
    		if (data.getMaximumSpeed() == GraphStatistics.NO_MAXIMUM_SPEED) {
    			return coutToDest/(130/3.6);
    		} else {
        		return coutToDest/(data.getMaximumSpeed()/3.6);
    		}
    	}
    	return coutToDest;
    }
    
    @Override
	protected double conditionCost(Label ly, Label lx, Arc arc, ShortestPathData data) {
		return Math.min(ly.getCout(), lx.getCout() + data.getCost(arc));
	}
    
    @Override
    protected ShortestPathSolution doRun() {

        // Retrieve the graph.
        ShortestPathData data = getInputData();
        Graph graph = data.getGraph();

        final int nbNodes = graph.size();

        // Initialize array of LabelStars.
        LabelStar[] marks = new LabelStar[nbNodes];
        for (Node node: graph) {
            marks[node.getId()] = initLabel(node, data);
        }
        marks[data.getOrigin().getId()].setCout(0.0);

        // Initialize array of predecessors.
        Arc[] predecessorArcs = new Arc[nbNodes];

        //Creation of the BinaryHeap of LabelStars.
//    		BinaryHeap<LabelStar> tas = new BinaryHeap<LabelStar>();
        BinaryHeap<Label> tas = initTas();

        // Notify observers about the first event (origin processed).
        notifyOriginProcessed(data.getOrigin());

        //Insertion du sommet initial dans le tas
        tas.insert(marks[data.getOrigin().getId()]);

        boolean stillExistNotMarked = true;
        Node x;
        LabelStar lx;
        LabelStar ly;
        double oldCost, newCost;
        int nodesNb = 0;

        //Loop while all the nodes are not marked or we find the destination
        while(stillExistNotMarked) {
            stillExistNotMarked = false;
            if (!tas.isEmpty()) {
                lx = (LabelStar)tas.deleteMin();
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
//                    if (ly.getCoutToDest() == 0.0) {
//                    	ly.setCoutToDest(createCoutToDest(ly.getNode(), data));
//                    }
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
				if ((x.equals(data.getDestination())) || (nodesNb >= nbNodes)) {
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
                arc = predecessorArcs[arc.getOrigin().getId()];
            }

            // Reverse the path...
            Collections.reverse(arcs);

            // Create the final solution.
            solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, arcs));
        }

//    		System.out.println("Nombre de nodes : " + nbNodes);
//    		System.out.println("Nombre d'itérations : " + iteration);
//    		System.out.println("Nombre de Jeanne d'Arc : " + nombredejeannedarc);

        return solution;
    }

    
}