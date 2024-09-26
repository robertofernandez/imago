package util;

import java.awt.Color;

public class DistanceColorDescription implements ColorDescription {

    int red;
    int green;
    int blue;
    Color myColor;
    double maxDistance;

    public DistanceColorDescription(int red, int green, int blue, double maxDistance) {
        super();
        this.red = red;
        this.green = green;
        this.blue = blue;
        myColor = new Color(red, green, blue);
        this.maxDistance = maxDistance;
    }

    public boolean isColor(int red, int green, int blue) {
        Color newColor = new Color(red, green, blue);
        return colourDistance(myColor, newColor) < maxDistance;
    }

    public double colourDistance(Color color) {
        return colourDistance(myColor, color);
    }

    public double colourDistance(Color c1, Color c2) {
        double rmean = (c1.getRed() + c2.getRed()) / 2;
        int r = c1.getRed() - c2.getRed();
        int g = c1.getGreen() - c2.getGreen();
        int b = c1.getBlue() - c2.getBlue();
        double weightR = 2 + rmean / 256;
        double weightG = 4.0;
        double weightB = 2 + (255 - rmean) / 256;
        return Math.sqrt(weightR * r * r + weightG * g * g + weightB * b * b);
    }
}
