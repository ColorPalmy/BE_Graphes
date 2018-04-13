package org.insa.algo.shortestpath;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.insa.graph.Graph;
import org.insa.graph.Node;
import org.insa.graph.RoadInformation;
import org.insa.graph.RoadInformation.RoadType;
import org.junit.BeforeClass;
import org.junit.Test;

public class DijkstraAlgorithmTest {
	
    // Small graph use for tests
    private static Graph graph;

    // List of nodes
    private static Node[] nodes;

    @BeforeClass
    public static void initAll() throws IOException {

        // Create nodes
        nodes = new Node[6];
        for (int i = 0; i < nodes.length; ++i) {
            nodes[i] = new Node(i, null);
        }

        Node.linkNodes(nodes[0], nodes[1], 7,
                new RoadInformation(RoadType.UNCLASSIFIED, null, true, 1, null),
                new ArrayList<>());
        Node.linkNodes(nodes[0], nodes[2], 8,
                new RoadInformation(RoadType.UNCLASSIFIED, null, true, 1, null),
                new ArrayList<>());
        Node.linkNodes(nodes[1], nodes[3], 4,
                new RoadInformation(RoadType.UNCLASSIFIED, null, true, 1, null),
                new ArrayList<>());
        Node.linkNodes(nodes[1], nodes[4], 1,
                new RoadInformation(RoadType.UNCLASSIFIED, null, true, 1, null),
                new ArrayList<>());
        Node.linkNodes(nodes[1], nodes[5], 5,
                new RoadInformation(RoadType.UNCLASSIFIED, null, true, 1, null),
                new ArrayList<>());
        Node.linkNodes(nodes[2], nodes[0], 7,
                new RoadInformation(RoadType.UNCLASSIFIED, null, true, 1, null),
                new ArrayList<>());
        Node.linkNodes(nodes[2], nodes[1], 2,
                new RoadInformation(RoadType.UNCLASSIFIED, null, true, 1, null),
                new ArrayList<>());
        Node.linkNodes(nodes[2], nodes[5], 2,
                new RoadInformation(RoadType.UNCLASSIFIED, null, true, 1, null),
                new ArrayList<>());
        Node.linkNodes(nodes[4], nodes[2], 2,
                new RoadInformation(RoadType.UNCLASSIFIED, null, true, 1, null),
                new ArrayList<>());
        Node.linkNodes(nodes[4], nodes[3], 2,
                new RoadInformation(RoadType.UNCLASSIFIED, null, true, 1, null),
                new ArrayList<>());
        Node.linkNodes(nodes[4], nodes[5], 3,
                new RoadInformation(RoadType.UNCLASSIFIED, null, true, 1, null),
                new ArrayList<>());
        Node.linkNodes(nodes[5], nodes[4], 3,
                new RoadInformation(RoadType.UNCLASSIFIED, null, true, 1, null),
                new ArrayList<>());
        
        graph = new Graph("ID", "", Arrays.asList(nodes), null);

    }

	
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
