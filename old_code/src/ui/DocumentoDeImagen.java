/**
 * @author Roberto G. Fern&aacute;ndez
 * @version 0.01
 *
 * Nombre del archivo: DocumentoDeImagen.java
 *
 * Historia de Creación/Modificación :
 *
 * Autor del cambio             Fecha             Descripción
 * -----------------------      ----------        ---------------------------------------
 * Roberto G. Fernández         24/08/2007        Creación
 *
 */

package ui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.image.ImageObserver;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;
import java.io.File;

import javax.swing.JInternalFrame;
import javax.swing.WindowConstants;

import util.iterators.ColorValuesCollection;

/**
 * Clase que representa los documentos de imagen.
 * 
 * @author UTN user
 * 
 */
public class DocumentoDeImagen extends JInternalFrame {
	private static final long serialVersionUID = -7565056578582724726L;

	private Image imagen;

	private int alto;

	private int ancho;

	private File archivoDeImagen;

	private int[] mapa;

	private int[] red;

	private int[] green;

	private int[] blue;

	private int[] alpha;

	private ImagoPpal padre;

	private int id;

	/**
	 * Constructor.
	 * 
	 * @param entrada
	 *            Archivo de imagen.
	 * @param padre
	 *            Ventana a la cual pertenece.
	 */
	public DocumentoDeImagen(File entrada, ImagoPpal padre) {
		super(entrada.getName(), true, // resizable
				true, // closable
				true, // maximizable
				true); // iconifiable

		archivoDeImagen = entrada;

		this.padre = padre;

		this.addInternalFrameListener(padre);

		this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		imagen = Toolkit.getDefaultToolkit().getImage(entrada.getPath());

		// Se utilzia un objeto MediaTracker para bloquear la tarea hasta
		// que la imagen se haya cargado o hayan transcurrido 10 segundos
		// desde que se inicia la carga
		MediaTracker tracker = new MediaTracker(this);
		tracker.addImage(imagen, 1);

		try {
			if (!tracker.waitForID(1, 10000)) {
				System.out.println("Error en la carga de la imagen");
				System.exit(1);
			}
		} catch (InterruptedException e) {
			System.out.println(e);
		}

		this.inicializar(imagen);
	}

