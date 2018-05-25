package org.insa.algo.shortestpath;

public class DijkstraPerformanceTest extends PerformanceTest {

	public DijkstraPerformanceTest() {
		super("Dijkstra");
	}

	@Override
	protected ShortestPathAlgorithm testedAlgo(ShortestPathData testdata) {
		return new DijkstraAlgorithm(testdata);
	}
	
	public static void main(String[] args) throws Exception {
		DijkstraPerformanceTest test = new DijkstraPerformanceTest();
		test.algoIsEfficient();
	}

}
