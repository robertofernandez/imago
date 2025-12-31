package ar.com.sodhium.commons.math;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DoubleCoordinates {
    @SerializedName("x")
    @Expose
    private Double x;

    @SerializedName("y")
    @Expose
    private Double y;

    @Override
    public int hashCode() {
        String asText = "<" + x + "_" + y + ">";
        return asText.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        return equalCoordinates((DoubleCoordinates) obj);
    }

    private boolean equalCoordinates(DoubleCoordinates coordinate) {
        try {
            return x.equals(coordinate.x) && y.equals(coordinate.y);
        } catch (Exception e) {
            return false;
        }

    }

    public DoubleCoordinates(Double x, Double y) {
        this.x = x;
        this.y = y;
    }

    public Double getX() {
        return x;
    }

    public Double getY() {
        return y;
    }

    @Override
    public String toString() {
        return "[x=" + x + ", y=" + y + "]";
    }

}
