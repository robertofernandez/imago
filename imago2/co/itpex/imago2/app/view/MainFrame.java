package co.itpex.imago2.app.view;

import co.itpex.imago2.app.components.CustomToolbar;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private JDesktopPane desktopPane;
    private JScrollPane scrollPane;
    private boolean operateOnCurrentImage = true; // Button state to indicate current image or new image operations

    public MainFrame() {
        // Setup main frame properties
        setTitle("Imago2 - Image Editor Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1024, 768);

        // Create the main desktop pane
        desktopPane = new JDesktopPane();
        desktopPane.setPreferredSize(new Dimension(2000, 2000)); // Initial large size

        // Wrap desktop pane in a scroll pane
        scrollPane = new JScrollPane(desktopPane);
        add(scrollPane, BorderLayout.CENTER);

        // Create toolbar and menu bar
        setJMenuBar(createMenuBar());
        add(createToolBar(), BorderLayout.NORTH);

        // Display the main frame
        setVisible(true);
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // Example menu structure
        JMenu fileMenu = new JMenu("File");
        JMenuItem openItem = new JMenuItem("Open Image");
        JMenuItem exitItem = new JMenuItem("Exit");

        // Exit action
        exitItem.addActionListener(e -> System.exit(0));

        fileMenu.add(openItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        menuBar.add(fileMenu);
        return menuBar;
    }

    private JToolBar createToolBar() {
        CustomToolbar toolbar = new CustomToolbar();

        // Add a toggle button that switches between "Operate on Current Image" and "Create New Image"
        JToggleButton toggleButton = new JToggleButton("Operate on Current");
        toggleButton.addItemListener(e -> {
            operateOnCurrentImage = toggleButton.isSelected();
            toggleButton.setText(operateOnCurrentImage ? "Operate on Current" : "Create New Image");
        });

        toolbar.add(toggleButton);
        return toolbar;
    }

    // Expose method to add internal frames to desktopPane
    public void addInternalFrame(JInternalFrame frame) {
        desktopPane.add(frame);
        frame.setVisible(true);

        // Update the preferred size of the desktop pane to fit the new frame
        adjustDesktopPaneSize();
    }

    // Adjust desktop pane size to fit all internal frames
    private void adjustDesktopPaneSize() {
        Rectangle bounds = desktopPane.getBounds();
        for (JInternalFrame frame : desktopPane.getAllFrames()) {
            bounds = bounds.union(frame.getBounds());
        }
        desktopPane.setPreferredSize(bounds.getSize());
        desktopPane.revalidate();
    }
}
