package ar.com.sodhium.commons.indexes;

/**
 * Utility to index in two dimensions a matrix represented by an array.
 * 
 * @author Roberto G. Fernandez
 * 
 */
public class Indexer {
    protected int[] indexedArray;
    protected int width;
    protected int height;

    public Indexer(int[] input, int width, int height) throws Exception {
        super();
        this.indexedArray = input;
        this.height = height;
        this.width = width;
        if (indexedArray.length < width * height) {
            throw new Exception("" + indexedArray.length + " < " + width + " * " + height);
        }
    }

    public Indexer(Indexer baseIndexer) throws Exception {
        super();
        this.indexedArray = baseIndexer.indexedArray;
        this.height = baseIndexer.height;
        this.width = baseIndexer.width;
        if (indexedArray.length < width * height) {
            throw new Exception("" + indexedArray.length + " < " + width + " * " + height);
        }
    }

    public int get(int x, int y) {
        int index = y * width + x;
        if (index > indexedArray.length - 1 || index < 0) {
            printError(x, y, index);
            return 0;
        }
        return (indexedArray[index]);
    }

    private void printError(int x, int y, int index) {
        String xAndY = "" + x + ", " + y;
        String widthAndHeight = "" + width + ", " + height;
        System.out.println("w, h -> (" + widthAndHeight + "; x,y -> (" + xAndY + "); index = " + index + "; limit = "
                + (indexedArray.length - 1));
    }

    public int getMaxSurounding(int x, int y) {
        int max = get(x, y);
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (i > 0 && i < width && j > 0 && j < height) {
                    int value = get(i, j);
                    if (value > max) {
                        max = value;
                    }
                }
            }
        }
        return max;
    }

    public void set(int value, int x, int y) {
        int index = y * width + x;
        if (index > indexedArray.length - 1 || index < 0) {
            printError(x, y, index);
            return;
        }
        indexedArray[index] = value;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
