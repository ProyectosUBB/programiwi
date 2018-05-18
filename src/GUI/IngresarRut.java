package GUI;

import objetos.Alumno;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import static ayudas.Tais.print;

/**
 * Clase vinculada al formulario de ingreso de RUT. Implementa la interacción con el usuario
 * así como la construcción y visualización de la ventana.
 *
 * @version     1.0 (17/05/2018)
 * @author      Anibal Llanos Prado
 */
public class IngresarRut {

    /* Variables de instancia. Elementos definidos en el formulario. */
    private JTextField campoRut;
    private JButton botonSalir;
    private JButton botonIngresar;
    private JPanel panelPrincipal;
    private JLabel etiquetaMensaje;

    /* Strings de mensajes para mostrar en la ventana. */
    private final String RUT_VACIO = "El campo RUT no puede estar en blanco.";
    private final String RUT_NO_VALIDO = "El RUT ingresado no se encuentra registrado.";

    /* Instancias de clases auxiliares a utilizar en la lógica. */
    private Alumno alumno;
    private JFrame ventana;


    /**
     * Constructor.
     *
     * @since   1.0
     */
    public IngresarRut() {

        /* Escuchador de botón 'salir'. */
        botonSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        /* Escuchador de botón 'ingresar'. */
        botonIngresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                String rut = campoRut.getText();
                print(rut);
                if (rut.equals("")) {   /* Mostrar mensaje si el rut está en blanco */
                    etiquetaMensaje.setText(RUT_VACIO);
                    etiquetaMensaje.setVisible(true);
                } else {
                    try {   /* Intenta crear el alumno para validarlo */
                        alumno = new Alumno(rut);
                        if (alumno.existe()) {  /* Si el alumno existe */
                            Object source = e.getSource();
                            if (source instanceof Component) {  /* Se busca la instancia de la ventana */
                                Component c = (Component) source;
                                Frame frame = JOptionPane.getFrameForComponent(c);
                                if (frame != null) {    /* Si se encuentra, se cierra y se carga la nueva. */
                                    frame.dispose();
                                    new InscribirRamos(alumno).mostrar();
                                }
                            }
                        } else {
                            etiquetaMensaje.setText(RUT_NO_VALIDO);
                        }
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }


    /**
     * Ejecuta el código para construir la interfaz gráfica.
     *
     * @since   1.0
     */
    public void mostrar() {

        /* Tamaño y posición de la pantalla */
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double screenWidth = screenSize.getWidth();
        double screenHeight = screenSize.getHeight();
        double ancho = 388;
        double alto = 180;
        int posicionX = (int) ((screenWidth / 2) - (ancho / 2));
        int posicionY = (int) ((screenHeight / 2) - (alto / 2));

        /* Construcción de la ventana. */
        ventana = new JFrame("SIAP - Ingreso de RUT");
        ventana.setContentPane(new IngresarRut().panelPrincipal);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setPreferredSize(new Dimension((int) ancho, (int) alto));
        ventana.setLocation(posicionX, posicionY);
        ventana.setResizable(false);
        ventana.pack();
        ventana.setVisible(true);
    }


    /**
     * Clase para implementar la creación manual de elementos definidos en el formulario.
     *
     * @since   1.0
     */
    private void createUIComponents() {
        ventana = new JFrame("SIAP - Ingreso de RUT");
    }

}
