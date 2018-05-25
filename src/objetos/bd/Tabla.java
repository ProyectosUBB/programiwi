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
 * NOTA IMPORTANTE: Desde la versión 2.4 implementa el patrón inmutable.
 *
 * @version     2.4.0 (25/05/2018)
 * @author      Anibal Llanos Prado
 */
public class Tabla {

    /* Variables de instancia */
    private final ArrayList<Tupla> tuplas;
    private final String nombre;
    private final BaseDeDatos bd;


    /**
     * Constructor desde la base de datos.
     * IMMUTABLE OK!
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
            tuplas.add(Tupla.instanciarDesdeHashMap(tupla));
        }
    }


    /**
     * Constructor desde conjunto de tuplas (en forma de HashMap)
     * IMPORTANTE: No es necesario que el nombre de la tabla coincida con algún nombre de tabla en
     * la base de datos. Esto sólo se vuelve problemático cuando se inserta la tabla (generará error
     * de SQL). El motivo de permitir esto es poder usar la clase tabla como medio de comunicación de
     * datos basados en listas de HashMap.
     * IMMUTABLE OK!
     *
     * @param   nombre  El nombre que se desea que tenga la tabla.
     * @param   tuplas  El conjunto de tuplas que deberá tener la tabla.
     *
     * @since   2.3.2
     */
    public Tabla(String nombre, ArrayList<Tupla> tuplas) throws SQLException {
        this.tuplas = tuplas;
        this.nombre = nombre;
        bd = new BaseDeDatos();
    }


    /**
     * Inserta la tabla en la base de datos utilizando la función de inserción en la clase BaseDeDatos
     * que recibe una tabla.
     * IMPORTANTE: Se debe haber construido la tabla con un nombre que coincida con el de alguna tabla
     * en la base de datos o se generará un error SQL.
     * IMMUTABLE OK!
     *
     * @since   2.2
     */
    public void insertar() {
        bd.insertar(this);
    }


    /**
     * Carga las tuplas de una tabla y luego devuelve sólo la primera que cumpla la condición de que
     * el valor almacenado en la columna sea igual al valor indicado como parámetro.
     * IMMUTABLE OK!
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
     * IMMUTABLE OK!
     *
     * @param   columna Nombre de la columna sobre la que buscar.
     * @param   valor   El valor que la columna debe tener.
     * @since   2.1
     */
    public Tabla filtrarTuplas(String columna, String valor) throws SQLException {
        ArrayList<Tupla> nuevasTuplas = new ArrayList<>();
        for (Tupla tupla : tuplas) {
            if (tupla.tiene(columna, valor)) {
                nuevasTuplas.add(tupla);
            }
        }
        return new Tabla(nombre, nuevasTuplas);
    }


    /**
     * Obtiene el valor máximo que tiene la tabla en cierta columna.
     * IMPORTANTE: Se intentará buscar el máximo en forma de entero. Debería ajustarse en el futuro.
     * IMMUTABLE OK!
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
     * IMMUTABLE OK!
     *
     * @param   columna La columna sobre la que se desea buscar el máximo.
     * @since   2.1.1
     */
    public Tabla filtrarMaximo(String columna) throws SQLException {
        return filtrarTuplas(columna, obtenerMaximo(columna));
    }


    /**
     * Agrega una tupla a la tabla.
     * IMMUTABLE OK!
     *
     * @param   tupla La tupla que se desea agregar.
     * @since   2.1
     */
    public Tabla agregarTupla(Tupla tupla) throws SQLException {
        ArrayList<Tupla> nuevasTuplas = obtenerTuplas();
        nuevasTuplas.add(tupla);
        return new Tabla(nombre, nuevasTuplas);
    }


    /**
     * Elimina la tupla que se encuentre en cierta posición.
     * IMMUTABLE OK!
     *
     * @param   posicion La posición que tiene la tupla en la tabla.
     */
    public Tabla eliminarTupla(int posicion) throws SQLException {
        ArrayList<Tupla> nuevasTuplas = obtenerTuplas();
        if (posicion < tuplas.size()) {
            nuevasTuplas.remove(posicion);
        }
        return new Tabla(nombre, nuevasTuplas);
    }


    /**
     * Indica si es que la tabla tiene almacenada alguna tupla que cumpla la condición de que el
     * valor almacenado sea igual al valor indicado como parámetro.
     * IMMUTABLE OK!
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
     * IMMUTABLE OK!
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
     * Entrega la cantidad de tuplas que tenga actualmente la tabla.
     * IMMUTABLE OK!
     *
     * @return El número de tuplas de la tabla.
     */
    public int contarTuplas() {
        return tuplas.size();
    }


    /**
     * Getter de la lista de tuplas.
     * IMMUTABLE OK!
     *
     * @return  Una lista con las tuplas de la tabla.
     * @since   2.3
     */
    public ArrayList<Tupla> obtenerTuplas() {
        return tuplas;
    }


    /**
     * Obtiene una tupla de la tabla, ubicada en cierta posición.
     * IMMUTABLE OK!
     *
     * @param   posicion La posición de la tupla en la tabla.
     * @return  La tupla solicitada.
     * @since   2.3.6
     */
    public Tupla obtenerTupla(int posicion) {
        if (tuplas.size() > posicion) {
            return tuplas.get(posicion);
        }
        return null;
    }


    /**
     * Obtiene la primera tupla de la tabla. Esto puede servir para conocer algunos valores que podrían
     * (o deberían) repetirse en las demás tuplas.
     * IMMUTABLE OK!
     *
     * @return  La primera tupla de la tabla.
     * @since   2.3.1
     */
    public Tupla otenerPrimeraTupla() {
        return tuplas.get(0);
    }


    /**
     * Getter del nombre de la tabla.
     * IMMUTABLE OK!
     *
     * @return  El nombre de la tabla.
     * @since   2.3.2
     */
    String obtenerNombre() {
        return nombre;
    }

}
