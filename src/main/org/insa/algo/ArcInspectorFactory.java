package org.insa.algo;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import org.insa.algo.AbstractInputData.Mode;
import org.insa.graph.AccessRestrictions.AccessMode;
import org.insa.graph.AccessRestrictions.AccessRestriction;
import org.insa.graph.Arc;
import org.insa.graph.GraphStatistics;

public class ArcInspectorFactory {

    /**
     * @return List of all arc filters in this factory.
     */
    public static List<ArcInspector> getAllFilters() {
        List<ArcInspector> filters = new ArrayList<>();

        // Common filters:

        // 0: No filter (all arcs allowed):
        filters.add(new ArcInspector() {
            @Override
            public boolean isAllowed(Arc arc) {
                return true;
            }

            @Override
            public double getCost(Arc arc) {
                return arc.getLength();
            }

            @Override
            public int getMaximumSpeed() {
                return GraphStatistics.NO_MAXIMUM_SPEED;
            }

            @Override
            public Mode getMode() {
                return Mode.LENGTH;
            }

            @Override
            public String toString() {
                return "Shortest path, all roads allowed";
            }
        });

        // 1: Only road allowed for cars and length:
        filters.add(new ArcInspector() {
            @Override
            public boolean isAllowed(Arc arc) {
                return arc.getRoadInformation().getAccessRestrictions()
                        .isAllowedForAny(AccessMode.MOTORCAR, EnumSet.complementOf(EnumSet
                                .of(AccessRestriction.FORBIDDEN, AccessRestriction.PRIVATE)));
            }

            @Override
            public double getCost(Arc arc) {
                return arc.getLength();
            }

            @Override
            public int getMaximumSpeed() {
                return GraphStatistics.NO_MAXIMUM_SPEED;
            }

            @Override
            public Mode getMode() {
                return Mode.LENGTH;
            }

            @Override
            public String toString() {
                return "Shortest path, only roads open for cars";
            }
        });

        // 2: All roads allowed and time:
        filters.add(new ArcInspector() {
            @Override
            public boolean isAllowed(Arc arc) {
                return true;
            }

            @Override
            public double getCost(Arc arc) {
                return arc.getMinimumTravelTime();
            }

            @Override
            public int getMaximumSpeed() {
                return GraphStatistics.NO_MAXIMUM_SPEED;
            }

            @Override
            public Mode getMode() {
                return Mode.TIME;
            }

            @Override
            public String toString() {
                return "Fastest path, all roads allowed";
            }
        });
        
        // 3: Only road allowed for cars and time:
        filters.add(new ArcInspector() {
            @Override
            public boolean isAllowed(Arc arc) {
                return arc.getRoadInformation().getAccessRestrictions()
                        .isAllowedForAny(AccessMode.MOTORCAR, EnumSet.complementOf(EnumSet
                                .of(AccessRestriction.FORBIDDEN, AccessRestriction.PRIVATE)));
            }

            @Override
            public double getCost(Arc arc) {
                return arc.getMinimumTravelTime();
            }

            @Override
            public int getMaximumSpeed() {
                return GraphStatistics.NO_MAXIMUM_SPEED;
            }

            @Override
            public Mode getMode() {
                return Mode.TIME;
            }

            @Override
            public String toString() {
                return "Fastest path, only roads open for cars";
            }
        });

        // 4: Non-private roads for pedestrian and time:
        filters.add(new ArcInspector() {

            @Override
            public boolean isAllowed(Arc arc) {
                return arc.getRoadInformation().getAccessRestrictions()
                        .isAllowedForAny(AccessMode.FOOT, EnumSet.complementOf(EnumSet
                                .of(AccessRestriction.FORBIDDEN, AccessRestriction.PRIVATE)));
            }

            @Override
            public double getCost(Arc arc) {
                return arc.getTravelTime(
                        Math.min(getMaximumSpeed(), arc.getRoadInformation().getMaximumSpeed()));
            }

            @Override
            public String toString() {
                return "Fastest path for pedestrian";
            }

            @Override
            public int getMaximumSpeed() {
                return 5;
            }

            @Override
            public Mode getMode() {
                return Mode.TIME;
            }
        });
        
        // 5: Non-private roads for pedestrian and length:
        filters.add(new ArcInspector() {

            @Override
            public boolean isAllowed(Arc arc) {
                return arc.getRoadInformation().getAccessRestrictions()
                        .isAllowedForAny(AccessMode.FOOT, EnumSet.complementOf(EnumSet
                                .of(AccessRestriction.FORBIDDEN, AccessRestriction.PRIVATE)));
            }

            @Override
            public double getCost(Arc arc) {
                return arc.getTravelTime(
                        Math.min(getMaximumSpeed(), arc.getRoadInformation().getMaximumSpeed()));
            }

            @Override
            public String toString() {
                return "Shortest path for pedestrian";
            }

            @Override
            public int getMaximumSpeed() {
                return 5;
            }

            @Override
            public Mode getMode() {
                return Mode.LENGTH;
            }
        });
        
        // 6: Non-private roads for bicycle and time:
        filters.add(new ArcInspector() {

            @Override
            public boolean isAllowed(Arc arc) {
                return arc.getRoadInformation().getAccessRestrictions()
                        .isAllowedForAny(AccessMode.BICYCLE, EnumSet.complementOf(EnumSet
                                .of(AccessRestriction.FORBIDDEN, AccessRestriction.PRIVATE)));
            }

            @Override
            public double getCost(Arc arc) {
                return arc.getTravelTime(
                        Math.min(getMaximumSpeed(), arc.getRoadInformation().getMaximumSpeed()));
            }

            @Override
            public String toString() {
                return "Fastest path for bicycle";
            }

            @Override
            public int getMaximumSpeed() {
                return 5;
            }

            @Override
            public Mode getMode() {
                return Mode.TIME;
            }
        });
        
        // 7: Non-private roads for bicycle and length:
        filters.add(new ArcInspector() {

            @Override
            public boolean isAllowed(Arc arc) {
                return arc.getRoadInformation().getAccessRestrictions()
                        .isAllowedForAny(AccessMode.BICYCLE, EnumSet.complementOf(EnumSet
                                .of(AccessRestriction.FORBIDDEN, AccessRestriction.PRIVATE)));
            }

            @Override
            public double getCost(Arc arc) {
                return arc.getTravelTime(
                        Math.min(getMaximumSpeed(), arc.getRoadInformation().getMaximumSpeed()));
            }

            @Override
            public String toString() {
                return "Shortest path for bicycle";
            }

            @Override
            public int getMaximumSpeed() {
                return 5;
            }

            @Override
            public Mode getMode() {
                return Mode.LENGTH;
            }
        });

        return filters;
    }

}
