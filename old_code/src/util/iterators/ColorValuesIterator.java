/**
 * 
 */
package util.iterators;

import java.util.Iterator;

/**
 * @author UTN user
 *
 */
public class ColorValuesIterator implements Iterator {

	private int[] colorValues;
	int index;
	
	/**
	 * 
	 */
	public ColorValuesIterator(int[] colorValues) {
		super();
		index = 0;
		this.colorValues = colorValues;
	}

	/* (non-Javadoc)
	 * @see java.util.Iterator#hasNext()
	 */
	public boolean hasNext() {
		return index < colorValues.length;
	}

	/* (non-Javadoc)
	 * @see java.util.Iterator#next()
	 */
	public Object next() {
		return new Integer(colorValues[index++]);
	}

	/* (non-Javadoc)
	 * @see java.util.Iterator#remove()
	 */
	public void remove() {
		// TODO Auto-generated method stub

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
