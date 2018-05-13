package bd;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Abstrae una tabla de la base de datos en un arreglo ordenado. Permite además realizar algunas
 * operaciones básicas sobre la consulta.
 *
 * @version     2.1 (12/05/2018)
 * @author      Anibal Llanos Prado
 */
public class Tabla {

    // `
    /* Variables de instancia */
    private BaseDeDatos bd;
    private ArrayList<Tupla> tuplas;
    private String nombre;


    /**
     * Constructor
     *
     * @param nombre Nombre de la tabla
     * @throws SQLException Error al recuperar información de la tabla en la base de datos.
     */
    public Tabla(String nombre) throws SQLException {
        bd = new BaseDeDatos();
        tuplas = new ArrayList<>();
        this.nombre = nombre;
        cargarTuplas();
    }


    /**
     * Carga las tuplas de la tabla. Si se ejecuta cuando ya hay tuplas cargadas, se sobre-escriben.
     *
     * @throws SQLException Errores al buscar las tuplas en la base de datos.
     */
    private void cargarTuplas() throws SQLException {
        String consulta = "SELECT * FROM `" + nombre + "`";
        for (HashMap<String, String> tupla :  bd.consultar(consulta)) {
            tuplas.add(new Tupla(tupla));
        }
    }


    /**
     * Carga las tuplas de una tabla y luego devuelve sólo la primera que cumpla la condición de que
     * el valor almacenado en la columna sea igual al valor indicado como parámetro.
     *
     * @param   columna Nombre de la columna sobre la que buscar.
     * @param   valor   El valor que la columna debe tener.
     * @return  Una Tupla con el resultado de a búsqueda.
     */
    public Tupla buscarPrimero(String columna, String valor) {
        for (Tupla tupla : tuplas) {
            if (tupla.tiene(columna, valor)) {
                return tupla;
            }
        }
        return null;
    }


    /**
     * Busca entre las tuplas almacenadas las que cumplan la condición de que el valor almacenado
     * sea igual al valor indicado como parámetro. Finalmente vacía la lista de tuplas almacenadas
     * e inserta las que resultaron de la búsqueda.
     *
     * @param   columna Nombre de la columna sobre la que buscar.
     * @param   valor   El valor que la columna debe tener.
     */
    public void filtrarTuplas(String columna, String valor) {
        ArrayList<Tupla> nuevasTuplas = new ArrayList<>();
        for (Tupla tupla : tuplas) {
            if (tupla.tiene(columna, valor)) {
                nuevasTuplas.add(tupla);
            }
        }
        tuplas = nuevasTuplas;
    }


    /**
     * Indica si es que la tabla tiene almacenada alguna tupla que cumpla la condición de que el
     * valor almacenado sea igual al valor indicado como parámetro.
     *
     * @param   columna Nombre de la columna sobre la que buscar.
     * @param   valor   El valor que la columna debe tener.
     * @return  True si la tabla cumple con la condición. False si es que no.
     */
    public boolean tiene(String columna, String valor) {
        for (Tupla tupla : tuplas) {
            if (tupla.tiene(columna, valor)) {
                return true;
            }
        }
        return false;
    }


    /**
     * Convierte la representación de una lista de tuplas (ArrayList) en un String para su representación visual.
     *
     * @return Un String que representa visualmente a la lista de tuplas.
     */
    public String toString() {
        StringBuilder salida = new StringBuilder("> Tuplas en la tabla '" + nombre + "':\n");
        for (Tupla tupla : tuplas) {
            salida.append(">> ").append(tupla.toString()).append("\n");
        }
        return salida.toString();
    }

}
