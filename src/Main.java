import objetos.Alumno;

import java.io.IOException;
import java.sql.*;

import static ayudas.Tais.print;

/**
 * Clase que maneja el ciclo de interacción. Puede ser reemplazada por una GUI.
 *
 * @version     2.1.1 (12/05/2018)
 * @author      Anibal Llanos Prado
 */
public class Main {

    /**
     * Método main. Ejecuta la aplicación.
     *
     * @param   argv    Argumentos desde línea de comandos.
     * @throws IOException Si se generan errores al procesar la entrada desde el teclado.
     * @throws SQLException Si se generan errores al ejecutar consultas SQL.
     */
    public static void main(String[] argv) throws IOException, SQLException {

        /* Instanciación e inicialización */
        Alumno alumno;
        String[] comando;
        Controlador controlador = new Controlador();

        /* Captura de RUTs para inscribir ramos */
        print("SISTEMA DE INSCRIPCIÓN DE RAMOS\n");
        do {
            alumno = controlador.ingresarRut();
        } while (!alumno.existe());

        /* Inicialización de la inscripción */
        controlador.inicializar(alumno.columna("rut"));
        print("Alumno encontrado! [" + alumno.nombreCompleto() + "]\n");

        /* Ciclo de ingreso de comandos
        DEBO REEMPLAZAR ESO POR USO DE REGEX*/
        while(controlador.isLooping()) {
            comando = controlador.ingresarComando();    /* Se corta el comando en trocitos. */
            if (comando.length == 1) {  /* Comandos de largo 1 */
                switch (comando[0]) {
                    case "consultar":
                        controlador.mostrarRamosInscritos();
                        break;
                    case "guardar":
                        controlador.finalizarIngreso();
                        break;
                    case "limpiar":
                        controlador.obtenerInscripcion().vaciar();
                        break;
                    case "salir":
                        controlador.toggleLooping();
                        break;
                    default:
                        print("Comando no reconocido.");
                }
            } else if (comando.length == 2) {   /* Comandos de largo 2 */
                switch (comando[0]) {
                    case "inscribir":
                        controlador.ingresarRamo(comando[1]);
                        break;
                    default:
                        print("Comando no reconocido.");
                }
            } else {
                print("Comando no reconocido.");
            }
        }
    }
}
