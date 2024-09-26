package util;

public class SimpleColorDescription implements ColorDescription {

    private final int red;
    private final int green;
    private final int blue;
    private final int tred;
    private final int tgreen;
    private final int tblue;

    public SimpleColorDescription(int red, int green, int blue, int tred, int tgreen, int tblue) {
        super();
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.tred = tred;
        this.tgreen = tgreen;
        this.tblue = tblue;
    }

    public boolean isColor(int red, int green, int blue) {
        return Math.abs(this.red - red) < tred && Math.abs(this.green - green) < tgreen
                && Math.abs(this.blue - blue) < tblue;
    }

}
