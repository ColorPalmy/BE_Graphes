package org.insa.algo.utils;

import org.insa.graph.Node;

public class Label implements Comparable<Label> {

	private boolean marked;
	protected double cout;
	private Node node;
	
	public Label(Node node) {
		this.marked = false;
		this.node = node;
		this.cout = Double.POSITIVE_INFINITY;
	}
	
	public Label(Node node, boolean mark, double cout) {
		this.marked = mark;
		this.cout = cout;
		this.node = node;
	}	

	public boolean isMarked() {
		return this.marked;
	}
	
	public void setMarked(boolean value) {
		this.marked = value;
	}
	
	public void setCout(double cout) {
		this.cout = cout;
	}
	
	public double getTotalCost() {
		return cout;
	}
	
	public void setTotalCost(double cout) {
		this.cout = cout;
	}

	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

	public double getCout() {
		return cout;
	}

	public int comparison(Label o) {
		return (int)Math.signum(this.getTotalCost() - o.getTotalCost());
	}
	
	@Override
	public int compareTo(Label o) {
		return comparison(o);
	}
}
