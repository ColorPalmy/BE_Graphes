package org.insa.algo.utils;

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

	public double getCout() {
//		if (!Double.isInfinite(this.cout)) {
//			return this.cout + this.coutToDest;
//		} else {
//			return this.cout;
//		}
		return this.cout + this.coutToDest;
	}
	
	@Override
	public int compareTo(Label o) {
		if (Double.compare(this.getCout(), o.getCout()) == 0) {
			return Double.compare(this.getCoutToDest(),  ((LabelStar)o).getCoutToDest());
		}
		return Double.compare(this.getCout(), o.getCout());
	}

}
