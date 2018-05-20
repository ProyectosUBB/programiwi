package GUI;

import objetos.bd.Tabla;
import objetos.bd.Tupla;
import objetos.Alumno;
import objetos.Ramo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

import static ayudas.Tais.ANO_ACTUAL;
import static ayudas.Tais.SEMESTRE_ACTUAL;
import static ayudas.Tais.capitalizarTodo;

/**
 * Clase vinculada al formulario de inscribir ramos. Implementa la interacción con el usuario
 * así como la construcción y visualización de la ventana.
 *
 * @version     1.2.1 (20/05/2018)
 * @author      Anibal Llanos Prado
 */
public class InscribirRamos {

    /* Variables de instancia de elementos definidos en el formulario. */
    private JPanel panelPrincipal;
    private JLabel etiquetaRut;
    private JLabel etiquetaNombre;
    private JLabel etiquetaCarrera;
    private JLabel fechaEtiqueta;
    private JLabel estadoEtiqueta;
    private JButton botonAgregarRamo;
    private JTextField campoCodigoRamo;
    private JList<String> inscripcionAnteriorLista;
    private JList<String> inscripcionLista;

    /* Instancias de elementos auxiliares utilizados en la lógica. */
    private Alumno alumno;
    private Tabla inscripcionAnterior;
    private Tabla ofertaRamos;
    private DefaultListModel<String> modeloInscripcion;


    /**
     * Constructor.
     *
     * @param   alumno  El alumno obtenido desde la ventana anterior (captura y validación de RUT.
     * @since   1.0
     */
    InscribirRamos(Alumno alumno) throws SQLException {
        this.alumno = alumno;
        ofertaRamos = new Tabla("ramos");

        botonAgregarRamo.addActionListener(e -> {
            String codigo = campoCodigoRamo.getText();
            if (ofertaRamos.tiene("codigo", codigo)) {
                if (!inscripcionAnterior.tiene("codigo", codigo)) {
                    try {
                        Ramo ramo = Ramo.instanciarConCodigo(codigo);
                        if (ramo != null) {
                            modeloInscripcion.addElement(ramo.toString());
                            campoCodigoRamo.setText("");
                        }
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        campoCodigoRamo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == 10) {
                    botonAgregarRamo.doClick();
                }
            }
        });
    }


    /**
     * Ejecuta el código para construir la interfaz gráfica.
     *
     * @since   1.0
     */
    void mostrar() throws SQLException {

        /* Tamaño y posición de la pantalla */
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double screenWidth = screenSize.getWidth();
        double screenHeight = screenSize.getHeight();

        /* Cargar la ventana */
        JFrame ventana = new JFrame("SIAP - Inscribir ramos");
        ventana.setContentPane(new InscribirRamos(alumno).panelPrincipal);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setResizable(false);
        ventana.pack();
        Dimension tamano = ventana.getSize();
        ventana.setLocation(
                (int)((screenWidth / 2) - (tamano.width / 2)),
                (int)((screenHeight / 2) - (tamano.height / 2))
        );
        ventana.setVisible(true);
    }


    /**
     * Construye un modelo para representar los elementos de la lista de inscri´ci+on anterior. La
     * lista es generada con los valores correspondientes a su inscripción anterior.
     *
     * @return  El modelo de la lista, con los elementos previamente insertados.
     * @throws  SQLException Error al buscar el ramo en la base de datos.
     * @since   1.2
     */
    private DefaultListModel<String> modeloInscripcionAnterior() throws SQLException {
        Tabla ramos = new Tabla("ramos");
        DefaultListModel<String> model = new DefaultListModel<>();
        Tupla tuplaRamo;
        for (Tupla tupla : inscripcionAnterior.obtenerTuplas()) {
            tuplaRamo = ramos.buscarPrimero("codigo", tupla.valor("ramos_codigo"));
            model.addElement("(" + tupla.valor("ramos_codigo") + ") " + capitalizarTodo(tuplaRamo.valor("nombre")));
        }

        return model;
    }


    /**
     * Clase para implementar la creación manual de elementos definidos en el formulario.
     *
     * @since   1.0
     */
    private void createUIComponents() throws SQLException {

        /* Carrera related */
        Tabla carrera = new Tabla("carrera");
        Tabla usuarioTieneCarrera = new Tabla("usuario_tiene_carrera");
        Tupla tuplaCarreraUsuario = usuarioTieneCarrera.buscarPrimero("usuario_rut", alumno.valor("rut"));
        Tupla tuplaCarrera = carrera.buscarPrimero("codigo", tuplaCarreraUsuario.valor("carrera_codigo"));

        /* Inscripción anterior */
        inscripcionAnterior = new Tabla("inscripciones_ramos");
        inscripcionAnterior.filtrarTuplas("usuario_rut", alumno.valor("rut"));
        inscripcionAnterior.filtrarMaximo("ano");
        inscripcionAnterior.filtrarMaximo("semestre");
        String semestre = inscripcionAnterior.otenerPrimeraTupla().valor("semestre");
        String ano = inscripcionAnterior.otenerPrimeraTupla().valor("ano");

        /* Crear Etiquetas */
        etiquetaRut = new JLabel();
        etiquetaNombre = new JLabel();
        fechaEtiqueta = new JLabel();
        estadoEtiqueta = new JLabel();
        etiquetaRut.setText(alumno.valor("rut"));
        etiquetaNombre.setText(alumno.nombreCompleto());
        etiquetaCarrera = new JLabel();

        /* Configurar etiquetas */
        fechaEtiqueta.setText(ano + " - " + semestre);
        if (SEMESTRE_ACTUAL.equals(semestre) && ANO_ACTUAL.equals(ano)) {
            estadoEtiqueta.setText("VIGENTE");
            estadoEtiqueta.setForeground(Color.green);
        } else {
            estadoEtiqueta.setText("EXPIRADA");
            estadoEtiqueta.setForeground(Color.red);
        }
        etiquetaCarrera.setText("(" + tuplaCarrera.valor("codigo") + "-" + tuplaCarrera.valor("malla") + ") "
                + capitalizarTodo(tuplaCarrera.valor("nombre")));

        /* Listas */
        inscripcionAnteriorLista = new JList<>();
        inscripcionAnteriorLista.setModel(modeloInscripcionAnterior());
        inscripcionLista = new JList<>();
        modeloInscripcion = new DefaultListModel<>();
        inscripcionLista.setModel(modeloInscripcion);
    }


}
