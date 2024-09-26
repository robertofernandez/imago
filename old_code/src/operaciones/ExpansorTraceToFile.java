package operaciones;

import java.awt.Point;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

import ui.DocumentoDeImagen;
import util.Indexador;

public class ExpansorTraceToFile implements OperadorUnarioDeImagen {

    public class Cluster {
        private int currentLimitX1;
        private int currentLimitX2;

        private int currentLimitY1;
        private int currentLimitY2;

        private Vector<Point> points;
        private final int direction1;
        private final int direction2;

        public Cluster(int initialX, int initialY, int firstDirectionX, int firstDirectionY) {
            this.direction1 = firstDirectionX;
            this.direction2 = firstDirectionY;
            currentLimitX1 = initialX;
            currentLimitX2 = initialX;
            currentLimitY1 = initialY;
            currentLimitY2 = initialY;
            points = new Vector<Point>();
        }

        public Vector<Point> getPoints() {
            return points;
        }
    }

    private int initX;
    private final String name;
    private int step;
    private File logFile;
    private BufferedWriter bufferedFileWriter;

    public ExpansorTraceToFile(int initX, String name, int step) {
        super();
        this.initX = initX;
        this.name = name;
        this.step = step;
    }

    public void closeFile() throws IOException {
        bufferedFileWriter.flush();
        bufferedFileWriter.close();
    }

    public void flushFile() throws IOException {
        bufferedFileWriter.flush();
    }

    public void appendLine(String lineToAppend) throws IOException {
        bufferedFileWriter.append(lineToAppend + "\n");
    }

    public void linkToPhysicalFile() throws IOException {
        logFile = new File("out.txt");
        FileWriter fileWriter;
        if (!logFile.exists()) {
            logFile.createNewFile();
            fileWriter = new FileWriter(logFile);
        } else {
            fileWriter = new FileWriter(logFile, true);
        }
        bufferedFileWriter = new BufferedWriter(fileWriter);
    }

    public DocumentoDeImagen operar(DocumentoDeImagen entrada) {
        try {
            linkToPhysicalFile();
            int ancho = entrada.getAncho();
            int alto = entrada.getAlto();
            Indexador indexador = new Indexador(entrada.getRed(), ancho, alto);

            for (int i = initX; i < ancho - 2; i += step) {
                for (int j = 2; j < alto - 2; j += step) {
                    traceDraw(i, j, indexador, ancho, alto);
                }
            }
            closeFile();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        DocumentoDeImagen salida = new DocumentoDeImagen(entrada.getImagen(), entrada.getTitle() + "(Copia salida)",
                entrada.getPadre());
        salida.descomponer();
        salida.recomponer();
        salida.actualizarImagen();
        return salida;
    }

    private void traceDraw(int x, int y, Indexador indexador, int ancho, int alto) throws IOException {
        Cluster horizontalCluster = new Cluster(x, y, 1 ,0);
        Cluster verticalCluster = new Cluster(x, y, 0, 1);
        Cluster firstDiagonalCluster = new Cluster(x, y, 1, -1);
        Cluster secondDiagonalCluster = new Cluster(x, y, 1, 1);
        if (indexador.get(x, y) > 180) {
            // if(312 == x && 126 == y){
            // System.out.print("here");
            // }
            horizontalCluster.getPoints().add(new Point(x, y));
            verticalCluster.getPoints().add(new Point(x, y));
            firstDiagonalCluster.getPoints().add(new Point(x, y));
            secondDiagonalCluster.getPoints().add(new Point(x, y));
            // appendLine("goto:" + x + "," + y);
        } else {
            return;
        }

        Integer previousDirectionX = null;
        Integer previousDirectionY = null;
        for (int j = x, i = y; i < ancho - 1 && j < alto - 1;) {
            int k = 0, l = 0;
            boolean found = false;
            if (previousDirectionX != null && (indexador.get(j + previousDirectionX, i + previousDirectionY) > 180)) {
                k = previousDirectionX;
                l = previousDirectionY;
                found = true;
            } else {
                for (k = -1; k < 2; k++) {
                    if (found) {
                        break;
                    }
                    for (l = -1; l < 2; l++) {
                        if (indexador.get(j + k, i + l) > 180) {
                            previousDirectionX = k;
                            previousDirectionY = l;
                            found = true;
                            break;
                        }
                    }
                }
            }

            if (found) {
                indexador.set(0, j + k, i + l);
                i = i + l;
                j = j + k;
                if (312 == j && 126 == i) {
                    System.out.print("here");
                }
                appendLine("goto:" + j + "," + i);
            } else {
                appendLine("release");
                previousDirectionX = null;
                previousDirectionY = null;
                return;
            }
        }
    }

    public String getNombre() {
        return name;
    }

}
