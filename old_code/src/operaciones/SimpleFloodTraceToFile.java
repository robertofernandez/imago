package operaciones;

import java.awt.Point;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import ui.DocumentoDeImagen;
import util.Indexador;

public class SimpleFloodTraceToFile implements OperadorUnarioDeImagen {
    private int initX;
    private final String name;
    private int step;
    private File logFile;
    private BufferedWriter bufferedFileWriter;
    
    public SimpleFloodTraceToFile(int initX, String name, int step) {
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
                    simpleFloodTraceDraw(i, j, indexador, ancho, alto);
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

    private void simpleFloodTraceDraw(int x, int y, Indexador indexador, int ancho, int alto) throws IOException {
        ArrayList<Point> paintedPoints = new ArrayList<Point>();
        HashMap<String, Boolean> containedElements = new HashMap<String, Boolean>();
        if (indexador.get(x, y) > 180) {
            paintedPoints.add(new Point(x, y));
            containedElements.put("" + x + "," + y, true);
            
            boolean couldAdd = true;
            while(couldAdd){
                
            }
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
            }
            else {
                for (k = -1; k < 2; k++) {
                    if(found){
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
                if(312 == j  && 126 == i){
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
    public class PixelAsNode{
        private int x;
        private int y;
        
        private PixelAsNode previousNode;
        private Vector<PixelAsNode> nextNodes;
        
        public PixelAsNode() {
            nextNodes = new Vector<SimpleFloodTraceToFile.PixelAsNode>();
        }
    };
}
