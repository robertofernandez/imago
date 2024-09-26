/**
 * @author Roberto G. Fern&aacute;ndez
 * @version 0.01
 *
 * Nombre del archivo: OperadorNulo.java
 *
 * Historia de Creación/Modificación :
 *
 * Autor del cambio             Fecha             Descripción
 * -----------------------      ----------        ---------------------------------------
 * Roberto G. Fernández         24/08/2007        Creación
 *
 */

package operaciones;

import ui.DocumentoDeImagen;

public class OperadorNulo implements OperadorUnarioDeImagen {

	public OperadorNulo() {
		super();
	}

	/* (non-Javadoc)
	 * @see operaciones.OperadorUnarioDeImagen#operar(ui.DocumentoDeImagen)
	 */
	public DocumentoDeImagen operar(DocumentoDeImagen entrada) {
		DocumentoDeImagen salida = new DocumentoDeImagen(entrada.getImagen(),entrada.getTitle()+"(Copia)",entrada.getPadre());
		salida.descomponer();
		salida.recomponer();
		salida.actualizarImagen();
		return salida;
	}

	/* (non-Javadoc)
	 * @see operaciones.OperadorUnarioDeImagen#getNombre()
	 */
	public String getNombre() {
		return "Clonar";
	}
}
