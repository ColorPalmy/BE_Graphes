package org.insa.algo.shortestpath;

public class AStarValidityTest extends ValidityTest {

	AStarValidityTest() {
		super("A*");
	}

	protected ShortestPathAlgorithm testedAlgo (ShortestPathData testdata) {
		return new AStarAlgorithm(testdata);
	}

	public static void main(String[] args) throws Exception {
		AStarValidityTest test = new AStarValidityTest();
		test.algoIsValid();
	}

}