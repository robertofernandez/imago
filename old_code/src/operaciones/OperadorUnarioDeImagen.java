/**
 * @author Roberto G. Fern&aacute;ndez
 * @version 0.01
 *
 * Nombre del archivo: OperadorUnarioDeImagen.java
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

/**
 * Representa el comportamiento de un operador que recibe
 * un documento de imagen, y lo opera para devolver otro. 
 * @author UTN user
 *
 */
public interface OperadorUnarioDeImagen {
	public DocumentoDeImagen operar(DocumentoDeImagen entrada);
	public String getNombre();
}
