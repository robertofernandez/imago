package ar.com.sodhium.commons.math;

public class MathUtils {

    public static boolean inRange(Double value, Double min, Double max) {
        return value.doubleValue() >= min.doubleValue() && value.doubleValue() <= max.doubleValue();
    }
}
