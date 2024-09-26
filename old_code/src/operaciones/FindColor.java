/**
 * 
 */
package operaciones;

import java.util.Vector;

import ui.DocumentoDeImagen;
import util.ColorDescription;
import util.Indexador;

/**
 * @author UTN user
 * 
 */
public class FindColor implements OperadorUnarioDeImagen {

    private ColorDescription colorDescription;
    private final String name;
    public static final int SUBSTRACT_MODE = 0;
    public static final int GREEN_CONTRAST_MODE = 1;
    private final int mode;

    /**
	 * 
	 */
    public FindColor(ColorDescription colorDescription, String name, int mode) {
        super();
        this.colorDescription = colorDescription;
        this.name = name;
        this.mode = mode;
    }

    /*
     * (non-Javadoc)
     * 
     * @see operaciones.OperadorUnarioDeImagen#operar(ui.DocumentoDeImagen)
     */
    public DocumentoDeImagen operar(DocumentoDeImagen entrada) {
        DocumentoDeImagen salida = new DocumentoDeImagen(entrada.getImagen(), entrada.getTitle() + "(color "
                + getNombre() + ")", entrada.getPadre());
        salida.descomponer();

        int ancho = salida.getAncho();
        int alto = salida.getAlto();

        Vector<int[]> colorOutpus = doFindColor(salida.getRed(), salida.getGreen(), salida.getBlue(), ancho, alto);
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
        return name;
    }

    private Vector<int[]> doFindColor(int[] red, int[] green, int[] blue, int ancho, int alto) {
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

        // ColorDescription colorDescription = new SimpleColorDescription(240,
        // 170, 170, 55, 150, 130);

        // colorDescription = new DistanceColorDescription(120,240, 120, 200);

        for (i = 0; i < alto; i++)
            for (j = 0; j < ancho; j++) {
                int cred = redIndex.get(j, i);
                int cgreen = greenIndex.get(j, i);
                int cblue = blueIndex.get(j, i);
                switch (mode) {
                case GREEN_CONTRAST_MODE:
                    if (colorDescription.isColor(cred, cgreen, cblue)) {
                        newRedIndex.set(200, j, i);
                        newGreenIndex.set(0, j, i);
                        newBlueIndex.set(0, j, i);
                    } else {
                        newRedIndex.set((int) (cred / 3), j, i);
                        newGreenIndex.set((int) (cgreen / 3), j, i);
                        newBlueIndex.set((int) (cblue / 3), j, i);
                    }
                    break;
                case SUBSTRACT_MODE:
                    if (colorDescription.isColor(cred, cgreen, cblue)) {
                        newRedIndex.set(cred, j, i);
                        newGreenIndex.set(cgreen, j, i);
                        newBlueIndex.set(cblue, j, i);
                    } else {
                        newRedIndex.set(0, j, i);
                        newGreenIndex.set(0, j, i);
                        newBlueIndex.set(0, j, i);
                    }
                    break;
                default:
                    break;
                }
            }

        return output;
    }

}
