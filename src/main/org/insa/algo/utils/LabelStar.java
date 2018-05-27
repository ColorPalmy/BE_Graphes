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

	@Override
	public double getTotalCost() {
		return this.cout + this.coutToDest;
	}
	
	@Override
	public int comparison(Label o) {
		LabelStar l = (LabelStar)o;
		if(0 == (int)Math.signum(this.getTotalCost() - l.getTotalCost())) {
			if(this.coutToDest >= l.coutToDest) {
				return 1;
			} else {
				return 0;
			}
		} else {
			return (int)Math.signum(this.getTotalCost() - l.getTotalCost());
		}
	}

}
