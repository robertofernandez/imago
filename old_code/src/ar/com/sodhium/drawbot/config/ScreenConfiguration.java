package ar.com.sodhium.drawbot.config;

/**
 * @author roberto.fernandez
 * 
 */
public class ScreenConfiguration {
    private static ScreenConfiguration instance;

    private Integer offsetX;
    private Integer offsetY;

    private ScreenConfiguration() {
        offsetX = 100;
        offsetY = 100;
    }

    public static ScreenConfiguration getConfiguration() {
        if (instance == null) {
            instance = new ScreenConfiguration();
        }
        return instance;
    }

    public Integer getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(Integer offsetX) {
        this.offsetX = offsetX;
    }

    public Integer getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(Integer offsetY) {
        this.offsetY = offsetY;
    }

}
