package org.insa.algo.shortestpath;

public class AStarSousCheminTest extends SousCheminTest{
	
	AStarSousCheminTest() {
		super();
	}
	
	protected ShortestPathAlgorithm testedAlgo (ShortestPathData testdata) {
		return new AStarAlgorithm(testdata);
	}
	
	public static void main(String[] args) throws Exception {
		AStarSousCheminTest test = new AStarSousCheminTest();
		test.testExecution();
	}
	
}
