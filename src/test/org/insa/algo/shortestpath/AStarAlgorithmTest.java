package org.insa.algo.shortestpath;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.insa.algo.shortestpath.AStarAlgorithm;
import org.insa.graph.Graph;
import org.insa.graph.Node;
import org.insa.graph.Point;
import org.insa.graph.RoadInformation;
import org.insa.graph.RoadInformation.RoadType;
import org.junit.BeforeClass;

public class AStarAlgorithmTest extends DijkstraAlgorithmTest{

	@BeforeClass
    public static void initAll() throws IOException {

        // Create nodes
        nodes = new Node[6];

        Point point = new Point(0,0);

        
        for (int i = 0; i < nodes.length; ++i) {
            nodes[i] = new Node(i, point);
        }

        ArrayList<Point> liste_points = new ArrayList<Point>();
        
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
	
	@Override
    protected ShortestPathAlgorithm testedAlgo (ShortestPathData testdata) {
    	return new AStarAlgorithm(testdata);
    }

}
