package co.itpex.imago2.app;

import co.itpex.imago2.app.view.MainFrame;

import javax.swing.*;

public class MainApp {

    public static void main(String[] args) {
        // Set FlatLaf theme for a modern appearance
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        // Launch the main frame
        SwingUtilities.invokeLater(MainFrame::new);
    }
}
