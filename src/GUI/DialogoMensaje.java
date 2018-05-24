package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Clase que permite generar ventanas de diálogo contextual para mostrar mensajes al usuario
 * a través de la interfaz gráfica.
 *
 * @version     1.0.0 (24/05/2018)
 * @author      Anibal Llanos Prado
 */
public class DialogoMensaje extends JDialog {

    /* Variables de instancia */
    private JPanel contentPane;
    private JButton buttonOK;
    private JLabel etiquetaMensaje;


    /**
     * Constructor.
     *
     * @since   1.0.0
     */
    DialogoMensaje() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        /* Acción botón Aceptar */
        buttonOK.addActionListener(e -> dispose());

        /* Eventos al presionar la X (de cerrar) */
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }


    /**
     * Muestra un mensaje en una ventana contextual d diálogo.
     *
     * @param   mensaje El mensaje que se desea mostrar.
     */
    void mostrar(String mensaje, boolean esError) {

        /* Tamaño y posición de la pantalla */
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double screenWidth = screenSize.getWidth();
        double screenHeight = screenSize.getHeight();

        /* Construir diálogo */
        etiquetaMensaje.setText(mensaje);
        if (esError) {
            etiquetaMensaje.setForeground(Color.RED);
            setTitle("SIAP - ERROR");
        } else {
            setTitle("SIAP - Mensaje");
        }
        pack();
        Dimension tamano = getSize();
        setLocation(
                (int)((screenWidth / 2) - (tamano.width / 2)),
                (int)((screenHeight / 2) - (tamano.height / 2))
        );
        setVisible(true);
    }
}
