package co.itpex.imago2.app.components;

import javax.swing.*;

public class CustomToolbar extends JToolBar {

    public CustomToolbar() {
        super("Tools");

        // Example toolbar buttons
        JButton openButton = new JButton("Open");
        JButton saveButton = new JButton("Save");

        add(openButton);
        add(saveButton);
    }
}
