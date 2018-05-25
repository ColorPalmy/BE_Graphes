package org.insa.algo.shortestpath;

public class AStarPerformanceTest extends PerformanceTest {

	public AStarPerformanceTest() {
		super("A*");
	}

	@Override
	protected ShortestPathAlgorithm testedAlgo(ShortestPathData testdata) {
		return new AStarAlgorithm(testdata);
	}

	public static void main(String[] args) throws Exception {
		AStarPerformanceTest test = new AStarPerformanceTest();
		test.algoIsEfficient();
	}
}
