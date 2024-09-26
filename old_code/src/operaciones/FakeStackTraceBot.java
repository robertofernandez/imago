package operaciones;

import java.awt.Point;
import java.awt.event.InputEvent;
import java.util.ArrayList;
import java.util.Stack;

import ui.DocumentoDeImagen;
import util.Indexador;
import ar.com.sodhium.drawbot.config.ScreenConfiguration;
import ar.com.sodhium.drawbot.test.FakeRobot;

public class FakeStackTraceBot implements OperadorUnarioDeImagen {
    int clicks;
    private final int initX;
    private final String name;
    private final int step;

    public FakeStackTraceBot(int initX, String name, int step) {
        super();
        this.initX = initX;
        this.name = name;
        this.step = step;
    }

    public DocumentoDeImagen operar(DocumentoDeImagen entrada) {
        FakeRobot robot;
        try {
            robot = new FakeRobot();
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

    private void stackDrawFrom(FakeRobot robot, int x, int y, Indexador indexador, int offsetX, int offsetY, int ancho,
            int alto) {
        clicks++;
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
        robot.mouseMove(offsetX + x, offsetY + y);
        robot.mousePress(InputEvent.BUTTON1_MASK);
        Stack<Point> processedPoints = new Stack<Point>();
        processedPoints.push(new Point(x, y));
        indexador.set(0, x, y);
        drawStack(processedPoints, robot, indexador, offsetX, offsetY, ancho, alto);
    }

    private void drawStack(Stack<Point> processedPoints, FakeRobot robot, Indexador indexador, int offsetX,
            int offsetY, int ancho, int alto) {
        while (!processedPoints.isEmpty()) {
            Point currentPoint = processedPoints.peek();
            ArrayList<Point> surroundingPoints = getSurroundingPoints(currentPoint, alto, ancho);
            boolean found = false;
            for (Point nextPoint : surroundingPoints) {
                int x = (int) nextPoint.getX();
                int y = (int) nextPoint.getY();
                if (indexador.get(x, y) > 180) {
                    indexador.set(0, x, y);
                    robot.mouseMove(x + offsetX, y + offsetY);
                    processedPoints.push(nextPoint);
                    found = true;
                    break;
                }
            }
            if (!found) {
                processedPoints.pop();
            }
        }
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
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
