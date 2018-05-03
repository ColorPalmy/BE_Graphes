package org.insa.algo.shortestpath;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;

import org.insa.algo.ArcInspectorFactory;
import org.insa.algo.AbstractInputData.Mode;
import org.insa.graph.Graph;
import org.insa.graph.Path;
import org.insa.graph.io.BinaryGraphReader;
import org.insa.graph.io.BinaryPathReader;
import org.insa.graph.io.GraphReader;
import org.insa.graph.io.PathReader;

public class DijkstraSousCheminTest {
	
public static void scenarioTest(String mapName, String pathName, String subpathName, Mode mode) throws Exception {
		
		// Create a graph reader.
		GraphReader reader = new BinaryGraphReader(
				new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));

		//System.out.println("Graph Reading started.");

		//Read the graph.
		Graph graph = reader.read();

		//System.out.println("Graph Reading ended.");

		//Create a PathReader.
		PathReader pathReader = new BinaryPathReader(
				new DataInputStream(new BufferedInputStream(new FileInputStream(pathName))));

		//Read the path.
		Path path = pathReader.readPath(graph);
		
		//Create a SubPathReader.
		PathReader subpathReader = new BinaryPathReader(
				new DataInputStream(new BufferedInputStream(new FileInputStream(subpathName))));
		
		//Read the subpath.
		Path subpath = subpathReader.readPath(graph);
		
		//Create the scenario
		Scenario scenario = new Scenario(mode, graph, path.getOrigin(), path.getDestination());
		
		//Create the data for the algorithms
		ShortestPathData testdata = null;
		if (scenario.getType() == Mode.TIME) {
			 testdata = new ShortestPathData(graph, scenario.getOrigin(), scenario.getDestination(), ArcInspectorFactory.getAllFilters().get(2));
		} else if (scenario.getType() == Mode.LENGTH) {
			 testdata = new ShortestPathData(graph, scenario.getOrigin(), scenario.getDestination(), ArcInspectorFactory.getAllFilters().get(0));
		}
		
		//Create the algorithms
		ShortestPathAlgorithm dijkstraAlgo = new DijkstraAlgorithm(testdata);
		
		//Create the solutions
		ShortestPathSolution dijkstraSolution = dijkstraAlgo.doRun();
		
		//Create the data for the algorithms
		ShortestPathData subtestdata = null;
		if (scenario.getType() == Mode.TIME) {
			 subtestdata = new ShortestPathData(graph, subpath.getOrigin(), subpath.getDestination(), ArcInspectorFactory.getAllFilters().get(2));
		} else if (scenario.getType() == Mode.LENGTH) {
			 subtestdata = new ShortestPathData(graph, subpath.getOrigin(), subpath.getDestination(), ArcInspectorFactory.getAllFilters().get(0));
		}
		
		//Create the algorithms
		ShortestPathAlgorithm dijkstraSubAlgo = new DijkstraAlgorithm(subtestdata);
		
		//Create the solutions
		ShortestPathSolution dijkstraSubSolution = dijkstraSubAlgo.doRun();

		//Get the shortest paths
		Path dPath = dijkstraSolution.getPath();
		Path sdPath = dijkstraSubSolution.getPath();
		
		if (scenario.getType() == Mode.TIME) {
			System.out.println("Time Path: " + dPath.getMinimumTravelTime() + " Time Sub Path: " + sdPath.getMinimumTravelTime());
		} else if (scenario.getType() == Mode.LENGTH) {
			System.out.println("Length Path: " + dPath.getLength() + " Length Sub Path: " + sdPath.getLength());
		}
		
	}

	public static void main(String[] args) throws Exception {
		String path = "B:\\Users\\remi\\eclipse-workspace\\MAPS_BE_Graphes";
		
        String pathName = path + "\\paths_perso\\" + "path1.path";
        String subpathName = path + "\\paths_perso\\" + "subpath1.path";
        String mapName  = path + "\\maps\\" + "toulouse.mapgr";
        scenarioTest(mapName, pathName, subpathName, Mode.TIME);
		scenarioTest(mapName, pathName, subpathName, Mode.LENGTH);	
		
        pathName = path + "\\paths_perso\\" + "path2.path";
        subpathName = path + "\\paths_perso\\" + "subpath2.path";
        mapName  = path + "\\maps\\" + "midi-pyrenees.mapgr";
		scenarioTest(mapName, pathName, subpathName, Mode.TIME);
		scenarioTest(mapName, pathName, subpathName, Mode.LENGTH);	
		
	}
}
