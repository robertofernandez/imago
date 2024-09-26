package trace;

import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.BufferedReader;
import java.io.FileReader;

import org.junit.Test;

import ar.com.sodhium.drawbot.config.ScreenConfiguration;

public class TraceTest {

    private static final int CLICKS_DELAY = 20;
    private static final int MOVE_DELAY = 2;
    private Robot robot;

    @Test
    public void test() throws Exception {

        Integer currentPositionX = -1;
        Integer currentPositionY = -1;

        int offsetX = ScreenConfiguration.getConfiguration().getOffsetX();
        int offsetY = ScreenConfiguration.getConfiguration().getOffsetY();

        robot = new Robot();
        robot.delay(3000);

        FileReader fr = new FileReader("out.txt");
        BufferedReader br = new BufferedReader(fr);
        String command;
        boolean penDown = false;
        while ((command = br.readLine()) != null) {
            if ("release".equals(command)) {
                robot.mouseRelease(InputEvent.BUTTON1_MASK);
                penDown = false;
                robot.delay(CLICKS_DELAY);
            } else if (command.startsWith("goto:")) {
                String coordinates = command.replaceFirst("goto:", "");
                String[] splitCoordinates = coordinates.split(",");
                Integer newX = Integer.valueOf(splitCoordinates[0]);
                Integer newY = Integer.valueOf(splitCoordinates[1]);
                if (!(newX.equals(currentPositionX) && newX.equals(currentPositionY))) {
                    // FIXME why are releases missing?
                    if (Math.abs(newX - currentPositionX) > 1 || Math.abs(newY - currentPositionY) > 1 && penDown) {
                        System.out.println("why?? (" + newX + " - " + currentPositionX + "; " + newY + " - "
                                + currentPositionY);
                        robot.mouseRelease(InputEvent.BUTTON1_MASK);
                        penDown = false;
                        robot.delay(CLICKS_DELAY);
                    }
                    currentPositionX = newX;
                    currentPositionY = newY;
                    robot.delay(MOVE_DELAY);
                    robot.mouseMove(offsetX + currentPositionX, offsetY + currentPositionY);
                    if (!penDown) {
                        robot.mousePress(InputEvent.BUTTON1_MASK);
                        penDown = true;
                    }
                }
            }
        }
        fr.close();
    }

}
