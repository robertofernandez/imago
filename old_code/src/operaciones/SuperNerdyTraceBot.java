package operaciones;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;

import ui.DocumentoDeImagen;
import util.Indexador;
import ar.com.sodhium.drawbot.config.ScreenConfiguration;

/**
 * The idea of this bot is to trace everything that can be traced in x, but in
 * every step moving all the possible way in y.
 * 
 * @author roberto.fernandez
 * 
 */
public class SuperNerdyTraceBot implements OperadorUnarioDeImagen {
    int clicks;
    private final int initX;
    private final String name;
    private final int step;

    public SuperNerdyTraceBot(int initX, String name, int step) {
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
            int delayTimes = 0;
            int delayStep = 5;
            int ancho = entrada.getAncho();
            int alto = entrada.getAlto();
            int offsetX = ScreenConfiguration.getConfiguration().getOffsetX();
            int offsetY = ScreenConfiguration.getConfiguration().getOffsetY();
            Indexador indexador = new Indexador(entrada.getRed(), ancho, alto);

            for (int i = initX; i < ancho - 2; i += step) {
                for (int j = 2; j < alto - 2; j += step) {
                    if (clicks > 20) {
                        System.out.print("." + delayTimes + ".");
                        robot.delay(100 + delayStep * delayTimes);
                        delayTimes++;
                        clicks = 0;
                    }
                    if (xLength(i, j, indexador, ancho, alto) > 3) {
                        xDraw(robot, i, j, indexador, offsetX, offsetY, ancho, alto);
                    }
                }
            }

            for (int i = initX; i < ancho - 2; i += step) {
                for (int j = 2; j < alto - 2; j += step) {
                    if (clicks > 20) {
                        System.out.print("." + delayTimes + ".");
                        robot.delay(100 + delayStep * delayTimes);
                        delayTimes++;
                        clicks = 0;
                    }
                    if (yLength(i, j, indexador, ancho, alto) > 3) {
                        yDraw(robot, i, j, indexador, offsetX, offsetY, ancho, alto);
                    }
                }
            }
            //
            // for (int i = initX; i < ancho - 2; i += step) {
            // for (int j = 2; j < alto - 2; j += step) {
            // if (clicks > 20) {
            // System.out.print(".");
            // robot.delay(100);
            // clicks = 0;
            // }
            // traceDraw(robot, i, j, indexador, offsetX, offsetY, ancho, alto);
            // }
            // }
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

    private void traceDraw(Robot robot, int x, int y, Indexador indexador, int offsetX, int offsetY, int ancho, int alto) {
        if (indexador.get(x, y) > 180) {
            clicks++;
            robot.mouseRelease(InputEvent.BUTTON1_MASK);
            robot.mouseMove(offsetX + x, offsetY + y);
            robot.mousePress(InputEvent.BUTTON1_MASK);
        } else {
            return;
        }

        for (int j = x, i = y; i < ancho - 1 && j < alto - 1;) {
            int k = 0, l = 0;
            for (k = -1; k < 2; k++) {
                for (l = -1; l < 2; l++) {
                    if (indexador.get(j + k, i + l) > 180) {
                        break;
                    }
                }
            }

            if (indexador.get(j + k, i + l) > 180) {
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

    private void xDraw(Robot robot, int xInput, int yInput, Indexador indexador, int offsetX, int offsetY, int ancho,
            int alto) {
        int x = xInput;
        int y = yInput;
        if (indexador.get(x, y) > 180) {
            clicks++;
            robot.mouseRelease(InputEvent.BUTTON1_MASK);
            robot.mouseMove(offsetX + x, offsetY + y);
            robot.mousePress(InputEvent.BUTTON1_MASK);
        } else {
            return;
        }

        for (int xOffset = 0; xOffset + x < ancho - 1 && xOffset + y < alto - 1; xOffset++) {
            if (indexador.get(x + xOffset, y) > 180) {
                performMoveAndUpdatevars(robot, x, y, indexador, offsetX, offsetY, alto, xOffset);
            } else {
                // first step: try simple diagonal
                if (indexador.get(x + xOffset, y + 1) > 180) {
                    y++;
                    performMoveAndUpdatevars(robot, x, y, indexador, offsetX, offsetY, alto, xOffset);
                } else if (indexador.get(x + xOffset, y - 1) > 180) {
                    y--;
                    performMoveAndUpdatevars(robot, x, y, indexador, offsetX, offsetY, alto, xOffset);
                }
                robot.mouseRelease(InputEvent.BUTTON1_MASK);
                return;
            }
        }
    }

    private void performMoveAndUpdatevars(Robot robot, int x, int y, Indexador indexador, int offsetX, int offsetY,
            int alto, int xOffset) {
        indexador.set(0, x + xOffset, y);
        robot.mouseMove(x + xOffset + offsetX, y + offsetY);
        yMove(robot, indexador, x + xOffset + offsetX, y + offsetY, 1, alto);
        yMove(robot, indexador, x + xOffset + offsetX, y + offsetY, -1, alto);
        robot.mouseMove(x + xOffset + offsetX, y + offsetY);
    }

    private void yMove(Robot robot, Indexador indexador, int x, int y, int step, int alto) {
        for (int yOffset = 0; yOffset + y < alto - 1 && yOffset + y > 0; yOffset += step) {
            if (indexador.get(x, y + yOffset) > 180) {
                indexador.set(0, x, y + yOffset);
                robot.mouseMove(x, y + yOffset);
            } else {
                return;
            }
        }
    }

    private void xMove(Robot robot, Indexador indexador, int x, int y, int step, int ancho) {
        for (int xOffset = 0; xOffset + x < ancho - 1 && xOffset + x > 0; xOffset += step) {
            if (indexador.get(x + xOffset, y) > 180) {
                indexador.set(0, x + xOffset, y);
                robot.mouseMove(x + xOffset, y);
            } else {
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

    private void yDraw(Robot robot, int x, int y, Indexador indexador, int offsetX, int offsetY, int ancho, int alto) {
        if (indexador.get(x, y) > 180) {
            clicks++;
            robot.mouseRelease(InputEvent.BUTTON1_MASK);
            robot.mouseMove(offsetX + x, offsetY + y);
            robot.mousePress(InputEvent.BUTTON1_MASK);
        } else {
            return;
        }

        for (int yOffset = 0; yOffset + x < ancho - 1 && yOffset + y < alto - 1; yOffset++) {
            if (indexador.get(x, y + yOffset) > 180) {
                indexador.set(0, x, y + yOffset);
                robot.mouseMove(x + offsetX, y + yOffset + offsetY);
                xMove(robot, indexador, x + offsetX, y + offsetY, 1, ancho);
                xMove(robot, indexador, x + offsetX, y + offsetY, -1, ancho);
            } else {
                robot.mouseRelease(InputEvent.BUTTON1_MASK);
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
