package ar.com.sodhium.commons.collections;

import java.util.ArrayList;

public class Heap<E extends Priorizable> {
    private ArrayList<E> elements;

    public Heap() {
        elements = new ArrayList<>();
    }

    public void addElement(E element) {
        elements.add(element);
        restoreQueueFromEnd();
    }

    public int size() {
        return elements.size();
    }

    public E pop() {
        E returnElement = elements.get(0);
        if (elements.size() == 1) {
            elements.remove(0);
            return returnElement;
        }
        elements.set(0, elements.get(elements.size() - 1));
        elements.remove(elements.size() - 1);
        restoreQueueFromStart();
        return returnElement;
    }

    public E peek() {
        return elements.get(0);
    }

    public boolean isEmpty() {
        return elements.isEmpty();
    }

    private void restoreQueueFromEnd() {
        for (int currentIndex = elements.size() - 1; currentIndex > 0;) {
            int parentIndex = (int) (Math.floor((currentIndex + 1) / 2 - 1));
            E currenElement = elements.get(currentIndex);
            E parentElement = elements.get(parentIndex);
            if (currenElement.hasLowerPriorityThan(parentElement)) {
                return;
            }
            swapIndexes(currentIndex, parentIndex);
            currentIndex = parentIndex;
        }
    }

    private void restoreQueueFromStart() {
        for (int currentIndex = 0; currentIndex < elements.size();) {
            int leftSonIndex = (currentIndex + 1) * 2 - 1;
            int rightSonIndex = (currentIndex + 1) * 2;
            E currenElement = elements.get(currentIndex);
            E leftSon = null;
            E rightSon = null;
            if (leftSonIndex < elements.size()) {
                leftSon = elements.get(leftSonIndex);
            }
            if (rightSonIndex < elements.size()) {
                rightSon = elements.get(rightSonIndex);
            }
            E swappingSon;
            int swappingIndex;
            if (leftSon == null) {
                return;
            }
            if (rightSon == null || rightSon.hasLowerPriorityThan(leftSon)) {
                swappingIndex = leftSonIndex;
                swappingSon = leftSon;
            } else {
                swappingIndex = rightSonIndex;
                swappingSon = rightSon;

            }
            if (swappingSon.hasLowerPriorityThan(currenElement)) {
                return;
            }
            swapIndexes(currentIndex, swappingIndex);
            currentIndex = swappingIndex;
        }
    }

    private void swapIndexes(int firstIndex, int secondIndex) {
        E firstElement = elements.get(firstIndex);
        elements.set(firstIndex, elements.get(secondIndex));
        elements.set(secondIndex, firstElement);
    }

    public void cap(int size) {
        ArrayList<E> newElements = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            if (isEmpty()) {
                break;
            }
            newElements.add(pop());
        }
        elements = newElements;
    }
}
