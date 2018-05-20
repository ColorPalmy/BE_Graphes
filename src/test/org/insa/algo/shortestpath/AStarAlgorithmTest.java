package org.insa.algo.shortestpath;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.insa.algo.ArcInspectorFactory;
import org.insa.algo.shortestpath.BellmanFordAlgorithm;
import org.insa.algo.shortestpath.AStarAlgorithm;
import org.insa.graph.Graph;
import org.insa.graph.Node;
import org.insa.graph.Point;
import org.insa.graph.RoadInformation;
import org.insa.graph.RoadInformation.RoadType;
import org.junit.BeforeClass;
import org.junit.Test;

public class AStarAlgorithmTest {

    // Small graph use for tests
    private static Graph graph;

    // List of nodes
    private static Node[] nodes;

    @BeforeClass
    
    public static void initAll() throws IOException {

    	Point p1 = new Point(0,0);
        Point p2 = new Point(0,0);
        ArrayList<Point> liste_points = new ArrayList<Point>();
        liste_points.add(p1);
        liste_points.add(p2);
    	
        // Create nodes
        nodes = new Node[6];
//        for (int i = 0; i < nodes.length; ++i) {
//            nodes[i] = new Node(i, p1);
//        }
        nodes[0] = new Node(0, new Point(0, 0.5f));
        nodes[1] = new Node(1, new Point(0.5f, 1));
        nodes[2] = new Node(2, new Point(0.5f, 0));
        nodes[3] = new Node(3, new Point(1, 1.5f));
        nodes[4] = new Node(4, new Point(1.5f, 1));
        nodes[5] = new Node(5, new Point(1.5f, 0));


        Node.linkNodes(nodes[0], nodes[1], 7,
                new RoadInformation(RoadType.UNCLASSIFIED, null, true, 1, null),
                liste_points);
        Node.linkNodes(nodes[0], nodes[2], 8,
                new RoadInformation(RoadType.UNCLASSIFIED, null, true, 1, null),
                liste_points);
        Node.linkNodes(nodes[1], nodes[3], 4,
                new RoadInformation(RoadType.UNCLASSIFIED, null, true, 1, null),
                liste_points);
        Node.linkNodes(nodes[1], nodes[4], 1,
                new RoadInformation(RoadType.UNCLASSIFIED, null, true, 1, null),
                liste_points);
        Node.linkNodes(nodes[1], nodes[5], 5,
                new RoadInformation(RoadType.UNCLASSIFIED, null, true, 1, null),
                liste_points);
        Node.linkNodes(nodes[2], nodes[0], 7,
                new RoadInformation(RoadType.UNCLASSIFIED, null, true, 1, null),
                liste_points);
        Node.linkNodes(nodes[2], nodes[1], 2,
                new RoadInformation(RoadType.UNCLASSIFIED, null, true, 1, null),
                liste_points);
        Node.linkNodes(nodes[2], nodes[5], 2,
                new RoadInformation(RoadType.UNCLASSIFIED, null, true, 1, null),
                liste_points);
        Node.linkNodes(nodes[4], nodes[2], 2,
                new RoadInformation(RoadType.UNCLASSIFIED, null, true, 1, null),
                liste_points);
        Node.linkNodes(nodes[4], nodes[3], 2,
                new RoadInformation(RoadType.UNCLASSIFIED, null, true, 1, null),
                liste_points);
        Node.linkNodes(nodes[4], nodes[5], 3,
                new RoadInformation(RoadType.UNCLASSIFIED, null, true, 1, null),
                liste_points);
        Node.linkNodes(nodes[5], nodes[4], 3,
                new RoadInformation(RoadType.UNCLASSIFIED, null, true, 1, null),
                liste_points);
        
        graph = new Graph("ID", "", Arrays.asList(nodes), null);
    }
	
	@Test
    public void BellmanAStarSameShortestPath() {
		String s = "";
		//Generate pairs
		for (int i = 0; i < nodes.length; ++i) {
			for (int j = 0; j < nodes.length; ++j) {
				if (i != j) {
					ShortestPathData testdata = new ShortestPathData(graph, nodes[i], nodes[j], ArcInspectorFactory.getAllFilters().get(0));
					
					ShortestPathAlgorithm AStarAlgo = new AStarAlgorithm(testdata);
					ShortestPathAlgorithm bellmanAlgo = new BellmanFordAlgorithm(testdata);
					
					ShortestPathSolution AStarSolution = AStarAlgo.doRun();
					ShortestPathSolution bellmanSolution = bellmanAlgo.doRun();
					
					//Check if both solutions are feasible or unfeasible
					assertEquals(AStarSolution.isFeasible(), bellmanSolution.isFeasible());
					
					if (AStarSolution.isFeasible() && bellmanSolution.isFeasible()) {
						//Check if both paths are valid
						assertEquals(AStarSolution.getPath().isValid(), bellmanSolution.getPath().isValid());
						//Check if both paths have the same length
						assertEquals(AStarSolution.getPath().getLength(), bellmanSolution.getPath().getLength(), 1e-6);
						//Check if both paths take the same time
						assertEquals(AStarSolution.getPath().getMinimumTravelTime(), bellmanSolution.getPath().getMinimumTravelTime(), 1e-6);
						//Check if both paths have the same size
						assertEquals(AStarSolution.getPath().size(), bellmanSolution.getPath().size());
						s += AStarSolution.getPath().getLength() + " ";
					} else {
						s += "inf ";
					}
					
				} else {
					s += "- ";
				}
	        }
			System.out.println(s);
			s = "";
		}
    }

}
