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

        Point[] points = new Point[6];
        points[0] = new Point(0, 0.5f);
        points[1] = new Point(0.5f, 1);
        points[2] = new Point(0.5f, 0);
        points[3] = new Point(1, 1.5f);
        points[4] = new Point(1.5f, 1);
        points[5] = new Point(1.5f, 0);
        nodes[0] = new Node(0, points[0]);
        nodes[1] = new Node(1, points[1]);
        nodes[2] = new Node(2, points[2]);
        nodes[3] = new Node(3, points[3]);
        nodes[4] = new Node(4, points[4]);
        nodes[5] = new Node(5, points[5]);

        ArrayList<Point> liste_points = new ArrayList<Point>();
        
        liste_points.add(points[0]);
        liste_points.add(points[1]);
        Node.linkNodes(nodes[0], nodes[1], 7,
                new RoadInformation(RoadType.UNCLASSIFIED, null, true, 1, null),
                liste_points);
        
        liste_points.clear();
        liste_points.add(points[0]);
        liste_points.add(points[2]);
        
        Node.linkNodes(nodes[0], nodes[2], 8,
                new RoadInformation(RoadType.UNCLASSIFIED, null, true, 1, null),
                liste_points);
        
        liste_points.clear();
        liste_points.add(points[1]);
        liste_points.add(points[3]);
        
        Node.linkNodes(nodes[1], nodes[3], 4,
                new RoadInformation(RoadType.UNCLASSIFIED, null, true, 1, null),
                liste_points);
        
        liste_points.clear();
        liste_points.add(points[1]);
        liste_points.add(points[4]);
        
        Node.linkNodes(nodes[1], nodes[4], 1,
                new RoadInformation(RoadType.UNCLASSIFIED, null, true, 1, null),
                liste_points);
        
        liste_points.clear();
        liste_points.add(points[1]);
        liste_points.add(points[5]);
        
        Node.linkNodes(nodes[1], nodes[5], 5,
                new RoadInformation(RoadType.UNCLASSIFIED, null, true, 1, null),
                liste_points);
        
        liste_points.clear();
        liste_points.add(points[2]);
        liste_points.add(points[0]);
        
        Node.linkNodes(nodes[2], nodes[0], 7,
                new RoadInformation(RoadType.UNCLASSIFIED, null, true, 1, null),
                liste_points);
        
        liste_points.clear();
        liste_points.add(points[2]);
        liste_points.add(points[1]);
        
        Node.linkNodes(nodes[2], nodes[1], 2,
                new RoadInformation(RoadType.UNCLASSIFIED, null, true, 1, null),
                liste_points);
        
        liste_points.clear();
        liste_points.add(points[2]);
        liste_points.add(points[5]);
        
        Node.linkNodes(nodes[2], nodes[5], 2,
                new RoadInformation(RoadType.UNCLASSIFIED, null, true, 1, null),
                liste_points);
        
        liste_points.clear();
        liste_points.add(points[4]);
        liste_points.add(points[2]);
        
        Node.linkNodes(nodes[4], nodes[2], 2,
                new RoadInformation(RoadType.UNCLASSIFIED, null, true, 1, null),
                liste_points);
        
        liste_points.clear();
        liste_points.add(points[4]);
        liste_points.add(points[3]);
        
        Node.linkNodes(nodes[4], nodes[3], 2,
                new RoadInformation(RoadType.UNCLASSIFIED, null, true, 1, null),
                liste_points);
        
        liste_points.clear();
        liste_points.add(points[4]);
        liste_points.add(points[5]);
        
        Node.linkNodes(nodes[4], nodes[5], 3,
                new RoadInformation(RoadType.UNCLASSIFIED, null, true, 1, null),
                liste_points);
        
        liste_points.clear();
        liste_points.add(points[5]);
        liste_points.add(points[4]);
        
        Node.linkNodes(nodes[5], nodes[4], 3,
                new RoadInformation(RoadType.UNCLASSIFIED, null, true, 1, null),
                liste_points);
        
        liste_points.clear();

        
        graph = new Graph("ID", "", Arrays.asList(nodes), null);
    }
	
	@Override
    protected ShortestPathAlgorithm testedAlgo (ShortestPathData testdata) {
    	return new AStarAlgorithm(testdata);
    }

}
