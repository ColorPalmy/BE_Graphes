package org.insa.algo.shortestpath;

import org.insa.algo.AbstractInputData.Mode;
import org.insa.graph.AccessRestrictions.AccessMode;
import org.insa.graph.Graph;
import org.insa.graph.Node;

public class Scenario {
	
	private Mode type;
	private AccessMode accessType;
	private Graph map;
	private Node origin;
	private Node destination;

	public Scenario(AccessMode accessType, Mode type, Graph graph, Node origin, Node destination) {
		this.type = type;
		this.map = graph;
		this.origin = origin;
		this.destination = destination;
		this.accessType = accessType;
	}

	public Mode getType() {
		return type;
	}

	public void setType(Mode type) {
		this.type = type;
	}

	public Graph getMap() {
		return map;
	}

	public void setMap(Graph map) {
		this.map = map;
	}

	public Node getOrigin() {
		return origin;
	}

	public void setOrigin(Node origin) {
		this.origin = origin;
	}

	public Node getDestination() {
		return destination;
	}

	public void setDestination(Node destination) {
		this.destination = destination;
	}

	public AccessMode getAccessType() {
		return accessType;
	}

	public void setAccessType(AccessMode accessType) {
		this.accessType = accessType;
	}

}
