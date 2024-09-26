/**
 * @author Roberto G. Fern&aacute;ndez
 * @version 0.01
 *
 * Nombre del archivo ImagoPpal.java
 *
 * Historia de Creaci�n/Modificaci�n :
 *
 * Autor del cambio             Fecha             Descripci�n
 * -----------------------      ----------        ---------------------------------------
 * Roberto G. Fern�ndez         24/08/2007        Creaci�n
 *
 */

package ui;

import static operaciones.FindColor.GREEN_CONTRAST_MODE;
import static operaciones.FindColor.SUBSTRACT_MODE;

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import operaciones.Drawbot;
import operaciones.FakeStackTraceBot;
import operaciones.FindColor;
import operaciones.Lightness;
import operaciones.MedianaIneficiente;
import operaciones.Mitad;
import operaciones.ModaI;
import operaciones.NerdyTraceBot;
import operaciones.OperadorNulo;
import operaciones.SavingDrawbot;
import operaciones.SavingStackTraceBot;
import operaciones.SimpleCluster;
import operaciones.SimpleClusterWithBasicColors;
import operaciones.SimplePaintTraceBot2;
import operaciones.SimpleTraceBot;
import operaciones.SimpleTraceBot2;
import operaciones.SimpleTraceBot3;
import operaciones.SimpleTraceToFile2;
import operaciones.SmoothThreshold;
import operaciones.Sobel;
import operaciones.StackTraceBot;
import operaciones.SuavizadoSelNegI;
import operaciones.SuavizadoSelPosI;
import operaciones.SuperNerdyTraceBot;
import operaciones.Taller1;
import operaciones.Threshold;
import operaciones.TraceBorders;
import util.DistanceColorDescription;
import util.mdi.MDIDesktopPane;

/**
 * Ventana principal de la aplicaci�n.
 * 
 * @author UTN user
 * 
 */
public class ImagoPpal extends JFrame implements InternalFrameListener, ActionListener {
    private static final long serialVersionUID = -2811861569233422674L;
    static final int desktopWidth = 1200;
    static final int desktopHeight = 800;

    JDesktopPane mainDesktopPane;
    private JScrollPane scrollPane;
    Vector<DocumentoDeImagen> documentosAbiertos;
    JMenu menuArchivo;
    JMenu menuOperaciones;
    JMenu savingOperations;
    JMenu thresholdMenu;
    JMenu smoothThresholdMenu;
    JMenu greenContrastMenu;
    JMenu substractMenu;
    JMenuItem menuItemAbrir;
    Vector<MenuItemDeOperacion> menuItemOperaciones;
    Vector<MenuItemDeOperacion> savingOperationsMenuItem;
    Vector<MenuItemDeOperacion> greenContrastColorFindingMenuItems;
    Vector<MenuItemDeOperacion> substractColorFindingMenuItems;
    Vector<MenuItemDeOperacion> thresholdMenuItems;
    Vector<MenuItemDeOperacion> smoothThresholdMenuItems;
    JMenuBar barraDeMenus;
    int idSeleccionado = -1;

    /**
     * @throws HeadlessException
     */
    public ImagoPpal() throws HeadlessException {
        super();
        this.inicializarVentana();
    }

