package operaciones;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import ui.DocumentoDeImagen;
import util.Indexador;

public class SimpleTraceToFile2 implements OperadorUnarioDeImagen {
    private int initX;
    private final String name;
    private int step;
    private File logFile;
    private BufferedWriter bufferedFileWriter;

    public SimpleTraceToFile2(int initX, String name, int step) {
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

            /*
            for (int i = initX; i < ancho - 2; i += step) {
                for (int j = 2; j < alto - 2; j += step) {
                    if (xLength(i, j, indexador, ancho, alto) > 3) {
                        xDraw(i, j, indexador, ancho, alto);
                    }
                }
            }

            appendLine("release");
            appendLine("comment: finish x draw");

            for (int i = initX; i < ancho - 2; i += step) {
                for (int j = 2; j < alto - 2; j += step) {
                    if (yLength(i, j, indexador, ancho, alto) > 3) {
                        yDraw(i, j, indexador, ancho, alto);
                    }
                }
            }*/

            appendLine("release");
            appendLine("comment: finish y draw");

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
        if (indexador.get(x, y) > 180) {
            if(312 == x  && 126 == y){
                System.out.print("here");
            }
            appendLine("goto:" + x + "," + y);
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

    private void xDraw(int x, int y, Indexador indexador, int ancho, int alto) throws IOException {
        if (indexador.get(x, y) > 180) {
            indexador.set(0, x , y);
            appendLine("goto:" + x + "," + y);
        } else {
            return;
        }

        for (int xOffset = 0; xOffset + x < ancho - 1 && xOffset + y < alto - 1; xOffset++) {
            if (indexador.get(x + xOffset, y) > 180) {
                indexador.set(0, x + xOffset, y);
                appendLine("goto:" + (x + xOffset) + "," + y);
            } else {
                appendLine("release");
                return;
            }
        }
    }

    private int xLength(int x, int y, Indexador indexador, int ancho, int alto) {
        int tl = 0;
        if (!(indexador.get(x, y) > 180)) {
            return 0;
        }

        for (int xOffset = 0; xOffset + x < ancho - 1 && xOffset + y < alto - 1; xOffset++) {
            if (indexador.get(x + xOffset, y) > 180) {
                tl++;
            } else {
                return tl;
            }
        }
        return tl;
    }

    private void yDraw(int x, int y, Indexador indexador, int ancho, int alto) throws IOException {
        if (indexador.get(x, y) > 180) {
            indexador.set(0, x , y);
            appendLine("goto:" + x + "," + y);
        } else {
            return;
        }

        for (int yOffset = 0; yOffset + x < ancho - 1 && yOffset + y < alto - 1; yOffset++) {
            if (indexador.get(x, y + yOffset) > 180) {
                indexador.set(0, x, y + yOffset);
                appendLine("goto:" + x + "," + (y + yOffset));
            } else {
                appendLine("release");
                return;
            }
        }
    }

    private int yLength(int x, int y, Indexador indexador, int ancho, int alto) {
        int tl = 0;
        if (!(indexador.get(x, y) > 180)) {
            return 0;
        }

        for (int xOffset = 0; xOffset + x < ancho - 1 && xOffset + y < alto - 1; xOffset++) {
            if (indexador.get(x, y + xOffset) > 180) {
                tl++;
            } else {
                return tl;
            }
        }
        return tl;
    }

    public String getNombre() {
        return name;
    }

}
