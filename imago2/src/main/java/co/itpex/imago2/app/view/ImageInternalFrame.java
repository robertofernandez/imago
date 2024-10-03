package co.itpex.imago2.app.view;

import javax.swing.*;
import java.awt.*;

public class ImageInternalFrame extends JInternalFrame {

    private static final long serialVersionUID = 2541604635767642663L;

    public ImageInternalFrame(String title, ImageIcon imageIcon) {
        super(title, true, true, true, true); // resizable, closable, maximizable, iconifiable
        setSize(300, 300);

        // Add the image to the internal frame
        JLabel imageLabel = new JLabel(imageIcon);
        JScrollPane scrollPane = new JScrollPane(imageLabel);

        add(scrollPane, BorderLayout.CENTER);
    }
}
