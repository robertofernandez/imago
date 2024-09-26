package util;

import java.awt.Color;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DistanceColorDescriptionTests {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testIsColor() {
    }

    @Test
    public void testColourDistance() {
        DistanceColorDescription distanceColorDescription = new DistanceColorDescription(0, 0, 0, 200);

        Color black = new Color(0, 0, 0);
        for (int i = 0; i < 60; i++) {
            double colourDistance = distanceColorDescription.colourDistance(black, new Color(i, i, i));
            System.out.println("Black, distance with " + i + " = " + colourDistance);
        }

    }
}
