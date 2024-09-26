/*
 * Copyright 2012 WillDom S.A. All Rights Reserved.
 * Proprietary and Confidential information of WillDom S.A.
 * Disclosure, use or reproduction without the written authorization of WillDom S.A. is prohibited.
 */
package operaciones.clustering;

import java.awt.Color;

/**
 * @author Roberto G. Fernandez
 * 
 */
public class CubicColorCluster implements Comparable<CubicColorCluster> {
    private CubicColorCluster nearestActiveCluster;
    private CubicColorClusterComponent redComponent;
    private CubicColorClusterComponent greenComponent;
    private CubicColorClusterComponent blueComponent;
    private boolean active;
    private Color assignedColor;

    public CubicColorCluster() {
        redComponent = new CubicColorClusterComponent();
        greenComponent = new CubicColorClusterComponent();
        blueComponent = new CubicColorClusterComponent();
        active = false;
        nearestActiveCluster = null;
    }

    public void addElement(Integer red, Integer green, Integer blue) {
        redComponent.addElement(red);
        greenComponent.addElement(green);
        blueComponent.addElement(blue);
    }

    public Long getElements() {
        return redComponent.getElements();
    }

    public Double getRedMedia() {
        return redComponent.getAverage();
    }

    public Double getGreenMedia() {
        return greenComponent.getAverage();
    }

    public Double getBlueMedia() {
        return blueComponent.getAverage();
    }

    public int compareTo(CubicColorCluster anotherCluster) {
        return getElements().compareTo(anotherCluster.getElements());
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public CubicColorCluster getNearestActiveCluster() {
        return nearestActiveCluster;
    }

    public void setNearestActiveCluster(CubicColorCluster nearestActiveCluster) {
        this.nearestActiveCluster = nearestActiveCluster;
    }

    public Color getAssignedColor() {
        return assignedColor;
    }

    public void setAssignedColor(Color assignedColor) {
        this.assignedColor = assignedColor;
    }
}
