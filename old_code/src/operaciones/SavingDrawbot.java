package operaciones;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.lang.reflect.Field;

import ui.DocumentoDeImagen;
import util.Indexador;
import ar.com.sodhium.drawbot.config.ScreenConfiguration;

public class SavingDrawbot implements OperadorUnarioDeImagen {
    private int clicks;
    private final int initX;
    private final String name;
    private final int step;
    private int currentFrameNumber;

    public SavingDrawbot(int initX, String name, int step) {
        super();
        this.initX = initX;
        this.name = name;
        this.step = step;
        currentFrameNumber = 0;
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
        } catch (NoSuchFieldException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
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
            int alto) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        if (indexador.get(x, y) > 180) {
            clicks++;
            robot.mouseRelease(InputEvent.BUTTON1_MASK);
            robot.mouseMove(offsetX + x, offsetY + y);
            robot.mousePress(InputEvent.BUTTON1_MASK);
            advanceFrameNumber(robot);
        } else {
            return;
        }

        for (int diagonalOffset = 0; diagonalOffset + x < ancho - 1 && diagonalOffset + y < alto - 1; diagonalOffset++) {
            if (indexador.get(x + diagonalOffset, y + diagonalOffset) > 180) {
                indexador.set(0, x + diagonalOffset, y + diagonalOffset);
                advanceFrameNumber(robot);
                robot.mouseMove(x + diagonalOffset + offsetX, y + diagonalOffset + offsetY);
            } else {
                robot.mouseRelease(InputEvent.BUTTON1_MASK);
                return;
            }
        }
    }

    private void advanceFrameNumber(Robot robot) throws NoSuchFieldException, SecurityException,
            IllegalArgumentException, IllegalAccessException {
        int pixelRate = 30;
        if (currentFrameNumber % pixelRate == 0) {

            robot.keyPress(KeyEvent.VK_SHIFT);
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_S);

            robot.keyRelease(KeyEvent.VK_S);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyRelease(KeyEvent.VK_SHIFT);

            // robot.keyPress(KeyEvent.VK_SHIFT);
            robot.keyPress(KeyEvent.VK_END);

            robot.keyRelease(KeyEvent.VK_END);
            // robot.keyRelease(KeyEvent.VK_SHIFT);

            // robot.keyPress(KeyEvent.VK_DELETE);
            // robot.keyRelease(KeyEvent.VK_DELETE);

            for (int j = 0; j < 9; j++) {
                robot.keyPress(KeyEvent.VK_BACK_SPACE);
                robot.keyRelease(KeyEvent.VK_BACK_SPACE);
            }

            robot.delay(500);

            // name.substring(4)
            String fileName = String.format("%05d", currentFrameNumber / pixelRate);

            System.out.println("[" + fileName + "]");
            typeString(fileName, robot);
            robot.keyPress(KeyEvent.VK_PERIOD);
            robot.keyRelease(KeyEvent.VK_PERIOD);
            typeString("jpg", robot);

            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);

            robot.delay(2000);

        }
        currentFrameNumber++;
    }

    @SuppressWarnings("rawtypes")
    private void typeString(String fileName, Robot robot) throws NoSuchFieldException, SecurityException,
            IllegalArgumentException, IllegalAccessException {
        String inUpperCase = fileName.toUpperCase();
        for (int i = 0; i < fileName.length(); i++) {
            char currentChar = fileName.charAt(i);
            char upperCaseChar = inUpperCase.charAt(i);

            boolean upperCase = Character.isUpperCase(currentChar);
            String variableName = "VK_" + upperCaseChar;

            Class clazz = KeyEvent.class;
            Field field = clazz.getField(variableName);
            int keyCode = field.getInt(null);
            if (upperCase) {
                robot.keyPress(KeyEvent.VK_SHIFT);
            }

            robot.keyPress(keyCode);
            robot.keyRelease(keyCode);

            if (upperCase)
                robot.keyRelease(KeyEvent.VK_SHIFT);
        }

    }

    public String getNombre() {
        return name;
    }

}
