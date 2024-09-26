/**
 * 
 */
package operaciones;

import java.util.Collections;
import java.util.Vector;

import ui.DocumentoDeImagen;
import util.Indexador;

/**
 * @author UTN user
 *
 */
public class MedianaIneficiente implements OperadorUnarioDeImagen {

	/**
	 * 
	 */
	public MedianaIneficiente() {
		super();
	}

	/* (non-Javadoc)
	 * @see operaciones.OperadorUnarioDeImagen#operar(ui.DocumentoDeImagen)
	 */
	public DocumentoDeImagen operar(DocumentoDeImagen entrada) {
		DocumentoDeImagen salida = new DocumentoDeImagen(entrada.getImagen(),entrada.getTitle()+"(Mediana)",entrada.getPadre());
		salida.descomponer();
		
		int ancho=salida.getAncho();
		int alto=salida.getAlto();
		
		int[] rojo=this.operarColor(salida.getRed(),ancho,alto);
		int[] verde=this.operarColor(salida.getGreen(),ancho,alto);
		int[] azul=this.operarColor(salida.getBlue(),ancho,alto);
		int[] transparencia=this.operarColor(salida.getAlpha(),ancho,alto);

		
		salida.setRed(rojo);
		salida.setGreen(verde);
		salida.setBlue(azul);
		salida.setAlpha(transparencia);
		
		salida.setAncho(ancho);
		salida.setAlto(alto);
		
		salida.recomponer();
		salida.actualizarImagen();
		return salida;
	}

	/* (non-Javadoc)
	 * @see operaciones.OperadorUnarioDeImagen#getNombre()
	 */
	public String getNombre() {
		return "Mediana I";
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
		int[] salida=new int[alto*ancho];
		
		Indexador indexadorOriginal = new Indexador(color,ancho,alto);
		Indexador indexadorNuevo = new Indexador(salida,ancho,alto);
		
		int i,j,k,l;
		
		for(i=0;i<alto;i++)
			for(j=0;j<ancho;j++)
				indexadorNuevo.set(indexadorOriginal.get(j,i),j,i);

		Vector<Integer> actual;
		for(i=1;i<alto-1;i++)
		{
			for(j=1;j<ancho-1;j++)
			{
				actual=new Vector<Integer>();
				for(k=j-1;k<=j+1;k++)
				{
					for(l=i-1;l<=i+1;l++)
					{
						actual.addElement(new Integer(indexadorOriginal.get(k,l)));
					}
				}
				Collections.sort(actual);
				indexadorNuevo.set(actual.elementAt(4),j,i);
			}			
		}
		
		return salida;
	}

}
