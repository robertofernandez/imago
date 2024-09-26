package operaciones;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;

import ui.DocumentoDeImagen;
import util.Indexador;
import ar.com.sodhium.drawbot.config.ScreenConfiguration;

public class TraceBorders implements OperadorUnarioDeImagen {
    int clicks;
    private final int initX;
    private final String name;

    public TraceBorders(int initX, String name) {
        super();
        this.initX = initX;
        this.name = name;
    }

    public DocumentoDeImagen operar(DocumentoDeImagen entrada) {
        Robot robot;
        try {
            robot = new Robot();
            robot.delay(5000);
            clicks = 0;
            int ancho = entrada.getAncho();
            int alto = entrada.getAlto();
            int offsetX = ScreenConfiguration.getConfiguration().getOffsetX();
            int offsetY = ScreenConfiguration.getConfiguration().getOffsetY();
            Indexador indexador = new Indexador(entrada.getGreen(), ancho, alto);
            long[] cumulativeFrecuencies = new long[25];

            int maxColor = 0;
            int maxX = 0;
            int maxY = 0;
            double total = 0;
            double amount = 0;
            for (int x = initX; x < ancho - 2; x++) {
                for (int y = 2; y < alto - 2; y++) {
                    int currentColor = indexador.get(x, y);
                    total++;
                    amount += currentColor;
                    if (currentColor > maxColor) {
                        maxColor = currentColor;
                        maxX = x;
                        maxY = y;
                    }
                    for (int i = currentColor / 10; i >= 0; i--) {
                        cumulativeFrecuencies[i]++;
                    }
                }
            }
            System.out.print("max color: " + maxColor);
            int umbral = 2;
            System.out.print("frec: ");
            for (int i = 24; i > 2; i--) {
                System.out.print("|" + cumulativeFrecuencies[i]);
                if (cumulativeFrecuencies[i] > ancho * alto / 20) {
                    umbral = i;
                    break;
                }
            }

            umbral *= 25;

            System.out.print("umbral: " + umbral);

            for (int x = initX; x < ancho - 2; x++) {
                for (int y = 2; y < alto - 2; y++) {
                    int currentColor = indexador.get(x, y);
                    if (currentColor > umbral) {
                        traceFrom(robot, x, y, indexador, offsetX, offsetY, ancho, alto, umbral);
                    }
                }
            }

        } catch (AWTException e) {
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

    private void traceFrom(Robot robot, int x, int y, Indexador indexador, int offsetX, int offsetY, int ancho,
            int alto, int umbral) {
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
        robot.mouseMove(offsetX + x, offsetY + y);
        robot.mousePress(InputEvent.BUTTON1_MASK);

        for (int j = x, i = y; i < ancho - 1 && j < alto - 1;) {
            int k = 0, l = 0;
            for (k = -1; k < 2; k++) {
                for (l = -1; l < 2; l++) {
                    if (indexador.get(j + k, i + l) > umbral) {
                        break;
                    }
                }
            }

            if (indexador.get(j + k, i + l) > umbral) {
                indexador.set(0, j + k, i + l);
                i = i + l;
                j = j + k;
                robot.mouseMove(j + offsetX, i + offsetY);
            } else {
                robot.mouseRelease(InputEvent.BUTTON1_MASK);
                return;
            }
        }
    }

    public String getNombre() {
        return name;
    }

}
