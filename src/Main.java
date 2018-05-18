import GUI.IngresarRut;

import java.sql.SQLException;

/**
 * Clase que maneja el ciclo de interacción. Puede ser reemplazada por una GUI.
 * Lo fue.
 *
 * @version     3.0.1 (14/05/2018)
 * @author      Anibal Llanos Prado
 */
public class Main {

    /**
     * Método main. Ejecuta la aplicación.
     *
     * @param   argv    Argumentos desde línea de comandos.
     */
    public static void main(String[] argv) throws SQLException {
        //new InscribirRamos(new Alumno("16327196")).mostrar();
        new IngresarRut().mostrar();
    }


}
