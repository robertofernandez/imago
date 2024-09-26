/*
 * Copyright 2012 WillDom S.A. All Rights Reserved.
 * Proprietary and Confidential information of WillDom S.A.
 * Disclosure, use or reproduction without the written authorization of WillDom S.A. is prohibited.
 */
package operaciones.clustering;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import util.DistanceColorDescription;

/**
 * @author Roberto G. Fernandez
 * 
 */
public class CubicClusterSet {
    private HashMap<Integer, CubicColorCluster> clustersMap;
    private ArrayList<CubicColorCluster> clustersList;
    private final int tolerance;
    private ArrayList<CubicColorCluster> activeClusters;
    private ArrayList<Color> basicColors;

    public CubicClusterSet(int tolerance) {
        this.tolerance = tolerance;
        basicColors = new ArrayList<Color>();
        clustersMap = new HashMap<Integer, CubicColorCluster>();
        clustersList = new ArrayList<CubicColorCluster>();
        for (Integer red = 0; red < 10; red++) {
            for (Integer green = 0; green < 10; green++) {
                for (Integer blue = 0; blue < 10; blue++) {
                    CubicColorCluster cubicColorCluster = new CubicColorCluster();
                    clustersMap.put(getMapIndex(red, green, blue), cubicColorCluster);
                    clustersList.add(cubicColorCluster);
                }
            }
        }
    }

    private int getMapIndex(Integer red, Integer green, Integer blue) {
        return red * 100 + green * 10 + blue;
    }

    /**
     * Converts a number from 0..255 to 0..9
     * 
     * @param value
     * @return
     */
    private int getDecimalIndex(Integer value) {
        return (int) (value.doubleValue() / 25.6D);
    }

    public void addElement(Integer red, Integer green, Integer blue) {
        CubicColorCluster cluster = getCluster(red, green, blue);
        cluster.addElement(red, green, blue);
    }

    public CubicColorCluster getCluster(Integer red, Integer green, Integer blue) {
        return clustersMap.get(getMapIndex(getDecimalIndex(red), getDecimalIndex(green), getDecimalIndex(blue)));
    }

    public ArrayList<CubicColorCluster> getClustersList() {
        return clustersList;
    }

    public void sortElements() {
        Collections.sort(clustersList, Collections.reverseOrder());
    }

    public ArrayList<CubicColorCluster> setActiveClusters() {
        activeClusters = new ArrayList<CubicColorCluster>();
        sortElements();
        for (int i = 0, j = 0; i < clustersList.size() && j < 16; i++) {
            CubicColorCluster cubicColorCluster = clustersList.get(i);
            linkToNearestCluster(cubicColorCluster, activeClusters);
            if (cubicColorCluster.getNearestActiveCluster() != null) {
                DistanceColorDescription distanceDescription = getDistanceDescription(cubicColorCluster);
                if (distanceDescription.colourDistance(CubicClusterSet.geColor(cubicColorCluster
                        .getNearestActiveCluster())) > tolerance) {
                    cubicColorCluster.setActive(true);
                    j++;
                    activeClusters.add(cubicColorCluster);
                    System.out.println("Setting active cluster <" + cubicColorCluster.getRedMedia() + ", "
                            + cubicColorCluster.getGreenMedia() + ", " + cubicColorCluster.getBlueMedia() + "> with "
                            + cubicColorCluster.getElements() + "elements");
                } else {
                    System.out.print(".");
                }
            } else {
                cubicColorCluster.setActive(true);
                j++;
                activeClusters.add(cubicColorCluster);
                System.out.println("Setting active cluster <" + cubicColorCluster.getRedMedia() + ", "
                        + cubicColorCluster.getGreenMedia() + ", " + cubicColorCluster.getBlueMedia() + "> with "
                        + cubicColorCluster.getElements() + "elements");
            }

        }
        return activeClusters;
    }

    public void linkToNearestCluster(ArrayList<CubicColorCluster> activeClusters) {
        for (CubicColorCluster cubicColorCluster : clustersList) {
            if (!cubicColorCluster.isActive()) {
                linkToNearestCluster(cubicColorCluster, activeClusters);
            }
        }
    }

    private void linkToNearestCluster(CubicColorCluster cubicColorCluster, ArrayList<CubicColorCluster> activeClusters) {
        CubicColorCluster nearest = null;
        double distance = 99999999999D;
        DistanceColorDescription description = getDistanceDescription(cubicColorCluster);
        for (CubicColorCluster activeCubicColorCluster : activeClusters) {
            double currentDistance = description.colourDistance(geColor(activeCubicColorCluster));
            if (currentDistance < distance) {
                currentDistance = distance;
                nearest = activeCubicColorCluster;
            }
        }
        cubicColorCluster.setNearestActiveCluster(nearest);
    }

    public CubicColorCluster getNearestActiveCluster(Integer red, Integer green, Integer blue) {
        CubicColorCluster nearest = null;
        double distance = 99999999999D;
        DistanceColorDescription description = getDistanceDescriptionFromColor(red, green, blue);
        for (CubicColorCluster activeCubicColorCluster : activeClusters) {
            double currentDistance = description.colourDistance(geColor(activeCubicColorCluster));
            if (currentDistance < distance) {
                currentDistance = distance;
                nearest = activeCubicColorCluster;
            }
        }
        return nearest;
    }

    private static Color geColor(CubicColorCluster cluster) {
        return new Color(cluster.getRedMedia().intValue(), cluster.getGreenMedia().intValue(), cluster.getBlueMedia()
                .intValue());
    }

    private static DistanceColorDescription getDistanceDescription(CubicColorCluster cluster) {
        return new DistanceColorDescription(cluster.getRedMedia().intValue(), cluster.getGreenMedia().intValue(),
                cluster.getBlueMedia().intValue(), 50D);
    }

    private static DistanceColorDescription getDistanceDescriptionFromColor(Integer red, Integer green, Integer blue) {
        return new DistanceColorDescription(red, green, blue, 50D);
    }

    public void assigBasicColorsToActiveClusters() {
        for (int i = 0; i < basicColors.size(); i++) {
            activeClusters.get(i).setAssignedColor(basicColors.get(i));
        }
    }

    public void addBasicColor(Integer red, Integer green, Integer blue) {
        basicColors.add(new Color(red, green, blue));
    }
}
