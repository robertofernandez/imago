package co.itpex.imago2.app.components;

import javax.swing.*;

public class CustomToolbar extends JToolBar {

    private static final long serialVersionUID = 1430874053920239452L;

    public CustomToolbar() {
        super("Tools");

        // Example toolbar buttons
        JButton openButton = new JButton("Open");
        JButton saveButton = new JButton("Save");

        add(openButton);
        add(saveButton);
    }
}
