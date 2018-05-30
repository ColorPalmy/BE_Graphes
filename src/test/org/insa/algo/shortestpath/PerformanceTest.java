package org.insa.algo.shortestpath;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.time.LocalTime;

import org.insa.algo.ArcInspectorFactory;
import org.insa.algo.AbstractInputData.Mode;
import org.insa.graph.Graph;
import org.insa.graph.Path;
import org.insa.graph.AccessRestrictions.AccessMode;
import org.insa.graph.io.BinaryGraphReader;
import org.insa.graph.io.BinaryPathReader;
import org.insa.graph.io.GraphReader;
import org.insa.graph.io.PathReader;

public abstract class PerformanceTest {

	protected String algo;

	private long duration;

	public PerformanceTest(String algo) {
		this.algo = algo;
		this.duration = 0;
	}

	protected abstract ShortestPathAlgorithm testedAlgo (ShortestPathData testdata);

	private ShortestPathSolution solution (ShortestPathAlgorithm algo) {
		ShortestPathSolution s ;
		for (int i = 0; i<3; i++) {
			algo.doRun();
		}
		long start = System.currentTimeMillis();
		s = algo.doRun();
		long end = System.currentTimeMillis();
		this.duration = end - start;
		System.gc();
		return s;
	}

	public void scenarioTest(String mapName, String pathName, Mode mode, AccessMode accessMode) throws Exception {

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

		//Create the scenario
		Scenario scenario = new Scenario(accessMode, mode, graph, path.getOrigin(), path.getDestination());

		//Create the data for the algorithms
		ShortestPathData testdata = null;
		if (scenario.getType() == Mode.TIME) {
			switch (scenario.getAccessType()) {
			case MOTORCAR: {
				testdata = new ShortestPathData(graph, scenario.getOrigin(), scenario.getDestination(), ArcInspectorFactory.getAllFilters().get(3));
				break;
			}
			case FOOT: {
				testdata = new ShortestPathData(graph, scenario.getOrigin(), scenario.getDestination(), ArcInspectorFactory.getAllFilters().get(4));
				break;
			}
			case BICYCLE: {
				testdata = new ShortestPathData(graph, scenario.getOrigin(), scenario.getDestination(), ArcInspectorFactory.getAllFilters().get(6));
				break;
			}
			default:
				testdata = new ShortestPathData(graph, scenario.getOrigin(), scenario.getDestination(), ArcInspectorFactory.getAllFilters().get(2));
				break;
			}
		} else if (scenario.getType() == Mode.LENGTH) {
			switch (scenario.getAccessType()) {
			case MOTORCAR: {
				testdata = new ShortestPathData(graph, scenario.getOrigin(), scenario.getDestination(), ArcInspectorFactory.getAllFilters().get(1));
				break;
			}
			case FOOT: {
				testdata = new ShortestPathData(graph, scenario.getOrigin(), scenario.getDestination(), ArcInspectorFactory.getAllFilters().get(5));
				break;
			}
			case BICYCLE: {
				testdata = new ShortestPathData(graph, scenario.getOrigin(), scenario.getDestination(), ArcInspectorFactory.getAllFilters().get(7));
				break;
			}
			default:
				testdata = new ShortestPathData(graph, scenario.getOrigin(), scenario.getDestination(), ArcInspectorFactory.getAllFilters().get(0));
				break;
			}
		}

		//Create the algorithms
		ShortestPathAlgorithm testAlgo = testedAlgo(testdata);

		//Create the solutions
		ShortestPathSolution obtainedSolution = solution(testAlgo);

		if (!obtainedSolution.isFeasible()) {
			System.out.println("No path found");
		} else {
			//Get the shortest paths
			Path dPath = obtainedSolution.getPath();
			System.out.println("Duration: " + this.duration + "ms");
			if (scenario.getType() == Mode.TIME) {
				System.out.println(scenario.getAccessType().toString() + " Time "+ this.algo + ": " + LocalTime.MIN.plusSeconds((long)dPath.getMinimumTravelTime()).toString());
			} else if (scenario.getType() == Mode.LENGTH) {
				System.out.println(scenario.getAccessType().toString() + " Length " + this.algo + ": " + dPath.getLength() + "m");
			}
		}
	}


	public void algoIsEfficient() throws Exception {

		//	String path = "B:\\Users\\remi\\eclipse-workspace\\MAPS_BE_Graphes";
		String path = "C:\\Users\\linam\\Documents\\INSA\\3A\\2S\\BE_Graphes";


		String[][] files = new String[5][2];
		//Chemin court
		files[0][0] = "short1path_fr_3863719_4100123.path";
		files[0][1] = "france.mapgr";
		files[1][0] = "path_fr31insa_rangueil_r2.path";
		files[1][1] = "insa.mapgr";
		//Chemin mi-long
		files[2][0] = "midpath_fr_3965724_5916340.path";
		files[2][1] = "france.mapgr";
		//Chemin long: d'un bout à l'autre de la France	
		files[3][0] = "longpath_fr_3126266_2529126.path";
		files[3][1] = "france.mapgr";
		//Chemin long: du milieu à un bout de la France
		files[4][0] = "longhardpath_fr_2043086_6034662.path";
		files[4][1] = "france.mapgr";




		for(String[] file : files) {
			String pathName = path + "\\paths_perso\\" + file[0];
			String mapName  = path + "\\maps\\" + file[1];
			System.out.println( "\n" + file[0] + " & " + file[1]);
			scenarioTest(mapName, pathName, Mode.TIME, AccessMode.MOTORCAR);
			scenarioTest(mapName, pathName, Mode.LENGTH, AccessMode.MOTORCAR);
			scenarioTest(mapName, pathName, Mode.TIME, AccessMode.FOOT);
			scenarioTest(mapName, pathName, Mode.LENGTH, AccessMode.FOOT);
			scenarioTest(mapName, pathName, Mode.TIME, AccessMode.BICYCLE);
			scenarioTest(mapName, pathName, Mode.LENGTH, AccessMode.BICYCLE);
		}
	}

}
