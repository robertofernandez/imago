/**
 * Archivo: Taller1.java
 * Fecha de creaci&oacute;n: 18/09/2008
 */
package operaciones;

import ui.DocumentoDeImagen;
import util.iterators.ColorValuesCollection;

/**
 * @author Roberto G. Fern&aacute;ndez@Sophia I+D
 * 
 */
public class Taller1 implements OperadorUnarioDeImagen {

	public String getNombre() {
		return "Operacion del taller 1";
	}

	public DocumentoDeImagen operar(DocumentoDeImagen entrada) {
		DocumentoDeImagen salida = new DocumentoDeImagen(entrada.getImagen(),
				entrada.getTitle() + "(Operado)", entrada.getPadre());

		ColorValuesCollection red = entrada.getRedCollection();
		ColorValuesCollection green = entrada.getGreenCollection();
		ColorValuesCollection blue = entrada.getBlueCollection();

		// FIXME: implementar metodo
		return salida;
	}

}
