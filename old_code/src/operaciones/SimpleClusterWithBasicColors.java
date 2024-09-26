/**
 * 
 */
package operaciones;

import java.util.ArrayList;

import operaciones.clustering.CubicClusterSet;
import operaciones.clustering.CubicColorCluster;
import ui.DocumentoDeImagen;
import util.Indexador;

/**
 * @author UTN user
 * 
 */
public class SimpleClusterWithBasicColors implements OperadorUnarioDeImagen {

    /**
	 * 
	 */
    public SimpleClusterWithBasicColors() {
        super();
    }

    /*
     * (non-Javadoc)
     * 
     * @see operaciones.OperadorUnarioDeImagen#operar(ui.DocumentoDeImagen)
     */
    public DocumentoDeImagen operar(DocumentoDeImagen entrada) {
        DocumentoDeImagen salida = new DocumentoDeImagen(entrada.getImagen(), entrada.getTitle() + "(Clustered -bc-)",
                entrada.getPadre());
        salida.descomponer();

        int ancho = salida.getAncho();
        int alto = salida.getAlto();

        int[] red = salida.getRed();
        int[] green = salida.getGreen();
        int[] blue = salida.getBlue();

        CubicClusterSet clusterSet = new CubicClusterSet(90);
        clusterSet.addBasicColor(255, 0, 0);
        clusterSet.addBasicColor(0, 255, 0);
        clusterSet.addBasicColor(0, 0, 255);
        clusterSet.addBasicColor(255, 255, 0);
        clusterSet.addBasicColor(255, 0, 255);
        clusterSet.addBasicColor(0, 255, 255);
        clusterSet.addBasicColor(255, 255, 255);
        clusterSet.addBasicColor(100, 0, 0);
        clusterSet.addBasicColor(0, 100, 0);
        clusterSet.addBasicColor(0, 0, 100);
        clusterSet.addBasicColor(100, 100, 0);
        clusterSet.addBasicColor(100, 0, 100);
        clusterSet.addBasicColor(0, 100, 100);
        clusterSet.addBasicColor(100, 100, 100);
        clusterSet.addBasicColor(0, 0, 0);
        clusterSet.addBasicColor(65, 98, 160);

        Indexador redIndex = new Indexador(red, ancho, alto);
        Indexador greenIndex = new Indexador(green, ancho, alto);
        Indexador blueIndex = new Indexador(blue, ancho, alto);

        for (int i = 0; i < alto; i++) {
            for (int j = 0; j < ancho; j++) {
                clusterSet.addElement(redIndex.get(i, j), greenIndex.get(i, j), blueIndex.get(i, j));
            }
        }

        ArrayList<CubicColorCluster> activeClusters = clusterSet.setActiveClusters();
        clusterSet.linkToNearestCluster(activeClusters);
        clusterSet.assigBasicColorsToActiveClusters();

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
                CubicColorCluster cluster = clusterSet.getCluster(redValue, greenValue, blueValue);
                if (!cluster.isActive()) {
                    cluster = clusterSet.getNearestActiveCluster(redValue, greenValue, blueValue);
                }
                if (cluster != null) {
                    newRedIndex.set(cluster.getAssignedColor().getRed(), i, j);
                    newGreenIndex.set(cluster.getAssignedColor().getGreen(), i, j);
                    newBlueIndex.set(cluster.getAssignedColor().getBlue(), i, j);
                } else {
                    newRedIndex.set(0, i, j);
                    newGreenIndex.set(0, i, j);
                    newBlueIndex.set(0, i, j);
                }
            }
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

    /*
     * (non-Javadoc)
     * 
     * @see operaciones.OperadorUnarioDeImagen#getNombre()
     */
    public String getNombre() {
        return "Simple Cluster with basic colors";
    }
}
