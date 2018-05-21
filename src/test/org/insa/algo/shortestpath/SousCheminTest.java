package org.insa.algo.shortestpath;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.time.LocalTime;
import java.util.List;

import org.insa.algo.ArcInspectorFactory;
import org.insa.algo.AbstractInputData.Mode;
import org.insa.graph.AccessRestrictions.AccessMode;
import org.insa.graph.Arc;
import org.insa.graph.Graph;
import org.insa.graph.Path;
import org.insa.graph.io.BinaryGraphReader;
import org.insa.graph.io.BinaryPathReader;
import org.insa.graph.io.GraphReader;
import org.insa.graph.io.PathReader;

public abstract class SousCheminTest {

	public SousCheminTest() {
	}

	protected abstract ShortestPathAlgorithm testedAlgo (ShortestPathData testdata);

	public void scenarioTest(String mapName, String pathName, String subpathName, Mode mode, AccessMode accessMode) throws Exception {

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
		ShortestPathData subtestdata = null;
		if (scenario.getType() == Mode.TIME) {
			switch (scenario.getAccessType()) {
			case MOTORCAR: {
				subtestdata = new ShortestPathData(graph, subpath.getOrigin(), subpath.getDestination(), ArcInspectorFactory.getAllFilters().get(3));
				break;
			}
			case FOOT: {
				subtestdata = new ShortestPathData(graph, subpath.getOrigin(), subpath.getDestination(), ArcInspectorFactory.getAllFilters().get(4));
				break;
			}
			case BICYCLE: {
				subtestdata = new ShortestPathData(graph, subpath.getOrigin(), subpath.getDestination(), ArcInspectorFactory.getAllFilters().get(6));
				break;
			}
			default:
				subtestdata = new ShortestPathData(graph, subpath.getOrigin(), subpath.getDestination(), ArcInspectorFactory.getAllFilters().get(2));
				break;
			}
		} else if (scenario.getType() == Mode.LENGTH) {
			switch (scenario.getAccessType()) {
			case MOTORCAR: {
				subtestdata = new ShortestPathData(graph, subpath.getOrigin(), subpath.getDestination(), ArcInspectorFactory.getAllFilters().get(1));
				break;
			}
			case FOOT: {
				subtestdata = new ShortestPathData(graph, subpath.getOrigin(), subpath.getDestination(), ArcInspectorFactory.getAllFilters().get(5));
				break;
			}
			case BICYCLE: {
				subtestdata = new ShortestPathData(graph, subpath.getOrigin(), subpath.getDestination(), ArcInspectorFactory.getAllFilters().get(7));
				break;
			}
			default:
				subtestdata = new ShortestPathData(graph, subpath.getOrigin(), subpath.getDestination(), ArcInspectorFactory.getAllFilters().get(0));
				break;
			}
		}

		//Create the algorithms
		ShortestPathAlgorithm testAlgo = testedAlgo(testdata);
		ShortestPathAlgorithm subAlgo = testedAlgo(subtestdata);

		//Create the solutions
		ShortestPathSolution solution = testAlgo.doRun();
		ShortestPathSolution subSolution = subAlgo.doRun();

		if (!solution.isFeasible() && !subSolution.isFeasible()) {
			System.out.println("test OK: no path found");
		} else if (solution.isFeasible() && !subSolution.isFeasible()) {
			System.out.println("path found but subpath not found ");
		} else if (!solution.isFeasible() && subSolution.isFeasible()) {
			System.out.println("no path found but subpath found");
		} else {
			//Get the shortest paths
			Path dPath = solution.getPath();
			Path sdPath = subSolution.getPath();

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
				//				throw new Exception("the origin of the sub path is not in the path");
				System.out.println("the origin of the sub path is not in the path");
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
					System.out.println("test OK: The sub path is a shortest path");
				} else {
					System.out.println("test NOT OK: The sub path is NOT the shortest path");
				}
				if (scenario.getType() == Mode.TIME) {
					System.out.println(scenario.getAccessType().toString() + " Time subpath in Path: " + LocalTime.MIN.plusSeconds((long)time).toString() + " Time Sub Path: " + LocalTime.MIN.plusSeconds((long)sdPath.getMinimumTravelTime()).toString() + "\n");
				} else if (scenario.getType() == Mode.LENGTH) {
					System.out.println(scenario.getAccessType().toString() + " Length subpath in Path: " + length + "m Length Sub Path: " + sdPath.getLength() + "m\n");
				}
			}
		}
	}

	public void testExecution() throws Exception {
		//		String path = "B:\\Users\\remi\\eclipse-workspace\\MAPS_BE_Graphes";
		String path = "C:\\Users\\linam\\Documents\\INSA\\3A\\2S\\BE_Graphes";

		String pathName = path + "\\paths_perso\\" + "path_frn_20484_185662.path";
		String subpathName = path + "\\paths_perso\\" + "subpath_frn_30798_74902.path";
		String mapName  = path + "\\maps\\" + "midi-pyrenees.mapgr";
		scenarioTest(mapName, pathName, subpathName, Mode.LENGTH, AccessMode.MOTORCAR);
		subpathName = path + "\\paths_perso\\" + "subpath_frn_30798_122919.path";
		scenarioTest(mapName, pathName, subpathName, Mode.LENGTH, AccessMode.FOOT);
		subpathName = path + "\\paths_perso\\" + "subpath_frn_30798_122929.path";
		scenarioTest(mapName, pathName, subpathName, Mode.LENGTH, AccessMode.BICYCLE);
		pathName = path + "\\paths_perso\\" + "timepath_frn_20484_185662.path";
		subpathName = path + "\\paths_perso\\" + "timesubpath_frn_30798_152367.path";
		scenarioTest(mapName, pathName, subpathName, Mode.TIME, AccessMode.MOTORCAR);
		subpathName = path + "\\paths_perso\\" + "subpath_frn_30798_300780.path";
		scenarioTest(mapName, pathName, subpathName, Mode.TIME, AccessMode.FOOT);
		subpathName = path + "\\paths_perso\\" + "subpath_frn_30798_302198.path";
		scenarioTest(mapName, pathName, subpathName, Mode.TIME, AccessMode.BICYCLE);


//		pathName = path + "\\paths_perso\\" + "path_fr31insa_52_139.path";
//		subpathName = path + "\\paths_perso\\" + "subpath_fr31insa_517_389.path";
//		mapName  = path + "\\maps\\" + "insa.mapgr";
//		scenarioTest(mapName, pathName, subpathName, Mode.TIME, AccessMode.MOTORCAR);
//		scenarioTest(mapName, pathName, subpathName, Mode.TIME, AccessMode.FOOT);
//		scenarioTest(mapName, pathName, subpathName, Mode.TIME, AccessMode.BICYCLE);
//		scenarioTest(mapName, pathName, subpathName, Mode.LENGTH, AccessMode.MOTORCAR);
//		scenarioTest(mapName, pathName, subpathName, Mode.LENGTH, AccessMode.FOOT);
//		scenarioTest(mapName, pathName, subpathName, Mode.LENGTH, AccessMode.BICYCLE);

		pathName = path + "\\paths_perso\\" + "path_frn_107733_414382.path";
		subpathName = path + "\\paths_perso\\" + "subpath_frn_88878_481884.path";
		mapName  = path + "\\maps\\" + "midi-pyrenees.mapgr";
		scenarioTest(mapName, pathName, subpathName, Mode.LENGTH, AccessMode.MOTORCAR);
		scenarioTest(mapName, pathName, subpathName, Mode.LENGTH, AccessMode.FOOT);
		scenarioTest(mapName, pathName, subpathName, Mode.LENGTH, AccessMode.BICYCLE);
		pathName = path + "\\paths_perso\\" + "path_frn_107733_414382.path";
		subpathName = path + "\\paths_perso\\" + "timesubpath_frn_252493_32738.path";
		scenarioTest(mapName, pathName, subpathName, Mode.TIME, AccessMode.MOTORCAR);
		subpathName = path + "\\paths_perso\\" + "subpath_frn_98408_322270.path";
		scenarioTest(mapName, pathName, subpathName, Mode.TIME, AccessMode.FOOT);
		scenarioTest(mapName, pathName, subpathName, Mode.TIME, AccessMode.BICYCLE);

		pathName = path + "\\paths_perso\\" + "path_frn_pedestrian_p1_349737_526241.path";
		subpathName = path + "\\paths_perso\\" + "subpath_frn_349736_526240.path";
		scenarioTest(mapName, pathName, subpathName, Mode.TIME, AccessMode.MOTORCAR);
		scenarioTest(mapName, pathName, subpathName, Mode.TIME, AccessMode.FOOT);
		scenarioTest(mapName, pathName, subpathName, Mode.TIME, AccessMode.BICYCLE);
		scenarioTest(mapName, pathName, subpathName, Mode.LENGTH, AccessMode.MOTORCAR);
		scenarioTest(mapName, pathName, subpathName, Mode.LENGTH, AccessMode.FOOT);
		scenarioTest(mapName, pathName, subpathName, Mode.LENGTH, AccessMode.BICYCLE);

		pathName = path + "\\paths_perso\\" + "path_frn_highway_p1_32224_288320.path";
		subpathName = path + "\\paths_perso\\" + "path_frn_highway_subp1_32225_59978.path";
		scenarioTest(mapName, pathName, subpathName, Mode.TIME, AccessMode.MOTORCAR);
		scenarioTest(mapName, pathName, subpathName, Mode.TIME, AccessMode.FOOT);
		scenarioTest(mapName, pathName, subpathName, Mode.TIME, AccessMode.BICYCLE);
		scenarioTest(mapName, pathName, subpathName, Mode.LENGTH, AccessMode.MOTORCAR);
		scenarioTest(mapName, pathName, subpathName, Mode.LENGTH, AccessMode.FOOT);
		scenarioTest(mapName, pathName, subpathName, Mode.LENGTH, AccessMode.BICYCLE);

	}


}
