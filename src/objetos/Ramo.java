package objetos;

import objetos.bd.Tabla;
import objetos.bd.Tupla;

import java.sql.SQLException;
import java.util.ArrayList;

import static ayudas.Tais.capitalizarTodo;

/**
 * Representación de un ramo, el cual es en sí una tupla, pero con un par de cosas adicionales.
 * En el presente tal vez no tenga mucho sentido utilizar una clase independiente para los ramos
 * (pudiendo utilizar Tupla directamente), sin embargo, los ramos tienen muchas más cosas que la
 * versión actual del proyecto no considera, como horarios, profesor, etc.
 *
 * @version     2.2.0 (20/05/2018)
 * @author      Anibal Llanos Prado
 */
public class Ramo extends Tupla {


    /**
     * Constructor
     *
     * @param   codigo El código del ramo que se desea crear.
     * @throws  SQLException En casos de generarse errores al buscar el ramo en la base de datos.
     * @since   2.1
     */
    private Ramo(String codigo) throws SQLException {
        super("ramos", "codigo", codigo);
    }


    /**
     * Construye una representación visual (texto) del ramo.
     *
     * @return  Un String con la representación en texto del ramo.
     * @since   2.1
     */
    public String toString() {
        return "(" + valor("codigo") + ") " + capitalizarTodo(valor("nombre"));
    }


    /**
     * Determina si el código del ramo es igual al que se indica a través de los parámetros.
     *
     * @param   codigo El código contra el cual se comparará.
     * @return  True si el código entregado y el almacenado son iguales. False si no.
     * @since   2.1
     */
    boolean tieneCodigo(String codigo) {
        return tiene("codigo", codigo);
    }


    /**
     * Convierte el ramo en una tabla. Esto permite interactuar con la base de datos utilizando los
     * objetos del programa (abstracción).
     *
     * @return  Una tabla con 1 tupla, el ramo.
     * @since   2.1
     */
    Tabla convertirEnTabla() {
        ArrayList<Tupla> tuplas = new ArrayList<>();
        tuplas.add(this);
        return new Tabla("ramos", tuplas);
    }



    /*
     *  CONSTRUCTORES ESTÁTICOS
     *  Protegen a las clases que utilizan los objetos de obtener instancias inválidas
     *  por errores en las consultas a la base de datos. Permite respetar el contrato
     *  primordial de esta abstracción de la base de datos: Si no existe, es null.
     */


    /**
     * Entrega una instancia de Ramo que ha sido construida utilizando el parámetro "código".
     * Si no se encuentra el código en la tabla de ramos, devuelve Null.
     *
     * @param   codigo El código del ramo que se desea instanciar.
     * @return  Una instancia de Ramo con los datos del ramo solicitado.
     * @throws  SQLException Error al buscar el ramo en la base de datos.
     * @since   2.2.0
     */
    public static Ramo instanciarConCodigo(String codigo) throws SQLException {
        Ramo nuevoRamo = new Ramo(codigo);
        if (nuevoRamo.existe()) {
            return nuevoRamo;
        }
        return null;
    }

}
