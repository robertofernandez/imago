package ar.com.sodhium.commons.collections;

public interface Priorizable {
    boolean hasLowerPriorityThan(Priorizable element);
}
