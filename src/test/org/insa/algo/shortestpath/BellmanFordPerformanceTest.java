package org.insa.algo.shortestpath;

public class BellmanFordPerformanceTest extends PerformanceTest {

	public BellmanFordPerformanceTest() {
		super("BellmanFord");
	}

	@Override
	protected ShortestPathAlgorithm testedAlgo(ShortestPathData testdata) {
		return new BellmanFordAlgorithm(testdata);
	}
	
	public static void main(String[] args) throws Exception {
		BellmanFordPerformanceTest test = new BellmanFordPerformanceTest();
		test.algoIsEfficient();
	}

}