    /**
     * Inicializa los par�metros de la ventana.
     * 
     */
    private void inicializarVentana() {
        this.setTitle("Imago Versi�n 0.01 ");
        JFrame.setDefaultLookAndFeelDecorated(true);

        ImageIcon logo = createImageIcon("/img/mago2.gif", "Logo de la aplicaci�n");

        this.setIconImage(logo.getImage());

        // Setear la GUI.
        // panelPrincipal = new JDesktopPane();
        mainDesktopPane = new MDIDesktopPane();
        mainDesktopPane.putClientProperty("JDesktopPane.dragMode", "outline");
        // Como usamos pack, no es suficiente llamar a setSize.
        // Debemos setear el tama�o preferido del panel principal.
        mainDesktopPane.setPreferredSize(new Dimension(desktopWidth, desktopHeight));
        scrollPane = new JScrollPane();
        scrollPane.getViewport().add(mainDesktopPane);

        // this.setContentPane(panelPrincipal);
        this.getContentPane().add("North", scrollPane);
        menuArchivo = new JMenu("Archivo");
        menuOperaciones = new JMenu("Operaciones");
        savingOperations = new JMenu("Operaciones con guardado");
        thresholdMenu = new JMenu("Threshold");
        smoothThresholdMenu = new JMenu("Smooth Threshold");
        substractMenu = new JMenu("Substract");
        greenContrastMenu = new JMenu("Green contrast");
        menuItemAbrir = new JMenuItem("Abrir...");

        menuItemOperaciones = new Vector<MenuItemDeOperacion>();
        savingOperationsMenuItem = new Vector<MenuItemDeOperacion>();
        thresholdMenuItems = new Vector<MenuItemDeOperacion>();
        smoothThresholdMenuItems = new Vector<MenuItemDeOperacion>();
        greenContrastColorFindingMenuItems = new Vector<MenuItemDeOperacion>();
        substractColorFindingMenuItems = new Vector<MenuItemDeOperacion>();
        menuItemOperaciones.add(new MenuItemDeOperacion(new OperadorNulo()));
        menuItemOperaciones.add(new MenuItemDeOperacion(new Mitad()));
        menuItemOperaciones.add(new MenuItemDeOperacion(new MedianaIneficiente()));
        menuItemOperaciones.add(new MenuItemDeOperacion(new ModaI()));
        menuItemOperaciones.add(new MenuItemDeOperacion(new SuavizadoSelPosI()));
        menuItemOperaciones.add(new MenuItemDeOperacion(new SuavizadoSelNegI()));
        menuItemOperaciones.add(new MenuItemDeOperacion(new Taller1()));
        menuItemOperaciones.add(new MenuItemDeOperacion(new SimpleCluster()));
        menuItemOperaciones.add(new MenuItemDeOperacion(new SimpleClusterWithBasicColors()));
        menuItemOperaciones.add(new MenuItemDeOperacion(new Lightness("Lightness")));
        menuItemOperaciones.add(new MenuItemDeOperacion(new Sobel()));
        menuItemOperaciones.add(new MenuItemDeOperacion(new Drawbot(0, "Drawbot ALL", 1)));
        menuItemOperaciones.add(new MenuItemDeOperacion(new Drawbot(0, "Drawbot 0", 3)));
        menuItemOperaciones.add(new MenuItemDeOperacion(new SimpleTraceBot(0, "Simple trace ALL", 1)));
        menuItemOperaciones.add(new MenuItemDeOperacion(new NerdyTraceBot(0, "Nerdy trace ALL", 1)));
        menuItemOperaciones.add(new MenuItemDeOperacion(new StackTraceBot(0, "Stack trace", 1)));
        menuItemOperaciones.add(new MenuItemDeOperacion(new FakeStackTraceBot(0, "Fake Stack trace", 1)));
        menuItemOperaciones.add(new MenuItemDeOperacion(new SuperNerdyTraceBot(0, "Super Nerdy trace ALL", 1)));
        menuItemOperaciones.add(new MenuItemDeOperacion(new SimpleTraceBot2(0, "Simple trace ALL 2", 1)));
        menuItemOperaciones.add(new MenuItemDeOperacion(new SimpleTraceBot3(0, "Simple trace ALL 3", 1)));
        menuItemOperaciones.add(new MenuItemDeOperacion(new SimplePaintTraceBot2(0, "Simple paint trace 2", 1)));
        menuItemOperaciones.add(new MenuItemDeOperacion(new SimpleTraceToFile2(0, "Simple trace 2 to file", 1)));
        menuItemOperaciones.add(new MenuItemDeOperacion(new Drawbot(1, "Drawbot 1", 3)));
        menuItemOperaciones.add(new MenuItemDeOperacion(new Drawbot(2, "Drawbot 2", 3)));
        menuItemOperaciones.add(new MenuItemDeOperacion(new TraceBorders(1, "Trace Borders")));

        savingOperationsMenuItem.add(new MenuItemDeOperacion(new SavingStackTraceBot(0, "Stack trace", 1)));

        savingOperationsMenuItem.add(new MenuItemDeOperacion(new SavingDrawbot(0, "Diagonal 0", 3)));
        savingOperationsMenuItem.add(new MenuItemDeOperacion(new SavingDrawbot(1, "Diagonal 1", 3)));
        savingOperationsMenuItem.add(new MenuItemDeOperacion(new SavingDrawbot(2, "Diagonal 2", 3)));

        greenContrastColorFindingMenuItems.add(new MenuItemDeOperacion(new FindColor(new DistanceColorDescription(120,
                240, 120, 200), "draw skin", GREEN_CONTRAST_MODE)));
        greenContrastColorFindingMenuItems.add(new MenuItemDeOperacion(new FindColor(new DistanceColorDescription(240,
                20, 240, 200), "draw light blue", GREEN_CONTRAST_MODE)));
        greenContrastColorFindingMenuItems.add(new MenuItemDeOperacion(new FindColor(new DistanceColorDescription(60,
                20, 240, 200), "draw blue", GREEN_CONTRAST_MODE)));
        greenContrastColorFindingMenuItems.add(new MenuItemDeOperacion(new FindColor(new DistanceColorDescription(150,
                200, 100, 200), "draw brown", GREEN_CONTRAST_MODE)));
        greenContrastColorFindingMenuItems.add(new MenuItemDeOperacion(new FindColor(new DistanceColorDescription(240,
                240, 240, 200), "draw white", GREEN_CONTRAST_MODE)));
        greenContrastColorFindingMenuItems.add(new MenuItemDeOperacion(new FindColor(new DistanceColorDescription(54,
                54, 54, 162), "draw black", GREEN_CONTRAST_MODE)));
        greenContrastColorFindingMenuItems.add(new MenuItemDeOperacion(new FindColor(new DistanceColorDescription(20,
                240, 20, 200), "draw red", GREEN_CONTRAST_MODE)));
        greenContrastColorFindingMenuItems.add(new MenuItemDeOperacion(new FindColor(new DistanceColorDescription(120,
                240, 140, 200), "draw lred", GREEN_CONTRAST_MODE)));
        greenContrastColorFindingMenuItems.add(new MenuItemDeOperacion(new FindColor(new DistanceColorDescription(240,
                24, 20, 200), "draw green", GREEN_CONTRAST_MODE)));
        greenContrastColorFindingMenuItems.add(new MenuItemDeOperacion(new FindColor(new DistanceColorDescription(240,
                140, 140, 200), "draw lgreen", GREEN_CONTRAST_MODE)));
        greenContrastColorFindingMenuItems.add(new MenuItemDeOperacion(new FindColor(new DistanceColorDescription(210,
                240, 40, 200), "draw yellow", GREEN_CONTRAST_MODE)));

        substractColorFindingMenuItems.add(new MenuItemDeOperacion(new FindColor(new DistanceColorDescription(120, 240,
                120, 200), "substract skin", SUBSTRACT_MODE)));
        substractColorFindingMenuItems.add(new MenuItemDeOperacion(new FindColor(new DistanceColorDescription(240, 20,
                240, 200), "substract light blue", SUBSTRACT_MODE)));
        substractColorFindingMenuItems.add(new MenuItemDeOperacion(new FindColor(new DistanceColorDescription(60, 20,
                240, 200), "substract blue", SUBSTRACT_MODE)));
        substractColorFindingMenuItems.add(new MenuItemDeOperacion(new FindColor(new DistanceColorDescription(150, 200,
                100, 200), "substract brown", SUBSTRACT_MODE)));
        substractColorFindingMenuItems.add(new MenuItemDeOperacion(new FindColor(new DistanceColorDescription(240, 240,
                240, 200), "substract white", SUBSTRACT_MODE)));
        substractColorFindingMenuItems.add(new MenuItemDeOperacion(new FindColor(new DistanceColorDescription(60, 60,
                60, 150), "substract black", SUBSTRACT_MODE)));
        substractColorFindingMenuItems.add(new MenuItemDeOperacion(new FindColor(new DistanceColorDescription(120, 240,
                140, 200), "substract light red", SUBSTRACT_MODE)));
        substractColorFindingMenuItems.add(new MenuItemDeOperacion(new FindColor(new DistanceColorDescription(20, 240,
                20, 200), "substract red", SUBSTRACT_MODE)));
        substractColorFindingMenuItems.add(new MenuItemDeOperacion(new FindColor(new DistanceColorDescription(240, 140,
                140, 200), "substract light green", SUBSTRACT_MODE)));
        substractColorFindingMenuItems.add(new MenuItemDeOperacion(new FindColor(new DistanceColorDescription(240, 24,
                20, 200), "substract green", SUBSTRACT_MODE)));
        substractColorFindingMenuItems.add(new MenuItemDeOperacion(new FindColor(new DistanceColorDescription(210, 240,
                40, 200), "substract yellow", SUBSTRACT_MODE)));

        for (int i = 25; i < 255; i += 25) {
            thresholdMenuItems.add(new MenuItemDeOperacion(new Threshold(i)));
        }

        for (int i = 25; i < 255; i += 25) {
            smoothThresholdMenuItems.add(new MenuItemDeOperacion(new SmoothThreshold(i)));
        }

        for (MenuItemDeOperacion menu : menuItemOperaciones) {
            menu.addActionListener(this);
            menuOperaciones.add(menu);
        }

        for (MenuItemDeOperacion menu : savingOperationsMenuItem) {
            menu.addActionListener(this);
            savingOperations.add(menu);
        }

        for (MenuItemDeOperacion menu : substractColorFindingMenuItems) {
            menu.addActionListener(this);
            substractMenu.add(menu);
        }
        for (MenuItemDeOperacion menu : greenContrastColorFindingMenuItems) {
            menu.addActionListener(this);
            greenContrastMenu.add(menu);
        }
        for (MenuItemDeOperacion menu : thresholdMenuItems) {
            menu.addActionListener(this);
            thresholdMenu.add(menu);
        }

        for (MenuItemDeOperacion menu : smoothThresholdMenuItems) {
            menu.addActionListener(this);
            smoothThresholdMenu.add(menu);
        }

        menuItemAbrir.addActionListener(this);

        menuArchivo.add(menuItemAbrir);

        barraDeMenus = new JMenuBar();

        barraDeMenus.add(menuArchivo);
        barraDeMenus.add(menuOperaciones);
        barraDeMenus.add(greenContrastMenu);
        barraDeMenus.add(substractMenu);
        barraDeMenus.add(thresholdMenu);
        barraDeMenus.add(smoothThresholdMenu);
        barraDeMenus.add(savingOperations);
        this.setJMenuBar(barraDeMenus);

        documentosAbiertos = new Vector<DocumentoDeImagen>();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private static void createAndShowGUI() {
        JFrame.setDefaultLookAndFeelDecorated(true);

        ImagoPpal ventana = new ImagoPpal();
        ventana.pack();
        ventana.setVisible(true);
    }

    public static void main(String[] args) {
        // Calendarizar un trabajo para el hilo de env�o de eventos:
        // creaci�n y muestra de la ventana principal.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    public void internalFrameOpened(InternalFrameEvent arg0) {

    }

    public void internalFrameClosing(InternalFrameEvent arg0) {

    }

    public void internalFrameClosed(InternalFrameEvent arg0) {

    }

    public void internalFrameIconified(InternalFrameEvent arg0) {

    }

    public void internalFrameDeiconified(InternalFrameEvent arg0) {

    }

    /**
     * Evento que se dispara cuando se selecciona una ventana.
     */
    public void internalFrameActivated(InternalFrameEvent arg0) {
        if (!(arg0.getInternalFrame() instanceof DocumentoDeImagen))
            return;
        this.setIdSeleccionado(((DocumentoDeImagen) arg0.getInternalFrame()).getId());
    }

    public void internalFrameDeactivated(InternalFrameEvent arg0) {
    }

    /**
     * Manejo de una acci�n, que puede ser: * Click sobre un item de men�.
     * 
     */
    public void actionPerformed(ActionEvent arg0) {
        if (arg0.getSource().equals(menuItemAbrir))
            this.abrirArchivo();
        else if (arg0.getSource() instanceof MenuItemDeOperacion) {
            if (idSeleccionado == -1)
                return;
            DocumentoDeImagen documentoActual = this.documentosAbiertos.elementAt(idSeleccionado);
            DocumentoDeImagen salida = ((MenuItemDeOperacion) arg0.getSource()).operar(documentoActual);
            this.cargarImagen(salida);
        }
    }

    /**
     * Ubica el documento dentro del panel principal de la aplicaci�n.
     * 
     * @param documento
     *            Documento a ubicar.
     */
    private void ubicarDocumento(DocumentoDeImagen documento) {
        documento.setLocation(12 * documento.getId(), 9 * documento.getId());
    }

    /**
     * Acci�n de cargar un documento.
     * 
     * @param documentoNuevo
     *            Documento a cargar.
     */
    private void cargarImagen(DocumentoDeImagen documentoNuevo) {
        this.documentosAbiertos.addElement(documentoNuevo);
        documentoNuevo.setId(documentosAbiertos.size() - 1);
        ubicarDocumento(documentoNuevo);
        mainDesktopPane.add(documentoNuevo);
        try {
            documentoNuevo.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {
        }

        documentoNuevo.setVisible(true);
    }

    /**
     * Acci�n de elegir un archivo y abrirlo.
     * 
     */
    private void abrirArchivo() {
        JFileChooser selectorDeArchivo = new JFileChooser();

        int returnVal = selectorDeArchivo.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            DocumentoDeImagen documentoNuevo = new DocumentoDeImagen(selectorDeArchivo.getSelectedFile(), this);
            this.documentosAbiertos.addElement(documentoNuevo);
            documentoNuevo.setId(documentosAbiertos.size() - 1);
            ubicarDocumento(documentoNuevo);
            mainDesktopPane.add(documentoNuevo);

            try {
                documentoNuevo.setSelected(true);
            } catch (PropertyVetoException e) {
                e.printStackTrace();
            }

            documentoNuevo.setVisible(true);

        }
    }

    public int getIdSeleccionado() {
        return idSeleccionado;
    }

    public void setIdSeleccionado(int idSeleccionado) {
        this.idSeleccionado = idSeleccionado;
    }

    protected ImageIcon createImageIcon(String path, String description) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            System.err.println("Archivo no encontrado: " + path);
            return null;
        }
    }
}