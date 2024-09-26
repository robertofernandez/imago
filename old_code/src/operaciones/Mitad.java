/**
 * @author Roberto G. Fern&aacute;ndez
 * @version 0.01
 *
 * Nombre del archivo: Mitad.java
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
import util.Indexador;

public class Mitad implements OperadorUnarioDeImagen {

	public Mitad() {
		super();
	}

	/* (non-Javadoc)
	 * @see operaciones.OperadorUnarioDeImagen#operar(ui.DocumentoDeImagen)
	 */
	public DocumentoDeImagen operar(DocumentoDeImagen entrada) {
		DocumentoDeImagen salida = new DocumentoDeImagen(entrada.getImagen(),entrada.getTitle()+"(Reducido)",entrada.getPadre());
		salida.descomponer();
		
		int ancho=salida.getAncho();
		int alto=salida.getAlto();
		
		int altoNuevo=(int)(alto/2);
		int anchoNuevo=(int)(ancho/2);
	
		int[] rojo=this.operarColor(salida.getRed(),ancho,alto);
		int[] verde=this.operarColor(salida.getGreen(),ancho,alto);
		int[] azul=this.operarColor(salida.getBlue(),ancho,alto);
		int[] transparencia=this.operarColor(salida.getAlpha(),ancho,alto);

		
		salida.setRed(rojo);
		salida.setGreen(verde);
		salida.setBlue(azul);
		salida.setAlpha(transparencia);
		
		salida.setAncho(anchoNuevo);
		salida.setAlto(altoNuevo);
		
		salida.recomponer();
		salida.actualizarImagen();
		return salida;
	}

	/**
	 * Opera sobre un color particular y devuelve el color filtrado.
	 * 
	 * @param color Color a operar.
	 * @param ancho Ancho original de la imagen.
	 * @param alto Alto original de la imagen.
	 * @return Arreglo del color filtrado.
	 */
	private int[] operarColor(int[] color, int ancho, int alto)
	{
		int altoNuevo=(int)(alto/2);
		int anchoNuevo=(int)(ancho/2);
		int[] salida=new int[altoNuevo*anchoNuevo];
		
		Indexador indexadorOriginal = new Indexador(color,ancho,alto);
		Indexador indexadorNuevo = new Indexador(salida,anchoNuevo,altoNuevo);
		
		int i,j,k,l,valorNuevo;
		for(i=0,k=0;k<altoNuevo;k++,i+=2)
		{
			for(j=0,l=0;l<anchoNuevo;l++,j+=2)
			{
				valorNuevo=(indexadorOriginal.get(j,i)+indexadorOriginal.get(j+1,i)+
							indexadorOriginal.get(j,i+1)+indexadorOriginal.get(j+1,i+1))/4;
				indexadorNuevo.set(valorNuevo,l,k);
			}			
		}
		
		return salida;
	}

	/* (non-Javadoc)
	 * @see operaciones.OperadorUnarioDeImagen#getNombre()
	 */
	public String getNombre() {
		return "Reducir a la mitad";
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

}
