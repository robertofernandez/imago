/**
 * 
 */
package operaciones;

import java.util.Vector;

import ui.DocumentoDeImagen;
import util.Indexador;

/**
 * @author UTN user
 * 
 */
public class SmoothThreshold implements OperadorUnarioDeImagen {
    private final Integer amount;
    private int cubicAmount;

    /**
	 * 
	 */
    public SmoothThreshold(Integer amount) {
        super();
        this.amount = amount;
        cubicAmount = amount * amount * amount;
    }

    /*
     * (non-Javadoc)
     * 
     * @see operaciones.OperadorUnarioDeImagen#operar(ui.DocumentoDeImagen)
     */
    public DocumentoDeImagen operar(DocumentoDeImagen entrada) {
        DocumentoDeImagen salida = new DocumentoDeImagen(entrada.getImagen(), entrada.getTitle() + "(ST)",
                entrada.getPadre());
        salida.descomponer();

        int ancho = salida.getAncho();
        int alto = salida.getAlto();

        Vector<int[]> colorOutpus = doFindThreshold(salida.getRed(), salida.getGreen(), salida.getBlue(), ancho, alto);
        int[] transparencia = salida.getAlpha();

        salida.setRed(colorOutpus.get(0));
        salida.setGreen(colorOutpus.get(1));
        salida.setBlue(colorOutpus.get(2));
        salida.setAlpha(transparencia);

        salida.setAncho(ancho);
        salida.setAlto(alto);

        salida.recomponer();
        salida.actualizarImagen();
        return salida;
    }

    /*
     * (non-Javadoc)
     * 
     * @see operaciones.OperadorUnarioDeImagen#getNombre()
     */
    public String getNombre() {
        return "Smooth Threshold " + amount;
    }

    private Vector<int[]> doFindThreshold(int[] red, int[] green, int[] blue, int ancho, int alto) {
        Vector<int[]> output = new Vector<int[]>();

        int[] newRed = new int[alto * ancho];
        int[] newGreen = new int[alto * ancho];
        int[] newBlue = new int[alto * ancho];

        output.add(newRed);
        output.add(newGreen);
        output.add(newBlue);

        Indexador redIndex = new Indexador(red, ancho, alto);
        Indexador newRedIndex = new Indexador(newRed, ancho, alto);

        Indexador greenIndex = new Indexador(green, ancho, alto);
        Indexador newGreenIndex = new Indexador(newGreen, ancho, alto);

        Indexador blueIndex = new Indexador(blue, ancho, alto);
        Indexador newBlueIndex = new Indexador(newBlue, ancho, alto);

        int i, j;

        for (i = 0; i < alto; i++)
            for (j = 0; j < ancho; j++) {
                int cred = redIndex.get(j, i);
                int cgreen = greenIndex.get(j, i);
                int cblue = blueIndex.get(j, i);
                int value = 0;
                if (cred * cgreen * cblue >= cubicAmount && connected(j, i, redIndex, greenIndex, blueIndex)) {
                    value = 255;
                }
                newRedIndex.set(value, j, i);
                newGreenIndex.set(value, j, i);
                newBlueIndex.set(value, j, i);
            }

        return output;
    }

    private boolean connected(int x, int y, Indexador redIndex, Indexador greenIndex, Indexador blueIndex) {
        for (int i = x - 1; i < x + 1; i++) {
            for (int j = y - 1; j < y + 1; j++) {
                if (i == x && j == y) {
                    continue;
                } else {
                    int cred = redIndex.get(i, j);
                    int cgreen = greenIndex.get(i, j);
                    int cblue = blueIndex.get(i, j);
                    if (cred * cgreen * cblue >= cubicAmount) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
