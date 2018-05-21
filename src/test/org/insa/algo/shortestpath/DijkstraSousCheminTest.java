package org.insa.algo.shortestpath;

public class DijkstraSousCheminTest extends SousCheminTest {

	DijkstraSousCheminTest() {
		super();
	}
	
	protected ShortestPathAlgorithm testedAlgo (ShortestPathData testdata) {
		return new DijkstraAlgorithm(testdata);
	}
	
	public static void main(String[] args) throws Exception {
		DijkstraSousCheminTest test = new DijkstraSousCheminTest();
		test.testExecution();
	}

}
