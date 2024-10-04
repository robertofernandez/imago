package co.itpex.imago2.app.view;

import co.itpex.imago2.app.components.CustomToolbar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class MainFrame extends JFrame {
    private static final long serialVersionUID = -4643916002350103663L;
    private JDesktopPane desktopPane;
    private JScrollPane scrollPane;
    private boolean operateOnCurrentImage = true; // Button state to indicate current image or new image operations
    private JToolBar toolbar;

    public MainFrame() {
        // Set main frame properties
        setTitle("IMago2");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the size of the main window to be slightly smaller than the screen size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width * 3 / 4, screenSize.height * 3 / 4);  // 75% of screen size
        setLocationRelativeTo(null); // Center the main window on the screen

        // Create the main desktop pane
        desktopPane = new JDesktopPane();
        desktopPane.setPreferredSize(new Dimension(1024, 768)); // Initial size for the desktop pane

        // Wrap desktop pane in a scroll pane to allow scrolling if internal frames are larger
        scrollPane = new JScrollPane(desktopPane);
        add(scrollPane, BorderLayout.CENTER);

        // Create toolbar and menu bar
        setJMenuBar(createMenuBar());
        toolbar = createToolBar();
        add(toolbar, BorderLayout.NORTH);

        // Adjust desktop pane size when the main frame is resized
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                adjustDesktopPaneSize();
            }
        });

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

    // Method to add internal frames to desktopPane
    public void addInternalFrame(JInternalFrame frame) {
        desktopPane.add(frame);
        frame.setVisible(true);

        // Add a listener to restrict the internal frame movement and resize handling
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent e) {
                keepFrameWithinBounds(frame);
            }

            @Override
            public void componentResized(ComponentEvent e) {
                keepFrameWithinBounds(frame);
            }
        });

        // Update the preferred size of the desktop pane to fit the new frame
        adjustDesktopPaneSize();
    }

    // Adjust desktop pane size to fit all internal frames
    private void adjustDesktopPaneSize() {
        // Determine the maximum bounds required to show all internal frames
        int maxWidth = 0;
        int maxHeight = 0;

        for (JInternalFrame frame : desktopPane.getAllFrames()) {
            maxWidth = Math.max(maxWidth, frame.getX() + frame.getWidth());
            maxHeight = Math.max(maxHeight, frame.getY() + frame.getHeight());
        }

        // Set the new size for desktop pane to accommodate all frames
        Dimension newSize = new Dimension(maxWidth, maxHeight);
        desktopPane.setPreferredSize(newSize);

        // Revalidate and repaint to ensure scroll pane updates correctly
        desktopPane.revalidate();
        desktopPane.repaint();
    }

    // Keep internal frames within the bounds of the desktop pane
    private void keepFrameWithinBounds(JInternalFrame frame) {
        Rectangle bounds = frame.getBounds();

        // Ensure frame does not move outside the desktop pane boundaries
        int newX = Math.max(0, bounds.x);
        int newY = Math.max(0, bounds.y);

        // Apply new position if necessary
        if (newX != bounds.x || newY != bounds.y) {
            frame.setLocation(newX, newY);
        }

        // Adjust desktop size if frame goes beyond current desktop size
        adjustDesktopPaneSize();
    }
}
