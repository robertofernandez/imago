package operaciones;

import java.util.Collections;
import java.util.Vector;

import ui.DocumentoDeImagen;
import util.ContadorDeValores;
import util.Indexador;

/**
 * @author UTN user
 *
 */
public class SuavizadoSelNegI implements OperadorUnarioDeImagen {

	public SuavizadoSelNegI() {
		super();
	}

	/* (non-Javadoc)
	 * @see operaciones.OperadorUnarioDeImagen#operar(ui.DocumentoDeImagen)
	 */
	public DocumentoDeImagen operar(DocumentoDeImagen entrada) {
		DocumentoDeImagen salida = new DocumentoDeImagen(entrada.getImagen(),entrada.getTitle()+"(Suavizado)",entrada.getPadre());
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
		return "Suavizado Selectivo (-) I";
	}
	
	/**
	 * Opera sobre un color particular y devuelve el color filtrado.
	 * 
	 * @param color Color a operar.
	 * @param ancho Ancho original de la imagen.
	 * @param alto Alto original de la imagen.
	 * @return Arreglo del color filtrado.
	 */
	@SuppressWarnings("unchecked")
	private int[] operarColor(int[] color, int ancho, int alto)
	{
		int[] salida=new int[alto*ancho];
		
		Indexador indexadorOriginal = new Indexador(color,ancho,alto);
		Indexador indexadorNuevo = new Indexador(salida,ancho,alto);
		
		int i,j,k,l, colorCentral;
		
		for(i=0;i<alto;i++)
			for(j=0;j<ancho;j++)
				indexadorNuevo.set(indexadorOriginal.get(j,i),j,i);

		Vector<ContadorDeValores> actual;
		for(i=1;i<alto-1;i++)
		{
			for(j=1;j<ancho-1;j++)
			{
				colorCentral=indexadorOriginal.get(j,i);
				int colorTotal=0;
				actual=new Vector<ContadorDeValores>();
				for(k=j-1;k<=j+1;k++)
				{
					for(l=i-1;l<=i+1;l++)
					{
						ContadorDeValores nuevo=new ContadorDeValores(indexadorOriginal.get(k,l),colorCentral,true);
						int n=actual.indexOf(nuevo);
						if(n>=0)
							actual.elementAt(n).incrementarApariciones();
						else
							actual.add(nuevo);
						colorTotal+=nuevo.getValor();
					}
				}
				Collections.sort(actual);
				colorTotal-=actual.firstElement().getValor();
				indexadorNuevo.set(colorTotal/8,j,i);
			}			
		}
		
		return salida;
	}

}
