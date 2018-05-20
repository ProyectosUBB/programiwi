package objetos.bd;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import static ayudas.Tais.mayor;

/**
 * Abstrae una tabla de la base de datos en un arreglo ordenado. Permite además realizar algunas
 * operaciones básicas sobre la consulta.
 * NOTA: Desde la versión 2.3 en adelante se encuentra además orientada a representar un conjunto
 * de tuplas, independientemente si viene desde la base de datos es creada desde una lista de tuplas
 * previamente obtenida.
 *
 * @version     2.3.3 (20/05/2018)
 * @author      Anibal Llanos Prado
 */
public class Tabla {

    private ArrayList<Tupla> tuplas;
    private String nombre;
    private BaseDeDatos bd;


    /**
     * Constructor desde la base de datos.
     *
     * @param   nombre  Nombre de la tabla
     * @throws  SQLException Error al recuperar información de la tabla en la base de datos.
     * @since   2.1.1
     */
    public Tabla(String nombre) throws SQLException {
        bd = new BaseDeDatos();
        tuplas = new ArrayList<>();
        this.nombre = nombre;
        String consulta = "SELECT * FROM `" + nombre + "`";
        for (HashMap<String, String> tupla : bd.consultar(consulta)) {
            tuplas.add(new Tupla(tupla));
        }
    }


    /**
     * Constructor desde conjunto de tuplas (en forma de HashMap)
     * IMPORTANTE: No es necesario que el nombre de la tabla coincida con algún nombre de tabla en
     * la base de datos. Esto sólo se vuelve problemático cuando se inserta la tabla (generará error
     * de SQL). El motivo de permitir esto es poder usar la clase tabla como medio de comunicación de
     * datos basados en listas de HashMap.
     *
     * @param   nombre  El nombre que se desea que tenga la tabla.
     * @param   tuplas  El conjunto de tuplas que deberá tener la tabla.
     *
     * @since   2.3.2
     */
    public Tabla(String nombre, ArrayList<Tupla> tuplas) {
        this.tuplas = tuplas;
        this.nombre = nombre;
    }


    /**
     * Inserta la tabla en la base de datos utilizando la función de inserción en la clase BaseDeDatos
     * que recibe una tabla.
     * IMPORTANTE: Se debe haber construido la tabla con un nombre que coincida con el de alguna tabla
     * en la base de datos o se generará un error SQL.
     *
     * @since   2.2
     */
    public void insertar() {
        bd.insertar(this);
    }


    /**
     * Carga las tuplas de una tabla y luego devuelve sólo la primera que cumpla la condición de que
     * el valor almacenado en la columna sea igual al valor indicado como parámetro.
     *
     * @param   columna Nombre de la columna sobre la que buscar.
     * @param   valor   El valor que la columna debe tener.
     * @return  Una Tupla con el resultado de a búsqueda.
     * @since   2.1
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
     * @since   2.1
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
     * Obtiene el valor máximo que tiene la tabla en cierta columna.
     * IMPORTANTE: Se intentará buscar el máximo en forma de entero. Debería ajustarse en el futuro.
     *
     * @param   columna La columna sobre la que se desea buscar el máximo.
     * @return  El mayor de los elementos en la columna.
     * @since   2.1.1
     */
    private String obtenerMaximo(String columna) {
        String maximo = null;
        for (Tupla tupla : tuplas) {
            if (maximo == null) {
                maximo = tupla.valor(columna);
            }
            maximo = mayor(maximo, tupla.valor(columna));
        }
        return maximo;
    }


    /**
     * Busca entre las tuplas almacenadas las tuplas que tengan el valor más alto en la columna entregada
     * como parámetro.
     *
     * @param   columna La columna sobre la que se desea buscar el máximo.
     * @since   2.1.1
     */
    public void filtrarMaximo(String columna) {
        filtrarTuplas(columna, obtenerMaximo(columna));
    }


    /**
     * Indica si es que la tabla tiene almacenada alguna tupla que cumpla la condición de que el
     * valor almacenado sea igual al valor indicado como parámetro.
     *
     * @param   columna Nombre de la columna sobre la que buscar.
     * @param   valor   El valor que la columna debe tener.
     * @return  True si la tabla cumple con la condición. False si es que no.
     * @since   2.1
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
     * @return  Un String que representa visualmente a la lista de tuplas.
     * @since   2.1
     */
    public String toString() {
        StringBuilder salida = new StringBuilder("> Tuplas en la tabla");
        if (nombre != null) {
            salida.append(" '").append(nombre).append("'");
        }
        salida.append(":\n");
        for (Tupla tupla : tuplas) {
            salida.append(">> ").append(tupla.toString()).append("\n");
        }
        return salida.toString();
    }


    /**
     * Getter de la lista de tuplas.
     *
     * @return  Una lista con las tuplas de la tabla.
     * @since   2.3
     */
    public ArrayList<Tupla> obtenerTuplas() {
        return tuplas;
    }


    /**
     * Obtiene la primera tupla de la tabla. Esto puede servir para conocer algunos valores que podrían
     * (o deberían) repetirse en las demás tuplas.
     *
     * @return  La primera tupla de la tabla.
     * @since   2.3.1
     */
    public Tupla otenerPrimeraTupla() {
        return tuplas.get(0);
    }


    /**
     * Getter del nombre de la tabla.
     *
     * @return  El nombre de la tabla.
     * @since   2.3.2
     */
    String obtenerNombre() {
        return nombre;
    }

}