package org.insa.algo.shortestpath;

import org.insa.graph.Node;

public class LabelStar extends Label {
	
	private double coutToDest;

	public LabelStar(Node node) {
		super(node);
		this.coutToDest = Double.POSITIVE_INFINITY;
	}
	
	public LabelStar(Node node, double coutToDest) {
		super(node);
		this.coutToDest = coutToDest;
	}
	
	public double getCoutToDest() {
		return coutToDest;
	}

	public void setCoutToDest(double coutToDest) {
		this.coutToDest = coutToDest;
	}

	@Override
	public double getCout() {
		if (!Double.isInfinite(this.cout)) {
			return (this.cout + this.coutToDest);
		} else {
			return this.cout;
		}
	}

}
