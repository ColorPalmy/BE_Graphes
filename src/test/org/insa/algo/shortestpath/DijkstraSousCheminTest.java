package org.insa.algo.shortestpath;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.util.List;

import org.insa.algo.ArcInspectorFactory;
import org.insa.algo.AbstractInputData.Mode;
import org.insa.graph.Arc;
import org.insa.graph.Graph;
import org.insa.graph.Node;
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

		//Create a PathReader and a SubPathReader.
		PathReader pathReader = new BinaryPathReader(
				new DataInputStream(new BufferedInputStream(new FileInputStream(pathName))));
		PathReader subpathReader = new BinaryPathReader(
				new DataInputStream(new BufferedInputStream(new FileInputStream(subpathName))));

		//Read the path and the sub path.
		Path path = pathReader.readPath(graph);
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
		ShortestPathData subtestdata = null;
		if (scenario.getType() == Mode.TIME) {
			 subtestdata = new ShortestPathData(graph, subpath.getOrigin(), subpath.getDestination(), ArcInspectorFactory.getAllFilters().get(2));
		} else if (scenario.getType() == Mode.LENGTH) {
			 subtestdata = new ShortestPathData(graph, subpath.getOrigin(), subpath.getDestination(), ArcInspectorFactory.getAllFilters().get(0));
		}
		
		
		//Create the algorithms
		ShortestPathAlgorithm dijkstraAlgo = new DijkstraAlgorithm(testdata);
		ShortestPathAlgorithm dijkstraSubAlgo = new DijkstraAlgorithm(subtestdata);

		//Create the solutions
		ShortestPathSolution dijkstraSolution = dijkstraAlgo.doRun();
		ShortestPathSolution dijkstraSubSolution = dijkstraSubAlgo.doRun();

		//Get the shortest paths
		Path dPath = dijkstraSolution.getPath();
		Path sdPath = dijkstraSubSolution.getPath();
		
		List<Arc> pathArcs = dPath.getArcs();
//		int debut = pathArcs.indexOf(sdPath.getArcs().get(0));
		int debut = -1;
		int i = 0;
		for (Arc arc: dPath.getArcs()) {
			if ((arc.getOrigin().getId() == sdPath.getOrigin().getId()) && (arc.getDestination().getId() == sdPath.getArcs().get(0).getDestination().getId())) {
				debut = i;
				break;
			}
			i++;
		}
		
		if( debut == -1) {
			throw new Exception("the origin of the sub path is not in the path");
		} else {
			boolean same = true;
			float length = 0;
			double time = 0.0;
			for (Arc arc: sdPath.getArcs()) {
				length = length + arc.getLength();
				time = time + arc.getMinimumTravelTime();
				if ((arc.getOrigin().getId() != pathArcs.get(debut).getOrigin().getId()) || (arc.getDestination().getId() != pathArcs.get(debut).getDestination().getId())) {
					same = false;
					System.out.println("subpath origin: " + arc.getOrigin().getId() + " destination: " + arc.getDestination().getId() + " path origin: " + pathArcs.get(debut).getOrigin().getId() + " destination: " + pathArcs.get(debut).getDestination().getId());
//					break;
				}
				debut = debut + 1;
			}
			if (same || ((scenario.getType() == Mode.TIME) && (time == sdPath.getMinimumTravelTime()) ) || ((scenario.getType() == Mode.LENGTH) && (length == sdPath.getLength())) ) {
				System.out.println("The sub path is a shortest path");
			} else {
				System.out.println("The sub path is NOT the shortest path");
			}
			if (scenario.getType() == Mode.TIME) {
				System.out.println("Time subpath in Path: " + time + " Time Sub Path: " + sdPath.getMinimumTravelTime() + "\n");
			} else if (scenario.getType() == Mode.LENGTH) {
				System.out.println("Length subpath in Path: " + length + " Length Sub Path: " + sdPath.getLength() + "\n");
			}
		}

	}

	public static void main(String[] args) throws Exception {
//		String path = "B:\\Users\\remi\\eclipse-workspace\\MAPS_BE_Graphes";
		String path = "C:\\Users\\linam\\Documents\\INSA\\3A\\2S\\BE_Graphes";
<<<<<<< HEAD
        
		String pathName = path + "\\paths_perso\\" + "path1.path";
        String subpathName = path + "\\paths_perso\\" + "subpath1.path";
        String mapName  = path + "\\maps\\" + "toulouse.mapgr";
//      scenarioTest(mapName, pathName, subpathName, Mode.TIME);
//		scenarioTest(mapName, pathName, subpathName, Mode.LENGTH);	
		
        pathName = path + "\\paths_perso\\" + "path_frn_20484_185662.path";
        subpathName = path + "\\paths_perso\\" + "subpath_frn_30798_74902.path";
        mapName  = path + "\\maps\\" + "midi-pyrenees.mapgr";
=======
       
        String pathName = path + "\\paths_perso\\" + "path_frn_20484_185662.path";
        String subpathName = path + "\\paths_perso\\" + "subpath_frn_30798_74902.path";
        String mapName  = path + "\\maps\\" + "midi-pyrenees.mapgr";
		scenarioTest(mapName, pathName, subpathName, Mode.LENGTH);
		pathName = path + "\\paths_perso\\" + "timepath_frn_20484_185662.path";
        subpathName = path + "\\paths_perso\\" + "timesubpath_frn_30798_152367.path";
>>>>>>> ac159d96640c4b1d9d9caa5b77a63d5721341e08
		scenarioTest(mapName, pathName, subpathName, Mode.TIME);
		
		
        pathName = path + "\\paths_perso\\" + "path_fr31insa_52_139.path";
        subpathName = path + "\\paths_perso\\" + "subpath_fr31insa_517_389.path";
        mapName  = path + "\\maps\\" + "insa.mapgr";
		scenarioTest(mapName, pathName, subpathName, Mode.TIME);
		scenarioTest(mapName, pathName, subpathName, Mode.LENGTH);
		
        pathName = path + "\\paths_perso\\" + "path_frn_107733_415883.path";
        subpathName = path + "\\paths_perso\\" + "subpath_frn_88878_481884.path";
        mapName  = path + "\\maps\\" + "midi-pyrenees.mapgr";
		scenarioTest(mapName, pathName, subpathName, Mode.LENGTH);
        pathName = path + "\\paths_perso\\" + "timepath_frn_107733_415883.path";
        subpathName = path + "\\paths_perso\\" + "timesubpath_frn_252493_32738.path";
		scenarioTest(mapName, pathName, subpathName, Mode.TIME);
		
	}
}
