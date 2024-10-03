package co.itpex.imago2.app.view;

import co.itpex.imago2.app.components.CustomToolbar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class MainFrame extends JFrame {

    private static final long serialVersionUID = 8724320585911158994L;

    private JDesktopPane desktopPane;
    private JScrollPane scrollPane;
    private boolean operateOnCurrentImage = true; // Button state to indicate current image or new image operations

    public MainFrame() {
        // Setup main frame properties
        setTitle("Imago2 - Image Editor Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1024, 768);
        setLocationRelativeTo(null);

        // Create the main desktop pane
        desktopPane = new JDesktopPane();
        desktopPane.setPreferredSize(new Dimension(1024, 768)); // Start with a size that matches the main window

        // Wrap desktop pane in a scroll pane
        scrollPane = new JScrollPane(desktopPane);
        add(scrollPane, BorderLayout.CENTER);

        // Create toolbar and menu bar
        setJMenuBar(createMenuBar());
        add(createToolBar(), BorderLayout.NORTH);

        // Update desktop size when the main frame is resized
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

    // Expose method to add internal frames to desktopPane
    public void addInternalFrame(JInternalFrame frame) {
        desktopPane.add(frame);
        frame.setVisible(true);

        // Add a listener to restrict the internal frame movement
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

    // Adjust desktop pane size to ensure it's not smaller than the visible area
    private void adjustDesktopPaneSize() {
        // Get the size of the scroll pane's viewport (visible area)
        Dimension visibleSize = scrollPane.getViewport().getSize();

        // Get the current desktop size
        Dimension desktopSize = desktopPane.getPreferredSize();

        // Ensure desktopPane is at least as big as the visible area
        int newWidth = Math.max(visibleSize.width, desktopSize.width);
        int newHeight = Math.max(visibleSize.height, desktopSize.height);

        desktopPane.setPreferredSize(new Dimension(newWidth, newHeight));
        desktopPane.revalidate();
    }

    // Keep internal frames within the bounds of the desktop pane
    private void keepFrameWithinBounds(JInternalFrame frame) {
        Rectangle bounds = frame.getBounds();

        int maxX = desktopPane.getWidth() - bounds.width;
        int maxY = desktopPane.getHeight() - bounds.height;

        // Ensure frame does not go beyond the desktop bounds
        int newX = Math.max(0, Math.min(bounds.x, maxX));
        int newY = Math.max(0, Math.min(bounds.y, maxY));

        if (newX != bounds.x || newY != bounds.y) {
            frame.setLocation(newX, newY);
        }
    }
}
