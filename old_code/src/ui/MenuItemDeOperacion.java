/**
 * @author Roberto G. Fern&aacute;ndez
 * @version 0.01
 *
 * Nombre del archivo: MenuItemDeOperacion.java
 *
 * Historia de Creaci�n/Modificaci�n :
 *
 * Autor del cambio             Fecha             Descripci�n
 * -----------------------      ----------        ---------------------------------------
 * Roberto G. Fern�ndez         24/08/2007        Creaci�n
 *
 */

package ui;

import javax.swing.JMenuItem;

import operaciones.OperadorUnarioDeImagen;

/**
 * Los items de menu de operaci�n son items que se agregan a un JMenu, y que contienen
 * un operador que puede ser accedido desde el exterior de la clase, para usarse
 * cuando se registre un evento sobre el item.
 * 
 * @author UTN user
 *
 */
public class MenuItemDeOperacion extends JMenuItem {
	private static final long serialVersionUID = -8901174360309851346L;
	private OperadorUnarioDeImagen operador;

	public MenuItemDeOperacion(OperadorUnarioDeImagen operador) {
		super();
		this.operador=operador;
		this.setText(operador.getNombre());
	}

/**
 * 
 * @param entrada Imagen a operar.
 * @return Imagen resultado de operar la imagen de entrada. 
 */	
	public DocumentoDeImagen operar(DocumentoDeImagen entrada){
	    long t1 = System.currentTimeMillis();
	    DocumentoDeImagen salida =this.operador.operar(entrada);
	    long t2 = System.currentTimeMillis();
	    System.out.println("Tiempo de operaci�n en milisegundos: "+(new Long(t2-t1)).toString());
		return salida;
	}
}
