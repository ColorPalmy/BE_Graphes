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

public abstract class ValidityTest {
	
	protected String algo;
	
	ValidityTest(String algo) {
		this.algo = algo;
	}
	
	protected abstract ShortestPathAlgorithm testedAlgo (ShortestPathData testdata);
    
    private static ShortestPathAlgorithm oracleAlgo (ShortestPathData testdata) {
    	return new BellmanFordAlgorithm(testdata);
    }
    
    private static ShortestPathSolution solution (ShortestPathAlgorithm algo) {
    	return algo.doRun();
    }

	public void scenarioTest(String mapName, String pathName, Mode mode) throws Exception {
		
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
		Scenario scenario = new Scenario(mode, graph, path.getOrigin(), path.getDestination());
		
		//Create the data for the algorithms
		ShortestPathData testdata = null;
		if (scenario.getType() == Mode.TIME) {
			 testdata = new ShortestPathData(graph, scenario.getOrigin(), scenario.getDestination(), ArcInspectorFactory.getAllFilters().get(2));
		} else if (scenario.getType() == Mode.LENGTH) {
			 testdata = new ShortestPathData(graph, scenario.getOrigin(), scenario.getDestination(), ArcInspectorFactory.getAllFilters().get(0));
		}
		
		//Create the algorithms
		ShortestPathAlgorithm testAlgo = testedAlgo(testdata);
		ShortestPathAlgorithm oracle = oracleAlgo(testdata);
		
		//Create the solutions
		ShortestPathSolution obtainedSolution = solution(testAlgo);
		ShortestPathSolution expectedSolution = solution(oracle);
	
		//Get the shortest paths
		Path dPath = obtainedSolution.getPath();
		Path bPath = expectedSolution.getPath();
		
		if (scenario.getType() == Mode.TIME) {
			if (dPath.getMinimumTravelTime() == bPath.getMinimumTravelTime() ) {
				System.out.println("test OK");
			} else {
				System.out.println("test PAS OK");
			}
			System.out.println("Time "+ this.algo + ": " + dPath.getMinimumTravelTime() + " Time BellmanFord: " + bPath.getMinimumTravelTime() + "\n");
		} else if (scenario.getType() == Mode.LENGTH) {
			if (dPath.getLength() == bPath.getLength()) {
				System.out.println("test OK");
			} else {
				System.out.println("test PAS OK");
			}
			System.out.println("Length " + this.algo + ": " + dPath.getLength() + " Length BellmanFord: " + bPath.getLength()+ "\n");
		}
	}
	
	public void algoIsValid() throws Exception {
		
//		String path = "B:\\Users\\remi\\eclipse-workspace\\MAPS_BE_Graphes";
		String path = "C:\\Users\\linam\\Documents\\INSA\\3A\\2S\\BE_Graphes";
		
	
		String[][] files = new String[5][2];
		files[0][0] = "path_fr31_insa_aeroport_length.path";
		files[0][1] = "haute-garonne.mapgr";
		files[1][0] = "path_fr31_insa_aeroport_time.path";
		files[1][1] = "haute-garonne.mapgr";
		files[2][0] = "path_fr31_insa_bikini_canal.path";
		files[2][1] = "haute-garonne.mapgr";
		files[3][0] = "path_fr31insa_rangueil_insa.path";
		files[3][1] = "insa.mapgr";
		files[4][0] = "path_fr31insa_rangueil_r2.path";
		files[4][1] = "insa.mapgr";
		/*files[5][0] = "path_fr31_insa_bikini_motorcar.path";
		files[5][1] = "haute-garonne.mapgr";
		files[6][0] = "path_be_173101_302442.path";
		files[6][1] = "belgique.mapgr";
		files[7][0] = "path_fr_insa_tour.path";
		files[7][1] = "france.mapgr";*/

		
		for(String[] file : files) {
	        String pathName = path + "\\paths\\" + file[0];
	        String mapName  = path + "\\maps\\" + file[1];
	        System.out.println(file[0] + " & " + file[1]);
			scenarioTest(mapName, pathName, Mode.TIME);
			scenarioTest(mapName, pathName, Mode.LENGTH);
		}
	}

}
