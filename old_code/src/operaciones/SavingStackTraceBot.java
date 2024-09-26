package operaciones;

import java.awt.AWTException;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Stack;

import ui.DocumentoDeImagen;
import util.Indexador;
import ar.com.sodhium.drawbot.config.ScreenConfiguration;

public class SavingStackTraceBot implements OperadorUnarioDeImagen {
    private int clicks;
    private final int initX;
    private final String name;
    private final int step;
    private int currentFrameNumber;

    public SavingStackTraceBot(int initX, String name, int step) {
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
            int delayCount = 0;
            for (int i = initX; i < ancho - 2; i += step) {
                for (int j = 2; j < alto - 2; j += step) {
                    if (clicks > 20) {
                        System.out.print("." + delayCount++ + ".");
                        robot.delay(100);
                        clicks = 0;
                    }
                    if (indexador.get(i, j) > 180) {
                        stackDrawFrom(robot, i, j, indexador, offsetX, offsetY, ancho, alto);
                    }
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

    private void stackDrawFrom(Robot robot, int x, int y, Indexador indexador, int offsetX, int offsetY, int ancho,
            int alto) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        clicks++;
        robot.delay(15);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
        robot.mouseMove(offsetX + x, offsetY + y);
        robot.mousePress(InputEvent.BUTTON1_MASK);
        Stack<Point> processedPoints = new Stack<Point>();
        processedPoints.push(new Point(x, y));
        indexador.set(0, x, y);
        drawStack(processedPoints, robot, indexador, offsetX, offsetY, ancho, alto);
    }

    private void drawStack(Stack<Point> processedPoints, Robot robot, Indexador indexador, int offsetX, int offsetY,
            int ancho, int alto) throws NoSuchFieldException, SecurityException, IllegalArgumentException,
            IllegalAccessException {
        while (!processedPoints.isEmpty()) {
            Point currentPoint = processedPoints.peek();
            robot.delay(1);
            robot.mouseMove((int) currentPoint.getX() + offsetX, (int) currentPoint.getY() + offsetY);
            ArrayList<Point> surroundingPoints = getSurroundingPoints(currentPoint, alto, ancho);
            boolean found = false;
            for (Point nextPoint : surroundingPoints) {
                int x = (int) nextPoint.getX();
                int y = (int) nextPoint.getY();
                if (indexador.get(x, y) > 180) {
                    indexador.set(0, x, y);
                    robot.delay(1);
                    robot.mouseMove(x + offsetX, y + offsetY);
                    processedPoints.push(nextPoint);
                    found = true;
                    advanceFrameNumber(robot);
                    break;
                }
            }
            if (!found) {
                processedPoints.pop();
            }
        }
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
    }

    private void advanceFrameNumber(Robot robot) throws NoSuchFieldException, SecurityException,
            IllegalArgumentException, IllegalAccessException {
        int pixelRate = 10;
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

    private ArrayList<Point> getSurroundingPoints(Point currentPoint, int height, int width) {
        ArrayList<Point> output = new ArrayList<Point>();
        addIfInsideBounds(output, new Point((int) currentPoint.getX() - 1, (int) currentPoint.getY() - 1), height,
                width);
        addIfInsideBounds(output, new Point((int) currentPoint.getX() - 1, (int) currentPoint.getY()), height, width);
        addIfInsideBounds(output, new Point((int) currentPoint.getX() - 1, (int) currentPoint.getY() + 1), height,
                width);
        addIfInsideBounds(output, new Point((int) currentPoint.getX(), (int) currentPoint.getY() - 1), height, width);
        addIfInsideBounds(output, new Point((int) currentPoint.getX(), (int) currentPoint.getY() + 1), height, width);
        addIfInsideBounds(output, new Point((int) currentPoint.getX() + 1, (int) currentPoint.getY() - 1), height,
                width);
        addIfInsideBounds(output, new Point((int) currentPoint.getX() + 1, (int) currentPoint.getY()), height, width);
        addIfInsideBounds(output, new Point((int) currentPoint.getX() + 1, (int) currentPoint.getY() + 1), height,
                width);
        return output;
    }

    private void addIfInsideBounds(ArrayList<Point> output, Point point, int height, int width) {
        if (point.getX() >= 0 && point.getY() >= 0 && point.getX() <= width && point.getY() <= height) {
            output.add(point);
        }
    }

    public String getNombre() {
        return name;
    }
}
