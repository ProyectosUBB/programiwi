package objetos.bd;


import java.sql.SQLException;
import java.util.HashMap;
import java.util.Set;

/**
 * Abstrae una tupla de la base de tatos como un objeto.
 *
 * @version     2.3.2 (24/05/2018)
 * @author      Anibal Llanos Prado
 */
public class Tupla {

    /* Variables de instancia */
    private HashMap<String, String> columnas;


    /**
     * Constructor explícito.
     *
     * @param   columnas    Las columnas obtenidas desde la base de datos (con sus valores)
     * @since   2.1
     */
    private Tupla(HashMap<String, String> columnas) {
        this.columnas = columnas;
    }


    /**
     * Constructor desde base de datos.
     *
     * @param   nombreTabla El nombre de la tabla sobre la que se desea buscar la tupla.
     * @param   nombreColumna El nombre de la columna sobre la que se identificará la fila.
     * @param   valorColumna El valor que la columna debe tener para ser retornada.
     * @throws  SQLException Error al leer la tabla de ramos en la base de datos.
     * @since   2.3.0
     */
    protected Tupla(String nombreTabla, String nombreColumna, String valorColumna) throws SQLException {
        Tupla tupla = new Tabla(nombreTabla).buscarPrimero(nombreColumna, valorColumna);
        if (tupla != null) {
            columnas = tupla.obtenerColumnas();
        } else {
            columnas = null;
        }
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
    boolean tiene(String columna, String valor) {
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
     * @return  True si se encuentra un valor nulo. False si no.
     * @since   2.1
     */
    protected boolean tieneNoNulo(String columna) {
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


    /**
     * Determina si la tupla creada realmente existe, es decir, obtuvo datos desde la base
     * de datos.
     *
     * @return  True si encontró datos en la base de datos. False si no.
     * @since   2.3.0
     */
    public boolean existe() {
        return columnas != null;
    }



    /*
     *  CONSTRUCTORES ESTÁTICOS
     *  Protegen a las clases que utilizan los objetos de obtener instancias inválidas
     *  por errores en las consultas a la base de datos. Permite respetar el contrato
     *  primordial de esta abstracción de la base de datos: Si no existe, es null.
     */


    /**
     * Entrega una instancia de Tupla (primer resultado). Si los valores indicados para buscar en la base de datos no arrojan
     * resultado alguno, se devolverá Null en su lugar.
     *
     * @param   tabla Nombre de la tabla en la que se quiere buscar.
     * @param   columna Nombre de la columna de la tabla que se evaluará.
     * @param   valor Valor que debe tener la columna para ser una fila válida.
     * @return  Una tupla que cumple con las condiciones.
     * @throws  SQLException Error al leer la tabla que se indique.
     * @since   2.3.1
     */
    public static Tupla instanciarDesdeBaseDeDatos(String tabla, String columna, String valor) throws SQLException {
        Tupla nuevaTupla = new Tupla(tabla, columna, valor);
        if (nuevaTupla.existe()) {
            return nuevaTupla;
        }
        return null;
    }


    /**
     * Entrega una instancia de Tupla construida a partir de un HashMap.
     *
     * @param   tupla El HashMap que contiene los valores de la tupla.
     * @return  Una tupla que contiene los elementos indicados como parámetro.
     * @since   2.3.2
     */
    public static Tupla instanciarDesdeHashMap(HashMap<String, String> tupla) {
        return new Tupla(tupla);
    }

}
