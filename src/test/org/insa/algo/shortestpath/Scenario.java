package org.insa.algo.shortestpath;

import org.insa.graph.Graph;
import org.insa.graph.Node;

public class Scenario {
	
	/**
	 * Evaluation type
	 **/
	public enum EvaluationType {TIME, DISTANCE}
	
	private EvaluationType type;
	private Graph map;
	private Node origin;
	private Node destination;

	public Scenario(EvaluationType type, Graph graph, Node origin, Node destination) {
		this.type = type;
		this.map = graph;
		this.origin = origin;
		this.destination = destination;
	}

}
