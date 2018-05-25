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
	
//	@Override
//	public int comparison(Label o) {
//	if (Double.compare(this.getTotalCost(), o.getTotalCost()) == 0) {
//		System.out.println("labelstar");
//		return Double.compare(this.getCoutToDest(),  ((LabelStar)o).getCoutToDest());
//	}
//	return Double.compare(this.getTotalCost(), o.getTotalCost());
//	}
	
//	@Override
//	public int compareTo(Label o) {
//		System.out.println("labelStar");
//		if (Double.compare(this.getCout(), o.getCout()) == 0) {
//			return Double.compare(this.getCoutToDest(),  ((LabelStar)o).getCoutToDest());
//		}
//		return Double.compare(this.getCout(), o.getCout());
//	}

}
