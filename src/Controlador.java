import bd.Tabla;
import bd.Tupla;
import objetos.Alumno;
import objetos.Inscripcion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

import static ayudas.Tais.*;

/**
 * Interfaz de comunicación entre la línea de comandos y la inscripción de ramos.
 *
 * @version     2.1.2 (12/05/2018)
 * @author      Anibal Llanos Prado
 */
class Controlador {

    private BufferedReader lector;
    private Inscripcion inscripcion;
    private boolean loop = true;


    /**
     * Constructor
     *
     */
    Controlador() {
        lector = new BufferedReader(new InputStreamReader(System.in));
    }

    /* -------------------------------------------------
     * MÉTODOS DESCRITOS EN EL DIAGRAMA DE SECUENCIA
     * -------------------------------------------------
     */

    /**
     * Solicita al usuario un RUT y luego realiza las validaciones que sean necesarias para acceder al sietema
     * de inscripción.
     *
     * @return Un objeto de tipo Usuario que abstrae al usuario sujeto de inscripción.
     * @throws IOException Errores de entrada de teclado.
     * @throws SQLException Errores de consulta SQL.
     */
    Alumno ingresarRut() throws IOException, SQLException {
        String rut = input(">> Ingrese el RUT del alumno (sin puntos ni dígito verificador): ", lector);
        return new Alumno(rut);
    }


    /**
     * Solicita la incorporación de un ramo en la lista de ramos que se desea inscribir.
     *
     * @param   codigo  El código del ramo por inscribir.
     * @throws SQLException Errores de escritura en la base de datos.
     */
    void ingresarRamo(String codigo) throws SQLException {
        inscripcion.inscribir(codigo);
    }


    /**
     * Muestra una lista de todos los ramos que ha inscrito un alumno.
     */
    void mostrarRamosInscritos() {
        print(inscripcion.toString());
    }

    /**
     * Ejecuta el almacenamiento persistente (en la abse de datos) de la inscripción en trámite. Adicionalmente,
     * muestra la lista de ramos que previamente han sido inscritos.
     *
     * @throws SQLException Errores de escritura en la base de datos.
     */
    void finalizarIngreso() throws SQLException {
        print("--- REGISTRANDO INSCRIPCIÓN EN LA BASE DE DATOS ---");
        mostrarRamosInscritos();
        inscripcion.guardarInscripcion();
        print("--- INSCRICIÓN TERMINADA ---\n");
    }


    /* -------------------------------------------------
     * MÉTODOS ADICIONALES
     * -------------------------------------------------
     */

    /**
     * Maneja el ingreso de un comando en la consola. Separa los comandos por espacios (" "), y devuelve
     * un arreglo con esas partes.
     *
     * @return Un arreglo de String donde cada elemento es uno de las partes del comando.
     * @throws IOException Errores de entrada desde el teclado.
     */
    String[] ingresarComando() throws IOException {
        String entrada = input(">> ", lector);
        return entrada.split(" ");
    }


    /**
     * Inicializa el controlador. Actualmente sólo vuelve a cargar la inscripción.
     *
     * @param   rut El rut del alumno que se ha cargado en el controlador.
     * @throws SQLException Si se generan errores al leer desde la base de datos.
     */
    void inicializar(String rut) throws SQLException {
        inscripcion = new Inscripcion(rut);
    }

    /**
     * Determina si el sistema se encuentra loopeando o no. Que el sistema se encuentre loopeando significa que
     * aun continua recimiendo comandos por parte del operados.
     *
     * @return True si se encuentra loopeando. False si no.
     */
    boolean isLooping() {
        return loop;
    }


    /**
     * Cambia el valor de loopeo en el controlador por el opuesto.
     */
    void toggleLooping() {
        loop = !loop;
    }


    /**
     * Obtiene el objeto de inscripción que se está almacenando.
     *
     * @return Un objeto de tipo inscripción (el que se encuentra actualmente en el controlador).
     */
    Inscripcion obtenerInscripcion() {
        return inscripcion;
    }

}
