package org.insa.algo.shortestpath;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.time.LocalTime;

import org.insa.algo.ArcInspectorFactory;
import org.insa.algo.AbstractInputData.Mode;
import org.insa.graph.AccessRestrictions.AccessMode;
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
		ShortestPathAlgorithm oracle = oracleAlgo(testdata);
		
		//Create the solutions
		ShortestPathSolution obtainedSolution = solution(testAlgo);
		ShortestPathSolution expectedSolution = solution(oracle);
	
		if (!obtainedSolution.isFeasible() && !expectedSolution.isFeasible()) {
			System.out.println("test OK: no path found");
		} else if (obtainedSolution.isFeasible() && !expectedSolution.isFeasible()) {
			System.out.println("test NOT OK: path found but shouldnt ");
		} else if (!obtainedSolution.isFeasible() && expectedSolution.isFeasible()) {
			System.out.println("test NOT OK: no path found but should");
		} else {
			//Get the shortest paths
			Path dPath = obtainedSolution.getPath();
			Path bPath = expectedSolution.getPath();
			
			if (scenario.getType() == Mode.TIME) {
				if (dPath.getMinimumTravelTime() == bPath.getMinimumTravelTime() ) {
					System.out.println("test OK");
				} else {
					System.out.println("test NOT OK");
				}
				System.out.println(scenario.getAccessType().toString() + " Time "+ this.algo + ": " + LocalTime.MIN.plusSeconds((long)dPath.getMinimumTravelTime()).toString()  + " Time BellmanFord: " + LocalTime.MIN.plusSeconds((long)bPath.getMinimumTravelTime()).toString());
			} else if (scenario.getType() == Mode.LENGTH) {
				if (dPath.getLength() == bPath.getLength()) {
					System.out.println("test OK");
				} else {
					System.out.println("test NOT OK");
				}
				System.out.println(scenario.getAccessType().toString() + " Length " + this.algo + ": " + dPath.getLength() + "m Length BellmanFord: " + bPath.getLength()+ "m");
			}
		}
	}
	
	public void algoIsValid() throws Exception {
		
//		String path = "B:\\Users\\remi\\eclipse-workspace\\MAPS_BE_Graphes";
		String path = "C:\\Users\\linam\\Documents\\INSA\\3A\\2S\\BE_Graphes";
		
	
		String[][] files = new String[21][2];
		
		files[0][0] = "path_fr31_insa_aeroport_length.path";
		files[0][1] = "haute-garonne.mapgr";
		files[1][0] = "path_fr31_insa_aeroport_time.path";
		files[1][1] = "haute-garonne.mapgr";
		files[2][0] = "path_fr31_insa_bikini_canal.path";
		files[2][1] = "haute-garonne.mapgr";
		//chemin uniquement voiture
		files[3][0] = "path_fr31_highway_152765_152762.path";
		files[3][1] = "haute-garonne.mapgr";
		//chemins uniquement piétons
		files[4][0] = "path_fr31_pedestrian_39847_39843.path";
		files[4][1] = "haute-garonne.mapgr";
		
		files[5][0] = "path_fr31insa_rangueil_insa.path";
		files[5][1] = "insa.mapgr";
		files[6][0] = "path_fr31insa_rangueil_r2.path";
		files[6][1] = "insa.mapgr";

		//chemins uniquement piétons
		files[7][0] = "path_frn_pedestrian_609543_360479.path";
		files[7][1] = "midi-pyrenees.mapgr";
		files[8][0] = "path_frn_pedestrian_214906_436038.path";
		files[8][1] = "midi-pyrenees.mapgr";
		//chemin uniquement voiture
		files[9][0] = "path_frn_highway_p1_32224_288320.path";
		files[9][1] = "midi-pyrenees.mapgr";
		//chemins dans les 2 sens
		files[10][0] = "path_frn_67506_35474.path";
		files[10][1] = "midi-pyrenees.mapgr";
		files[11][0] = "path_frn_35474_67506.path";
		files[11][1] = "midi-pyrenees.mapgr";
		// chemins ABC puis AB et BC
		files[12][0] = "ABC_car_l_path_frn_558384_293387.path";
		files[12][1] = "midi-pyrenees.mapgr";
		files[13][0] = "AB_car_path_frn_558384_181643.path";
		files[13][1] = "midi-pyrenees.mapgr";
		files[14][0] = "AB_foot_path_frn_558384_154982.path";
		files[14][1] = "midi-pyrenees.mapgr";
		files[15][0] = "BC_car_path_frn_181643_293387.path";
		files[15][1] = "midi-pyrenees.mapgr";
		files[16][0] = "BC_foot_path_frn_154982_293387.path";
		files[16][1] = "midi-pyrenees.mapgr";
		
		files[17][0] = "path_frn_446860_590226.path";
		files[17][1] = "midi-pyrenees.mapgr";
		files[18][0] = "path_frn_590226_446860.path";
		files[18][1] = "midi-pyrenees.mapgr";
		files[19][0] = "path_frn_250708_146563.path";
		files[19][1] = "midi-pyrenees.mapgr";
		files[20][0] = "path_frn_146563_250708.path";
		files[20][1] = "midi-pyrenees.mapgr";
		
        String pathName;
        String mapName  = path + "\\maps\\" + "haute-garonne.mapgr";
        
//		for (int i = 0; i<5; i++) {
//			pathName = path + "\\paths_perso\\" + files[i][0];
//			scenarioTest(mapName, pathName, Mode.TIME, AccessMode.MOTORCAR);
//			scenarioTest(mapName, pathName, Mode.LENGTH, AccessMode.MOTORCAR);
//			scenarioTest(mapName, pathName, Mode.TIME, AccessMode.FOOT);
//			scenarioTest(mapName, pathName, Mode.LENGTH, AccessMode.FOOT);
//			scenarioTest(mapName, pathName, Mode.TIME, AccessMode.BICYCLE);
//			scenarioTest(mapName, pathName, Mode.LENGTH, AccessMode.BICYCLE);
//		}
		
		mapName  = path + "\\maps\\" + "insa.mapgr";
		pathName = path + "\\paths_perso\\" + "path_fr31insa_rangueil_insa.path";
		scenarioTest(mapName, pathName, Mode.TIME, AccessMode.MOTORCAR);
		scenarioTest(mapName, pathName, Mode.LENGTH, AccessMode.MOTORCAR);
		scenarioTest(mapName, pathName, Mode.TIME, AccessMode.FOOT);
		scenarioTest(mapName, pathName, Mode.LENGTH, AccessMode.FOOT);
		scenarioTest(mapName, pathName, Mode.TIME, AccessMode.BICYCLE);
		scenarioTest(mapName, pathName, Mode.LENGTH, AccessMode.BICYCLE);
		
		pathName = path + "\\paths_perso\\" + "path_fr31insa_rangueil_r2.path";
		scenarioTest(mapName, pathName, Mode.TIME, AccessMode.MOTORCAR);
		scenarioTest(mapName, pathName, Mode.LENGTH, AccessMode.MOTORCAR);
		scenarioTest(mapName, pathName, Mode.TIME, AccessMode.FOOT);
		scenarioTest(mapName, pathName, Mode.LENGTH, AccessMode.FOOT);
		scenarioTest(mapName, pathName, Mode.TIME, AccessMode.BICYCLE);
		scenarioTest(mapName, pathName, Mode.LENGTH, AccessMode.BICYCLE);
		
		mapName  = path + "\\maps\\" + "midi-pyrenees.mapgr";
		for (int i = 7; i<21; i++) {
			pathName = path + "\\paths_perso\\" + files[i][0];
			scenarioTest(mapName, pathName, Mode.TIME, AccessMode.MOTORCAR);
			scenarioTest(mapName, pathName, Mode.LENGTH, AccessMode.MOTORCAR);
			scenarioTest(mapName, pathName, Mode.TIME, AccessMode.FOOT);
			scenarioTest(mapName, pathName, Mode.LENGTH, AccessMode.FOOT);
			scenarioTest(mapName, pathName, Mode.TIME, AccessMode.BICYCLE);
			scenarioTest(mapName, pathName, Mode.LENGTH, AccessMode.BICYCLE);
		}
		
//		for(String[] file : files) {
//	        String pathName = path + "\\paths_perso\\" + file[0];
//	        String mapName  = path + "\\maps\\" + file[1];
//	        System.out.println( "\n" + file[0] + " & " + file[1]);
//	        if (file[0].equals(files[13][0]) || file[0].equals(files[15][0])) {
//				scenarioTest(mapName, pathName, Mode.TIME, AccessMode.MOTORCAR);
//				scenarioTest(mapName, pathName, Mode.LENGTH, AccessMode.MOTORCAR);
//	        } else if (file[0].equals(files[14][0]) || file[0].equals(files[16][0])) {
//				scenarioTest(mapName, pathName, Mode.TIME, AccessMode.FOOT);
//				scenarioTest(mapName, pathName, Mode.LENGTH, AccessMode.FOOT);
//				scenarioTest(mapName, pathName, Mode.TIME, AccessMode.BICYCLE);
//				scenarioTest(mapName, pathName, Mode.LENGTH, AccessMode.BICYCLE);
//	        } else {
//				scenarioTest(mapName, pathName, Mode.TIME, AccessMode.MOTORCAR);
//				scenarioTest(mapName, pathName, Mode.LENGTH, AccessMode.MOTORCAR);
//				scenarioTest(mapName, pathName, Mode.TIME, AccessMode.FOOT);
//				scenarioTest(mapName, pathName, Mode.LENGTH, AccessMode.FOOT);
//				scenarioTest(mapName, pathName, Mode.TIME, AccessMode.BICYCLE);
//				scenarioTest(mapName, pathName, Mode.LENGTH, AccessMode.BICYCLE);
//	        }
//		}        

		
		
	}

}
