package GUI;

import objetos.bd.Tabla;
import objetos.Alumno;
import objetos.Ramo;
import objetos.bd.Tupla;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

import static ayudas.Tais.*;

/**
 * Clase vinculada al formulario de inscribir ramos. Implementa la interacción con el usuario
 * así como la construcción y visualización de la ventana.
 *
 * @version     1.2.3 (24/05/2018)
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
    private JButton botonEliminarRamo;
    private JButton botonListarRamos;
    private JButton botonInscribirRamos;
    private JButton botonSalir;

    /* Instancias de elementos auxiliares utilizados en la lógica. */
    private Alumno alumno;
    private Tabla ofertaRamos;
    private InscripcionListModel modeloInscripcionAnterior;
    private InscripcionListModel modeloInscripcionActual;


    /**
     * Constructor.
     *
     * @param   alumno  El alumno obtenido desde la ventana anterior (captura y validación de RUT.
     * @since   1.0
     */
    InscribirRamos(Alumno alumno) {
        this.alumno = alumno;

        /* Escuchador Agregar Ramo */
        botonAgregarRamo.addActionListener(e -> {
            String codigo = campoCodigoRamo.getText();
            if (ofertaRamos.tiene("codigo", codigo)) {
                if (modeloInscripcionAnterior.noTieneRamos(codigo)) {
                    if (modeloInscripcionActual.noTieneRamos(codigo)) {
                        try {
                            Ramo ramo = Ramo.instanciarConCodigo(codigo);
                            if (ramo != null) {
                                modeloInscripcionActual.addElement(ramo);
                                campoCodigoRamo.setText("");
                            }
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                    } else {
                        new DialogoMensaje()
                                .mostrar("El ramo se encuentra en lista de inscripción actual.", true);
                    }
                } else {
                    new DialogoMensaje().mostrar("El ramo ya se ha sido cursado por el alumno.", true);
                }
            } else {
                new DialogoMensaje().mostrar("El ramo no existe.", true);
            }
        });

        /* Escuchador Apretar Enter */
        campoCodigoRamo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == 10) {
                    botonAgregarRamo.doClick();
                }
            }
        });

        /* Escuchador Eliminar Ramo */
        botonEliminarRamo.addActionListener(e ->
                modeloInscripcionActual.removeElement(inscripcionLista.getSelectedIndex()));

        /* Escuchador Mostrar Lista de Ramos */
        botonListarRamos.addActionListener(e -> {
            StringBuilder stringBuilder = new StringBuilder();
            for (Tupla tupla : ofertaRamos.obtenerTuplas()) {
                stringBuilder.append("<br>(").append(tupla.valor("codigo")).append(") ")
                        .append(tupla.valor("nombre"));
            }
            new DialogoMensaje().mostrar(
                    "<html>Lista de ramos disponibles:<br>" + stringBuilder.toString() + "</html>",
                    false
            );
        });

        /* Escuchador Inscribir Ramos */
        botonInscribirRamos.addActionListener(e -> {
            if (modeloInscripcionActual.contarRamos() != 0) {
                modeloInscripcionActual.inscribir();
                try {
                    modeloInscripcionAnterior = new InscripcionListModel(alumno.valor("rut"), false);
                    inscripcionAnteriorLista.setModel(modeloInscripcionAnterior);
                    estadoEtiqueta.setText("VIGENTE");
                    estadoEtiqueta.setForeground(Color.green);
                    bloquearInscripcion();
                    new DialogoMensaje().mostrar("Ramo(s) ha(n) sido inscrito(s) exitosamente!", false);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            } else {
                new DialogoMensaje().mostrar("No se ha agregado ningun ramo.", true);
            }
        });

        /* Escuchador Salir */
        botonSalir.addActionListener(e -> System.exit(0));
    }


    /**
     * Bloquea los elementos de la interfaz en caso de que el alumno ya tenga una inscripción vigente.
     *
     * @since   1.2.3
     */
    private void bloquearInscripcion() {
        campoCodigoRamo.setEnabled(false);
        botonAgregarRamo.setEnabled(false);
        botonEliminarRamo.setEnabled(false);
        botonInscribirRamos.setEnabled(false);
        inscripcionLista.setEnabled(false);
    }


    /**
     * Ejecuta el código para construir la interfaz gráfica.
     *
     * @since   1.0
     */
    void mostrar() {

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
     * Clase para implementar la creación manual de elementos definidos en el formulario.
     *
     * @since   1.0
     */
    private void createUIComponents() throws SQLException {

        campoCodigoRamo = new JTextField();
        botonInscribirRamos = new JButton();
        botonEliminarRamo = new JButton();
        botonAgregarRamo = new JButton();

        /* Inscripciones */
        ofertaRamos = new Tabla("ramos");
        modeloInscripcionActual =
                new InscripcionListModel(alumno.valor("rut"), true);
        modeloInscripcionAnterior =
                new InscripcionListModel(alumno.valor("rut"), false);
        Tabla ultimaInscripcion = modeloInscripcionAnterior.obtenerInscripcion().obtenerMasRecientes();
        String ultimoSemestre = ultimaInscripcion.otenerPrimeraTupla().valor("semestre");
        String ultimoAno = ultimaInscripcion.otenerPrimeraTupla().valor("ano");



        /* Crear Etiquetas */
        etiquetaRut = new JLabel();
        etiquetaNombre = new JLabel();
        fechaEtiqueta = new JLabel();
        estadoEtiqueta = new JLabel();
        etiquetaRut.setText(alumno.valor("rut"));
        etiquetaNombre.setText(alumno.nombreCompleto());
        etiquetaCarrera = new JLabel();

        /* Configurar etiquetas */
        fechaEtiqueta.setText(ultimoAno + " - " + ultimoSemestre);
        if (SEMESTRE_ACTUAL.equals(ultimoSemestre) && ANO_ACTUAL.equals(ultimoAno)) {
            estadoEtiqueta.setText("VIGENTE");
            estadoEtiqueta.setForeground(Color.green);
        } else {
            estadoEtiqueta.setText("EXPIRADA");
            estadoEtiqueta.setForeground(Color.red);
        }
        etiquetaCarrera.setText("(" + alumno.carrera("codigo") + "-" + alumno.carrera("malla") + ") "
                + capitalizarTodo(alumno.carrera("nombre")));

        /* Listas */
        inscripcionAnteriorLista = new JList<>();
        inscripcionAnteriorLista.setModel(modeloInscripcionAnterior);
        inscripcionLista = new JList<>();
        inscripcionLista.setModel(modeloInscripcionActual);

        if (ultimoSemestre.equals(SEMESTRE_ACTUAL) && ultimoAno.equals(ANO_ACTUAL)) {
            campoCodigoRamo.setEnabled(false);
            botonAgregarRamo.setEnabled(false);
            botonEliminarRamo.setEnabled(false);
            botonInscribirRamos.setEnabled(false);
        }
    }


}
