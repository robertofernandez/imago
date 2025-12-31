/**
 * 
 */
package operaciones;

import java.util.Collection;
import java.util.HashMap;

import ar.com.sodhium.commons.img.colors.RgbColor;
import ui.DocumentoDeImagen;
import util.Indexador;

/**
 * @author UTN user
 * 
 */
public class TilesGameSourcesMapper implements OperadorUnarioDeImagen {

	public HashMap<String, String> colorCodeToItem = new HashMap<String, String>();
	public HashMap<String, RgbColor> colorCodeToColor = new HashMap<String, RgbColor>();

	/**
	 * 
	 */
	public TilesGameSourcesMapper() {
		super();
		// azulAire
		createColor(33, 107, 173, "A");
		// verdeSuelo
		createColor(24, 41, 16, "G");
		//255, 243, 0
	}

	public void createColor(int r, int g, int b, String letter) {
		RgbColor azulAire = new RgbColor(r, g, b);
		colorCodeToItem.put(azulAire.toString(), letter);
		colorCodeToColor.put(azulAire.toString(), azulAire);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see operaciones.OperadorUnarioDeImagen#operar(ui.DocumentoDeImagen)
	 */
	public DocumentoDeImagen operar(DocumentoDeImagen entrada) {
		DocumentoDeImagen salida = new DocumentoDeImagen(entrada.getImagen(), entrada.getTitle() + "(Clustered)",
				entrada.getPadre());
		salida.descomponer();

		int ancho = salida.getAncho();
		int alto = salida.getAlto();

		int[] red = salida.getRed();
		int[] green = salida.getGreen();
		int[] blue = salida.getBlue();

		Indexador redIndex = new Indexador(red, ancho, alto);
		Indexador greenIndex = new Indexador(green, ancho, alto);
		Indexador blueIndex = new Indexador(blue, ancho, alto);

		int[] rojo = new int[alto * ancho];
		int[] verde = new int[alto * ancho];
		int[] azul = new int[alto * ancho];
		int[] transparencia = salida.getAlpha();

		Indexador newRedIndex = new Indexador(rojo, ancho, alto);
		Indexador newGreenIndex = new Indexador(verde, ancho, alto);
		Indexador newBlueIndex = new Indexador(azul, ancho, alto);

		for (int i = 0; i < alto; i++) {
			for (int j = 0; j < ancho; j++) {
				int redValue = redIndex.get(i, j);
				int greenValue = greenIndex.get(i, j);
				int blueValue = blueIndex.get(i, j);

				RgbColor color = new RgbColor(redValue, greenValue, blueValue);
				RgbColor bestMatchingColor = getBestMatchingColor(color, colorCodeToColor.values());
				System.out.print(colorCodeToItem.get(bestMatchingColor.toString()));
				
				bestMatchingColor = color;

				newRedIndex.set(bestMatchingColor.getRed(), i, j);
				newGreenIndex.set(bestMatchingColor.getGreen(), i, j);
				newBlueIndex.set(bestMatchingColor.getBlue(), i, j);
			}
			System.out.println("");
		}

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

	private RgbColor getBestMatchingColor(RgbColor color, Collection<RgbColor> values) {
		RgbColor output = null;
		double minDistance = 0;

		for (RgbColor toCompare : values) {
			double currentDistance = toCompare.getDistance(color);
			if (output == null || toCompare.getDistance(color) < minDistance) {
				minDistance = currentDistance;
				output = toCompare;
			}
		}

		return output;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see operaciones.OperadorUnarioDeImagen#getNombre()
	 */
	public String getNombre() {
		return "Get Tiles Game Output";
	}
}
