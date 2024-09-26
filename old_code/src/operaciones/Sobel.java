/**
 * 
 */
package operaciones;

import ui.DocumentoDeImagen;
import util.ImageCalculations;

/**
 * @author UTN user
 * 
 */
public class Sobel implements OperadorUnarioDeImagen {

    /**
	 * 
	 */
    public Sobel() {
        super();
    }

    /*
     * (non-Javadoc)
     * 
     * @see operaciones.OperadorUnarioDeImagen#operar(ui.DocumentoDeImagen)
     */
    public DocumentoDeImagen operar(DocumentoDeImagen entrada) {
        DocumentoDeImagen salida = new DocumentoDeImagen(entrada.getImagen(), entrada.getTitle() + "(Sobel)",
                entrada.getPadre());
        salida.descomponer();

        int ancho = salida.getAncho();
        int alto = salida.getAlto();

        int[] rojo = ImageCalculations.apply_sobel(salida.getRed(), ancho, alto, 1, 0);
        int[] transparencia = salida.getAlpha();

        salida.setRed(rojo);
        salida.setGreen(rojo);
        salida.setBlue(rojo);
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
        return "Sobel";
    }

}
