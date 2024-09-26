/**
 * 
 */
package util;

/**
 * Utilidada para indexar en dos dimensiones una matriz representada por un
 * arreglo.
 * 
 * @author UTN user
 * 
 */
public class Indexador {

    private int[] arregloIndexado;
    int ancho;
    int alto;

    /**
	 * 
	 */
    public Indexador(int[] arregloIndexado, int ancho, int alto) {
        super();
        this.arregloIndexado = arregloIndexado;
        this.alto = alto;
        this.ancho = ancho;
    }

    public int get(int x, int y) {
        int index = y * ancho + x;
        if (index > arregloIndexado.length - 1 || index < 0) {
            System.out.println((new Integer(x)).toString() + ", " + (new Integer(y)).toString());
            System.out.println((new Integer(ancho)).toString() + ", " + (new Integer(alto)).toString());
            return 0;
        }
        return (arregloIndexado[index]);
    }

    public void set(int valor, int x, int y) {
        int index = y * ancho + x;
        if (index > arregloIndexado.length - 1 || index < 0) {
            System.out.println((new Integer(x)).toString() + ", " + (new Integer(y)).toString());
            System.out.println((new Integer(ancho)).toString() + ", " + (new Integer(alto)).toString());
            return;
        }
        arregloIndexado[index] = valor;
    }

}