	/**
	 * Inicializador de la ventana.
	 * 
	 * @param imagenDeEntrada
	 *            Imagen que contendrá la ventana.
	 */
	private void inicializar(Image imagenDeEntrada) {
		imagen = imagenDeEntrada;
		// Se establecen las dimensiones de la ventana de manera que se ajusten
		// a las de la imagen
		ancho = imagen.getWidth(this);
		alto = imagen.getHeight(this);
		// Se hace visible el Frame
		this.setVisible(true);

		int insetArriba = this.getInsets().top;
		int insetIzqda = this.getInsets().left;

		// Se usan las dimensiones de insets y el tamaño de la imagen
		// fuente para establecer el tamaño total del Frame.
		this.setSize(2 * insetIzqda + ancho, 2 * insetArriba + alto + 25);

		mapa = new int[ancho * alto];
		// Se convierte la "imagenFuente" a representación numérica que
		// corresponde a sus pixels, de forma que se puedan manipular.
		// Esto hay que colocarlo en un bloque try-catch, porque tenemos
		// que estar prevenidos para recoger las excepciones de tipo
		// "InterrruptedException" que puede lanzar el método grabPixels()
		try {
			// Se instancia un objeto de tipo PixelGrabber, pasándole como
			// parámetro el array de pixels en donde queremos guardar la
			// representación numérica de la imagen que vamos manipular
			PixelGrabber pgObj = new PixelGrabber(imagen, 0, 0, ancho, alto,
					mapa, 0, ancho);
			if (!(pgObj.grabPixels() && ((pgObj.getStatus() & ImageObserver.ALLBITS) != 0)))
				throw new Exception("Error al descomponer imagen");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Constructor.
	 * 
	 * @param imagenDeEntrada
	 *            Imagen que contendrá la ventana.
	 * @param titulo
	 *            Título de la ventana.
	 * @param padre
	 *            Ventana a la cual pertenece.
	 */
	public DocumentoDeImagen(Image imagenDeEntrada, String titulo,
			ImagoPpal padre) {
		super(titulo, true, // resizable
				true, // closable
				true, // maximizable
				true); // iconifiable

		this.padre = padre;
		this.addInternalFrameListener(padre);

		archivoDeImagen = null;
		imagen = imagenDeEntrada;

		this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

		this.inicializar(imagen);
	}

	public void paint(Graphics g) {
		super.paint(g);
		if (imagen != null) {
			g.drawImage(imagen, this.getInsets().left,
					this.getInsets().top + 25, this);
		}
	}

	public Image getImagen() {
		return imagen;
	}

	public ImagoPpal getPadre() {
		return padre;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Método a utilizar cuando la imagen deba ser actualizada por alguna
	 * modificación a su mapa.
	 * 
	 */
	public void actualizarImagen() {
		imagen = this.createImage(new MemoryImageSource(ancho, alto, mapa, 0,
				ancho));
		this.setSize(2 * this.getInsets().left + ancho, 2
				* this.getInsets().top + alto + 25);
	}

	public int[] getMapa() {
		return mapa;
	}

	public void setMapa(int[] mapa) {
		this.mapa = mapa;
	}

	public int getAlto() {
		return alto;
	}

	public void setAlto(int alto) {
		this.alto = alto;
	}

	public int getAncho() {
		return ancho;
	}

	public void setAncho(int ancho) {
		this.ancho = ancho;
	}

	public File getArchivoDeImagen() {
		return archivoDeImagen;
	}

	public void setArchivoDeImagen(File archivoDeImagen) {
		this.archivoDeImagen = archivoDeImagen;
	}

	public int[] getAlpha() {
		if (alpha == null)
			descomponer();
		return alpha;
	}

	public void setAlpha(int[] alpha) {
		this.alpha = alpha;
	}

	public int[] getBlue() {
		if (blue == null)
			descomponer();
		return blue;
	}

	public void setBlue(int[] blue) {
		this.blue = blue;
	}

	public int[] getGreen() {
		if (green == null)
			descomponer();
		return green;
	}

	public void setGreen(int[] green) {
		this.green = green;
	}

	public int[] getRed() {
		if (red == null)
			descomponer();
		return red;
	}

	public void setRed(int[] red) {
		this.red = red;
	}

	/**
	 * Método a ser utilizado para descomponer la imagen en sus componentes RGB +
	 * transparencia.
	 * 
	 */
	public void descomponer() {
		if (this.mapa == null)
			return;
		alpha = new int[ancho * alto];
		red = new int[ancho * alto];
		green = new int[ancho * alto];
		blue = new int[ancho * alto];

		for (int i = 0; i < ancho * alto; i++) {
			blue[i] = mapa[i] & 0xFF;
			red[i] = (mapa[i] >> 8) & 0xFF;
			green[i] = (mapa[i] >> 16) & 0xFF;
			alpha[i] = (mapa[i] >> 24) & 0xFF;
		}
	}

	/**
	 * Método a ser utilizado para recomponer la imagen en sus componentes RGB +
	 * transparencia.
	 * 
	 */
	public void recomponer() {
		if (alpha == null)
			return;

		for (int i = 0; i < ancho * alto; i++) {
			mapa[i] = blue[i] | (red[i] << 8) | (green[i] << 16)
					| (alpha[i] << 24);
		}
	}

	public ColorValuesCollection getRedCollection() {
		return new ColorValuesCollection(getRed());
	}

	public ColorValuesCollection getGreenCollection() {
		return new ColorValuesCollection(getGreen());
	}

	public ColorValuesCollection getBlueCollection() {
		return new ColorValuesCollection(getBlue());
	}
}
