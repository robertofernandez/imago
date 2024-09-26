package operaciones;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;

import ui.DocumentoDeImagen;
import util.Indexador;
import ar.com.sodhium.drawbot.config.ScreenConfiguration;

public class Drawbot implements OperadorUnarioDeImagen {
    int clicks;
    private final int initX;
    private final String name;
    private final int step;

    public Drawbot(int initX, String name, int step) {
        super();
        this.initX = initX;
        this.name = name;
        this.step = step;
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
            Indexador indexador = new Indexador(entrada.getRed(), ancho, alto);

            for (int i = initX; i < ancho - 2; i += step) {
                for (int j = 2; j < alto - 2; j += step) {
                    if (clicks > 20) {
                        System.out.print(".");
                        robot.delay(100);
                        clicks = 0;
                    }
                    // traceDraw(robot, i, j, indexador, offsetX, offsetY,
                    // ancho, alto);
                    diagonalDraw(robot, i, j, indexador, offsetX, offsetY, ancho, alto);
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

    private void diagonalDraw(Robot robot, int x, int y, Indexador indexador, int offsetX, int offsetY, int ancho,
            int alto) {
        if (indexador.get(x, y) > 180) {
            clicks++;
            robot.mouseRelease(InputEvent.BUTTON1_MASK);
            robot.mouseMove(offsetX + x, offsetY + y);
            robot.mousePress(InputEvent.BUTTON1_MASK);
        } else {
            return;
        }

        for (int diagonalOffset = 0; diagonalOffset + x < ancho - 1 && diagonalOffset + y < alto - 1; diagonalOffset++) {
            if (indexador.get(x + diagonalOffset, y + diagonalOffset) > 180) {
                indexador.set(0, x + diagonalOffset, y + diagonalOffset);
                robot.mouseMove(x + diagonalOffset + offsetX, y + diagonalOffset + offsetY);
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
