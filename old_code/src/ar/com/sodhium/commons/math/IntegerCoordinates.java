package ar.com.sodhium.commons.math;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IntegerCoordinates {
    @SerializedName("x")
    @Expose
    private Integer x;

    @SerializedName("y")
    @Expose
    private Integer y;

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
        return equalCoordinates((IntegerCoordinates) obj);
    }

    private boolean equalCoordinates(IntegerCoordinates coordinate) {
        try {
            return x.equals(coordinate.x) && y.equals(coordinate.y);
        } catch (Exception e) {
            return false;
        }

    }

    public IntegerCoordinates(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    @Override
    public String toString() {
        return "[x=" + x + ", y=" + y + "]";
    }

}
