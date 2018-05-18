package bd;

import java.util.HashMap;
import java.util.Set;

/**
 * Abstrae una tupla de la base de tatos como un objeto.
 *
 * @version     2.2.2 (18/05/2018)
 * @author      Anibal Llanos Prado
 */
public class Tupla {

    /* Variables de instancia */
    private HashMap<String, String> columnas;


    /**
     * Constructor
     *
     * @param   columnas    Las columnas obtenidas desde la base de datos (con sus valores)
     * @since   2.1
     */
    Tupla(HashMap<String, String> columnas) {
        this.columnas = columnas;
    }


    /**
     * Entrega el valor almacenado en alguna columna de la tupla.
     *
     * @param   columna El nombre de la columna que almacena el valor a consultar.
     * @return  Un String cuyo valor es el que se almacena en la columna de la tupla.
     * @since   2.1
     */
    public String valor(String columna) {
        return columnas.get(columna);
    }


    /**
     * Verifica si el valor indicado como parámetro es igual al almacenado en alguna columna de la tupla.
     *
     * @param   columna El nombre de la columna que almacena el valor a consultar.
     * @param   valor   El valor que debe tener la tupla en la columna indicada.
     * @return  True si encuentra algún valor que cumpla la condición indicada. False si no.
     * @since   2.1
     */
    public boolean tiene(String columna, String valor) {
        String resultado = columnas.get(columna);
        if (resultado == null) {
            return false;
        }
        return resultado.equals(valor);
    }


    /**
     * Determina si el valor almacenado en una columna de la tupla es nulo o no.
     *
     * @param   columna El nombre de la columna que almacena el valor a consultar.
     * @return  False si se encuentra un valor nulo. True si no.
     * @since   2.1
     */
    public boolean tieneNoNulo(String columna) {
        return columnas.get(columna) != null;
    }


    /**
     * Convierte la representación de una tupla (HashMap) en un String para su representación visual.
     *
     * @return Un String que representa visualmente a la tupla.
     * @since   2.1
     */
    public String toString() {
        StringBuilder salida = new StringBuilder();
        Set<String> llaves = columnas.keySet();
        if (llaves.size() > 0) {
            for (String llave : llaves) {
                salida.append(", ").append(llave).append(": '").append(columnas.get(llave)).append("'");
            }
            salida.substring(2);
        }
        return salida.toString();
    }


    /**
     * Getter de columnas.
     *
     * @return  Las columnas de la tabla (en forma de HashMap).
     * @since   2.2
     */
    HashMap<String, String> obtenerColumnas() {
        return columnas;
    }

}
