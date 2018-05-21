package org.insa.algo.shortestpath;

public class DijkstraValidityTest extends ValidityTest{
	
    DijkstraValidityTest() {
		super("Dijkstra");
	}

	protected ShortestPathAlgorithm testedAlgo (ShortestPathData testdata) {
    	return new DijkstraAlgorithm(testdata);
    }
    
	public static void main(String[] args) throws Exception {
		DijkstraValidityTest test = new DijkstraValidityTest();
		test.algoIsValid();
	}

	}
