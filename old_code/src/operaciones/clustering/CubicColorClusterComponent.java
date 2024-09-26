/*
 * Copyright 2012 WillDom S.A. All Rights Reserved.
 * Proprietary and Confidential information of WillDom S.A.
 * Disclosure, use or reproduction without the written authorization of WillDom S.A. is prohibited.
 */
package operaciones.clustering;

/**
 * @author Roberto G. Fernandez
 * 
 */
public class CubicColorClusterComponent {
    private Long sum;
    private Long elements;

    public CubicColorClusterComponent() {
        sum = 0L;
        elements = 0L;
    }

    public void addElement(Integer value) {
        elements++;
        sum += value;
    }

    public Double getAverage() {
        if (elements > 0) {
            return sum.doubleValue() / elements.doubleValue();
        } else {
            return 0D;
        }
    }

    public Long getElements() {
        return elements;
    }

}
